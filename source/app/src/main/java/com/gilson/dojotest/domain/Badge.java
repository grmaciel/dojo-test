package com.gilson.dojotest.domain;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * I'm aware of enum evilness has been talked about and to avoid them at all costs
 * (https://www.youtube.com/watch?v=Hzs6OBcvNQE)
 * but for the code in here
 */
public interface Badge {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, CHALLENGER})
    public @interface BadgeLevel {}
    int BRONZE = 1;
    int SILVER = 2;
    int GOLD = 3;
    int PLATINUM = 4;
    int DIAMOND = 5;
    int MASTER = 6;
    int CHALLENGER = 7;
}
