package me.johngummadi.movies.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by johngummadi on 5/31/17.
 */

public class RestClient {
    final String BASE_URL = "https://api.themoviedb.org/3/";

    Retrofit _retrofit;
    MovieApi _api;
    private static RestClient _INSTANCE = new RestClient();

    private RestClient() {
        _retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        _api = _retrofit.create(MovieApi.class);
    }

    public static MovieApi getApi() {
        return _INSTANCE._api;
    }
}
