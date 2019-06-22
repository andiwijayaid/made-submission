package andi.android.madegdk.utils;

import android.content.Context;

public class Util {

    private Context context;

    public Util() {}

    public Util(Context context) {
        this.context = context;
    }

    public static Float convertRatingToFloat(Integer rating) {
        return rating * 5 / 100f;
    }

    public Integer getDrawableId(String drawableName) {
        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }

}
