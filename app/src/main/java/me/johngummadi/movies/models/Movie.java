package me.johngummadi.movies.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import me.johngummadi.movies.utils.ImageSize;

/**
 * Created by johngummadi on 5/31/17.
 */

public class Movie {
    public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    @SerializedName("poster_path")
    private String _posterPath;
    public String getPosterPath() { return _posterPath; }
    public String getPosterPathFull() {
        /**
         * TODO: This should be retrieved from the "configuration" API
         * https://api.themoviedb.org/3/configuration?api_key=xyz
         */
        return IMAGE_BASE_URL + "w370/" + _posterPath;
    }
    public static ImageSize getPosterImageSize() {
        return new ImageSize(370, 526);
    }

    @SerializedName("adult")
    private boolean _adult;
    public boolean isAdult() { return _adult; }

    @SerializedName("overview")
    private String _overview;
    public String getOverview() { return _overview; }

    @SerializedName("release_date")
    private String _releaseDate;
    public Date getReleaseDate() { return new Date(_releaseDate); }


    @SerializedName("genre_ids")
    private int[] _genreIds;
    public int[] getGenreIds() { return _genreIds; }

    @SerializedName("id")
    private int _id;
    public int getId() { return _id; }

    @SerializedName("original_title")
    private String _originalTitle;
    public String getOriginalTitle() { return _originalTitle; }

    @SerializedName("original_language")
    private String _originalLanguage;
    public String getOriginalLanguage() { return _originalLanguage; }

    @SerializedName("title")
    private String _title;
    public String getTitle() { return _title; }

    @SerializedName("backdrop_path")
    private String _backdropPath;
    public String getBackdropPath() { return _backdropPath; }
    public String getBackdropPathFull() {
        /**
         * TODO: This should be retrieved from the "configuration" API
         * https://api.themoviedb.org/3/configuration?api_key=xyz
         */
        return IMAGE_BASE_URL + "w780/" + _backdropPath;
    }

    @SerializedName("popularity")
    private double _popularity;
    public double getPopularity() { return _popularity; }

    @SerializedName("vote_count")
    private int _voteCount;
    public int getVoteCount() { return _voteCount; }

    @SerializedName("video")
    private boolean _video;
    public boolean getVideo() { return _video; }

    @SerializedName("vote_average")
    private double _voteAverage;
    public double getVoteAverage() { return _voteAverage; }
}
