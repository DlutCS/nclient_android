package org.dlutcs.nclient_android.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zeyiwu on 12/4/14.
 */
public class Toaster {

    public static void show(final Context context, final String text) {
        show(context, text, true);
    }

    public static void show(final Context context, final String text, final boolean isShort) {
        Toast.makeText(context, text, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)
                .show();
    }

    public static void show(final Context context, final int text) {
        show(context, text, true);
    }

    public static void show(final Context context, final int text, final boolean isShort) {
        Toast.makeText(context, text, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)
                .show();
    }
}
