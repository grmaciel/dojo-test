package com.gilson.dojotest.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.gilson.dojotest.R;
import com.gilson.dojotest.domain.Badge;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class ViewUtil {
    public static int getBadgeResource(String badgeName) {
        switch (badgeName) {
            case "master":
                return R.drawable.badge_master;
            case "silver":
                return R.drawable.badge_silver;
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

    public static int getBadgeLevel(String badgeName) {
        switch (badgeName) {
            case "silver":
                return Badge.SILVER;
            case "gold":
                return Badge.GOLD;
            case "platinum":
                return Badge.PLATINUM;
            case "diamond":
                return Badge.DIAMOND;
            case "master":
                return Badge.MASTER;
            case "challenger":
                return Badge.CHALLENGER;
        }

        return -1;
    }

    public static Spannable getMatchPlayerSpannable(Context context, String matchStatus, String champion) {
        String separator = " " + context.getResources().getString(R.string.as) + " ";

        Spannable text = new SpannableString(matchStatus + separator + champion);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, matchStatus.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(Color.LTGRAY), matchStatus.length(), matchStatus.length() + separator.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), matchStatus.length() + separator.length(), text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }

    public static String getMatchStatus(Context context, boolean win) {
        return win ? context.getResources().getString(R.string.win) :
                context.getResources().getString(R.string.defeat);
    }
}
