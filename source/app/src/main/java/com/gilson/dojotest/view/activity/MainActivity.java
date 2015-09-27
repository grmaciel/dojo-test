package com.gilson.dojotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gilson.dojotest.R;
import com.gilson.dojotest.di.component.DaggerMainPresenterComponent;
import com.gilson.dojotest.di.module.MainPresenterModule;
import com.gilson.dojotest.presenter.MainPresenter;
import com.gilson.dojotest.presenter.MatchHistoryPresenter;
import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.view.MatchView;
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.view.adapter.IOnItemClickListener;
import com.gilson.dojotest.view.adapter.MatchHistoryAdapter;
import com.gilson.dojotest.view.adapter.MatchHistoryDecorator;
import com.gilson.dojotest.view.custom.ICardListener;
import com.gilson.dojotest.ws.RestApiFakeImpl;
import com.gilson.dojotest.ws.dto.MatchDto;
import com.sothree.slidinguppanel.SlidingPanel;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ICardListener, MainView, MatchView {
    @Bind(R.id.matchInfoRecycler)
    RecyclerView matchInfoRecycler;

    //    @Bind(R.id.cardGrouper)
//    CustomCardGrouper cardGrouper;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Inject
    MainPresenter presenter;
    @Bind(R.id.slidingLayout)
    SlidingPanel slidingLayout;
    @Bind(R.id.btnGG)
    ImageView btnGG;

    private MatchDto matchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();
        MatchHistoryPresenter presenter = new MatchHistoryPresenter(this, new RestApiFakeImpl());

//        SlidingUpPanelLayout.PanelState.
        slidingLayout.setPanelSlideListener(new SlidingPanel.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                if (v > 0.15) {
                    View childView = matchInfoRecycler.getChildAt(0);

                    if (childView instanceof RelativeLayout) {
                        RelativeLayout layout = (RelativeLayout) childView;
                        layout.getChildAt(0).setVisibility(View.INVISIBLE);
                        layout.getChildAt(1).setVisibility(View.INVISIBLE);
                    }


//                    matchInfoRecycler.removeViewAt(0);
//                    matchInfoRecycler.removeViewAt(1);
                }
            }

            @Override
            public void onPanelCollapsed(View view) {
                View childView = matchInfoRecycler.getChildAt(0);

                if (childView instanceof RelativeLayout) {
                    RelativeLayout layout = (RelativeLayout) childView;
                    layout.getChildAt(0).setVisibility(View.VISIBLE);
                    layout.getChildAt(1).setVisibility(View.VISIBLE);
                }

//                RelativeLayout layout = (RelativeLayout) matchInfoRecycler.getChildAt(0);
//                layout.getChildAt(0).setVisibility(View.VISIBLE);
//                layout.getChildAt(1).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPanelExpanded(View view) {
                Log.d("", "EXPANDIU");
            }

            @Override
            public void onPanelAnchored(View view) {
                Log.d("", "ANCOROU");
            }

            @Override
            public void onPanelHidden(View view) {

            }
        });
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
//        cardGrouper.setVisibility(View.VISIBLE);
//        cardGrouper
//                .withBadge(ViewUtil.getBadgeResource(match.totalPerformance))
//                .withChampionName(match.champion)
//                .withMatchStatus(ViewUtil.getMatchStatus(this, match.win))
//                .withMatchDetail(getMatchDetail(match))
//                .withClickListener(this)
//                .build();
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

    @Override
    public void renderMatches(List<MatchDto> matches) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        matchInfoRecycler.setLayoutManager(llm);
        matchInfoRecycler.setAdapter(new
                MatchHistoryAdapter(this, matches,
                getOnItemClickListener()));
        matchInfoRecycler.addItemDecoration(new MatchHistoryDecorator(this));
    }

    public IOnItemClickListener<MatchDto> getOnItemClickListener() {
        return new IOnItemClickListener<MatchDto>() {
            @Override
            public void onClick(View view, MatchDto data) {
                startActivity(MatchDetailActivity
                        .getIntent(MainActivity.this,
                                data.id,
                                ViewUtil.getBadgeResource(data.totalPerformance)));
            }
        };
    }

    @OnClick(R.id.btnGG)
    public void onBtnClicked(View view) {
        ScaleAnimation fade_in =  new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(200);     // animation duration in milliseconds
//        fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
        view.startAnimation(fade_in);

        view.startAnimation(fade_in);
    }

}
