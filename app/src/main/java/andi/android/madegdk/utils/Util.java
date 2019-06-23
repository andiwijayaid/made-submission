package andi.android.madegdk.utils;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Util {

    private Context context;

    public Util(Context context) {
        this.context = context;
    }

    public static Float convertRatingToFloat(Integer rating) {
        return rating * 5 / 100f;
    }

    public Integer getDrawableId(String drawableName) {
        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }

    public String convertToCurrency(Integer money) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String pattern = ((DecimalFormat) nf).toPattern();
        String newPattern = pattern.replace("\u00A4", "").trim();
        NumberFormat newFormat = new DecimalFormat(newPattern);
        return newFormat.format(money);
    }

    public Boolean isZero(Integer money) {
        return money == 0;
    }
}
