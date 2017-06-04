package me.johngummadi.movies.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by johngummadi on 6/4/17.
 */

public class Utils {

    public static void showAlert(
            Context context,
            String title,
            String message) {
        AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.show();
    }
}
