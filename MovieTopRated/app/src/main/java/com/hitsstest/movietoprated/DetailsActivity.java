package com.hitsstest.movietoprated;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitsstest.movietoprated.Interfaces.ApiService;
import com.hitsstest.movietoprated.PojoClasses.Cast;
import com.hitsstest.movietoprated.PojoClasses.Credits;
import com.hitsstest.movietoprated.PojoClasses.Genre;
import com.hitsstest.movietoprated.PojoClasses.Genres;
import com.hitsstest.movietoprated.PojoClasses.Result;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    private static final String IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500/";
    private ImageView imageLayoutBG;
    private TextView detailsTitle,
            detailsScore,
            detailsOverview,
            detailsCrew,
            detailsRelease,
            detilsLanguage,
            detailsGenres;
    private Result resultObject;
    private  List<Genre> genreList;
    private  List<Cast> castList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resultObject = (Result) getIntent().getExtras().getSerializable("result");
        InitView();
        FillData();
    }

    private void InitView() {
        imageLayoutBG = (ImageView) findViewById(R.id.detailsBackground);
        detailsTitle = (TextView) findViewById(R.id.detailsTitleTex);
        detailsScore = (TextView) findViewById(R.id.detailsScoreText);
        detailsOverview = (TextView) findViewById(R.id.detailsOverviewText);
        detailsCrew = (TextView) findViewById(R.id.detailsCrewText);
        detailsRelease = (TextView) findViewById(R.id.detailsReleaseText);
        detilsLanguage = (TextView) findViewById(R.id.detailsLenguageText);
        detailsGenres = (TextView) findViewById(R.id.detailsGenresText);

    }

    private void FillData() {
        GetJSONGerens("https://api.themoviedb.org/3/genre/movie/");
        GetJSONCast("https://api.themoviedb.org/3/movie/");

        Picasso.get().load(IMAGE_BASE_PATH + resultObject.getPosterPath()).into(imageLayoutBG);
        detailsTitle.setText(resultObject.getTitle());
        detailsScore.setText(resultObject.getVoteAverage().toString() + "pts");
        detailsOverview.setText(resultObject.getOverview());
        try {
            detailsRelease.setText(GetDateFormat(resultObject.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detilsLanguage.setText(resultObject.getOriginalLanguage());
    }

    private CharSequence GetGenres() {
        String sequenceGeneres = new String();
                detailsGenres.setText("");
                List<Integer> list = resultObject.getGenreIds();
                for (int i : list) {
                    for (Genre g : genreList) {
                        if (g.getId() == i) {
                            sequenceGeneres = sequenceGeneres+g.getName() + ", ";
                        }
                    }
                }
        sequenceGeneres = sequenceGeneres.substring(0, sequenceGeneres.length()-2);
        return sequenceGeneres;
    }

    private CharSequence GetCast() {
        String sequenceCast = new String();
        detailsCrew.setText("");
        for (int i =0; i<10 && i< castList.size(); i++){
          sequenceCast = sequenceCast + castList.get(i).getName() + ", ";
        }
        sequenceCast = sequenceCast.substring(0, sequenceCast.length()-2);

        return sequenceCast;
    }

    private String GetDateFormat(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return dateFormat.format(myDate);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void GetJSONGerens(String baseURL) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getDataGenres().enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                switch (response.code()) {
                    case 200:
                        Genres genresData = response.body();
                        genreList = genresData.getGenres();
                        detailsGenres.setText(GetGenres());
                        break;
                    case 401:
                        Toast.makeText(getApplicationContext(), "Error 401.No se ha podido obtener la informacion. Intenta mas tarde", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getApplicationContext(), "Error 404. No se ha podido encontrar la informacion.", Toast.LENGTH_LONG).show();
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable t) {
                Log.e("error", t.toString());

            }
        });

    }

    private void GetJSONCast(String baseURL) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Credits> creditsCall = apiService.getDataCast(resultObject.getId());
        creditsCall.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                switch (response.code()){
                    case 200:
                        Credits creditsData = response.body();
                        castList = creditsData.getCast();
                        detailsCrew.setText(GetCast());
                        break;
                    case 401:
                        Toast.makeText(getApplicationContext(), "Error 401.No se ha podido obtener la informacion. Intenta mas tarde", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getApplicationContext(), "Error 404. No se ha podido encontrar la informacion.", Toast.LENGTH_LONG).show();
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.e("error", t.toString());

            }
        });




    }

}
