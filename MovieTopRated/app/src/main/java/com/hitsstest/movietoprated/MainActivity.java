package com.hitsstest.movietoprated;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hitsstest.movietoprated.Adapters.AdapterPosterView;
import com.hitsstest.movietoprated.Interfaces.ApiService;
import com.hitsstest.movietoprated.PojoClasses.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();

    }

    private void InitView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerPoster);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        GetJSON();
    }

    private void GetJSON() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getDataTop().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                switch (response.code()) {
                    case 200:
                        Movie movieData = response.body();
                        final AdapterPosterView adapterPosterView = new AdapterPosterView(movieData.getResults(), getApplicationContext());
                        recyclerView.setAdapter(adapterPosterView);

                        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            View firstVisibleChild = recyclerView.getChildAt(0);//change if necessary. determine that item(items) you need to find
                            final ImageView imageView = (ImageView) firstVisibleChild.findViewById(R.id.imageViewPoster);
                            if (null != imageView) {
                                imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        int[] locationsFrom = new int[2];
                                        imageView.getLocationInWindow(locationsFrom);
                                        int xStart = locationsFrom[0];
                                        int yStart = locationsFrom[1];
                                        Log.d("LOG_TAG", "your item xEnd=" + xStart + " yEnd=" + yStart);
                                        if (null != adapterPosterView) {
                                            adapterPosterView.setX(xStart);
                                            adapterPosterView.setY(yStart);
                                        }
                                    }
                                });
                            }
                        }
                    });*/


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
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }


}
