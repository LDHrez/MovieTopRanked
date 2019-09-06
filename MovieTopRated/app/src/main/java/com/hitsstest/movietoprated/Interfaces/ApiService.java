package com.hitsstest.movietoprated.Interfaces;

import com.hitsstest.movietoprated.PojoClasses.Credits;
import com.hitsstest.movietoprated.PojoClasses.Genres;
import com.hitsstest.movietoprated.PojoClasses.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("top_rated?api_key=d6c5804484df055f7a06983891d56a6f")
    Call<Movie> getDataTop();
    @GET("list?api_key=d6c5804484df055f7a06983891d56a6f")
    Call<Genres> getDataGenres();
    @GET("{id}/credits?api_key=d6c5804484df055f7a06983891d56a6f")
    Call<Credits> getDataCast(@Path("id") int movieId);
}
