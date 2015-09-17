package com.gilson.dojotest.view;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public interface MainView extends LoadDataView {
    void renderCardsResume(boolean win,
                           String champion,
                           String matchDetail,
                           int badgeResId);
}
