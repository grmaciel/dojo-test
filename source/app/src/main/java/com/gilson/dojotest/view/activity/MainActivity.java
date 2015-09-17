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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        cardGrouper.configureCard(
                ViewUtil.getMatchStatus(this, match.win),
                match.champion,
                match.gameMode + " • " + match.lane + " " +
                        this.getResources().getString(R.string.lane),
                ViewUtil.getBadgeResource(match.totalPerformance),
                this);
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
