package me.johngummadi.movies.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by johngummadi on 5/31/17.
 */

public interface MovieApi {
    final String apikey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @POST("search/movie?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&query")
    Call<MoviesSearchResponse> searchMovies(
            @Query("query") String query,
            @Query("page") int page);
}
