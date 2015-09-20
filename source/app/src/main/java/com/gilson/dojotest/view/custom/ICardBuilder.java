package com.gilson.dojotest.view.custom;

/**
 * Created by Gilson Maciel on 20/09/2015.
 */
public interface ICardBuilder {
    ICardBuilder withMatchStatus(String status);
    ICardBuilder withChampionName(String champion);
    ICardBuilder withMatchDetail(String detail);
    ICardBuilder withBadge(int badgeResId);
    ICardBuilder withClickListener(ICardListener listener);

    void build();
}
