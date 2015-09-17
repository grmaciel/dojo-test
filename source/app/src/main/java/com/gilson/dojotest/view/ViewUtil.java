package com.gilson.dojotest.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.gilson.dojotest.R;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class ViewUtil {
    public static int getBadgeResource(String badgeName) {
        switch (badgeName) {
            case "platinum":
                return R.drawable.badge_platinum;
            case "challenger":
                return R.drawable.badge_challenger;
            case "diamond":
                return R.drawable.badge_diamond;
            case "gold":
                return R.drawable.badge_gold;
        }

        return -1;
    }

    public static Spannable getMatchPlayerSpannable(Context context, String matchStatus, String champion) {
        Spannable text = new SpannableString(matchStatus + " " +
                context.getResources().getString(R.string.as) +
                " " + champion);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, matchStatus.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(Color.LTGRAY), matchStatus.length(), matchStatus.length() + 4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), matchStatus.length() + 4, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }

    public static String getMatchStatus(Context context, boolean win) {
        return win ? context.getResources().getString(R.string.win) :
                context.getResources().getString(R.string.defeat);
    }
}
