package com.gilson.dojotest.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.gilson.dojotest.view.custom.IOnCardDragListener;
import com.gilson.dojotest.ws.RestApiFakeImpl;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IOnCardDragListener, MainView {

    @Bind(R.id.cardGrouper)
    CustomCardGrouper cardGrouper;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Inject
    MainPresenter presenter;

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
    public void renderCardsResume(boolean win, String champion, String matchDetail, int badgeResId) {
        cardGrouper.setVisibility(View.VISIBLE);
        cardGrouper.configureCard(
                ViewUtil.getMatchStatus(this, win),
                champion,
                matchDetail + " " +
                        this.getResources().getString(R.string.lane),
                badgeResId,
                this);
    }

    @Override
    public void showLoading() {
        rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void hideLoading() {
        rlProgress.setVisibility(View.GONE);
    }
}
