package org.ababup1192.rsp.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LayoutHelper {

    public static <T extends View> void setLinearLayoutParams(T view, int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    public static <T extends View> void setLinearLayoutParams(T view, int widthAndHeight, int left, int top, int right, int bottom) {
        setLinearLayoutParams(view, widthAndHeight, widthAndHeight, left, top, right, bottom);
    }

    public static <T extends View> void setLinearLayoutParams(T view, int width, int height) {
        setLinearLayoutParams(view, width, height, 0, 0, 0, 0);
    }

    public static <T extends View> void setLinearLayoutParams(T view, int widthAndHeight) {
        setLinearLayoutParams(view, widthAndHeight, widthAndHeight);
    }

    public static <T extends View> void setLinearLyoutParamsWrap(T view) {
        setLinearLayoutParams(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
