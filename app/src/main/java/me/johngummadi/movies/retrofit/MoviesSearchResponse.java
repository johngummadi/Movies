package me.johngummadi.movies.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import me.johngummadi.movies.models.Movie;

/**
 * Created by johngummadi on 5/31/17.
 */

public class MoviesSearchResponse {
    @SerializedName("page")
    private int _page;
    public int getPage() { return _page; }

    @SerializedName("results")
    private Movie[] _results;
    public List<Movie> getResults() { return Arrays.asList(_results); }

    @SerializedName("total_results")
    private int _totalResults;
    public int getTotalResults() { return _totalResults; }

    @SerializedName("total_pages")
    private int _totalPages;
    public int getTotalPages() { return _totalPages; }
}
