package com.ezerski.vladislav.easyenglish.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {
    public static void showShort(@NonNull final View view,
                                 @NonNull final String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showShort(@NonNull final View view,
                                 @StringRes final int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}