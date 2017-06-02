package me.johngummadi.movies.utils;

/**
 * Created by johngummadi on 6/1/17.
 * Android's Size class is available only from API 21.
 * We could have used Point or Pair, etc., but
 * doesn't ook good, therefore this class.
 */

public class ImageSize {
    private int _width;
    private int _height;

    public ImageSize(int width, int height) {
        _width = width;
        _height = height;
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
    }
}
