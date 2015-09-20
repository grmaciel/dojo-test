package com.gilson.dojotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.gilson.dojotest.R;
import com.gilson.dojotest.di.component.DaggerMainPresenterComponent;
import com.gilson.dojotest.di.module.MainPresenterModule;
import com.gilson.dojotest.presenter.MainPresenter;
import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.view.custom.CustomCardGrouper;
import com.gilson.dojotest.view.custom.ICardListener;
import com.gilson.dojotest.ws.dto.MatchDto;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ICardListener, MainView {

    @Bind(R.id.cardGrouper)
    CustomCardGrouper cardGrouper;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Inject
    MainPresenter presenter;

    private MatchDto matchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();
    }

    private void inject() {
        DaggerMainPresenterComponent.builder().
                applicationComponent(getApplicationComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onCardDragged() {
        startActivity(new Intent(MainActivity.this, MatchHistoryActivity.class));
    }

    @Override
    public void onCardClick() {
        startActivity(MatchDetailActivity.getIntent(this,
                matchCard.id,
                ViewUtil.getBadgeResource(matchCard.totalPerformance)));
    }

    @Override
    public void renderCardsResume(MatchDto match) {
        this.matchCard = match;
        cardGrouper.setVisibility(View.VISIBLE);
        cardGrouper
                .withBadge(ViewUtil.getBadgeResource(match.totalPerformance))
                .withChampionName(match.champion)
                .withMatchStatus(ViewUtil.getMatchStatus(this, match.win))
                .withMatchDetail(getMatchDetail(match))
                .withClickListener(this)
                .build();
    }

    private String getMatchDetail(MatchDto match) {
        return match.gameMode + " • " + match.lane + " " +
                this.getResources().getString(R.string.lane);
    }

    @Override
    public void showLoading() {
        rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rlProgress.setVisibility(View.GONE);
    }
}
