package com.gilson.dojotest.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gilson.dojotest.R;
import com.gilson.dojotest.di.component.DaggerMainPresenterComponent;
import com.gilson.dojotest.di.module.MainPresenterModule;
import com.gilson.dojotest.presenter.MainPresenter;
import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.view.adapter.IOnItemClickListener;
import com.gilson.dojotest.view.adapter.MatchHistoryAdapter;
import com.gilson.dojotest.view.adapter.MatchHistoryDecorator;
import com.gilson.dojotest.ws.dto.MatchDto;
import com.sothree.slidinguppanel.SlidingPanel;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {
    @Bind(R.id.matchInfoRecycler)
    RecyclerView matchInfoRecycler;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.slidingLayout)
    SlidingPanel slidingLayout;
    @Bind(R.id.btnGG)
    ImageView btnGG;
    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();
        configureSlidePanel();
    }

    private void inject() {
        DaggerMainPresenterComponent.builder().
                applicationComponent(getApplicationComponent())
                .mainPresenterModule(new MainPresenterModule(this))
                .build()
                .inject(this);
    }

    private void configureSlidePanel() {
        slidingLayout.setCoveredFadeColor(0);
        slidingLayout.setPanelSlideListener(new SlidingPanel.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                View childView = matchInfoRecycler.getChildAt(0);
                changeGroupCardsVisibility(childView, View.INVISIBLE);
            }

            @Override
            public void onPanelCollapsed(View view) {
                View childView = matchInfoRecycler.getChildAt(0);
                changeGroupCardsVisibility(childView, View.VISIBLE);
            }

            @Override
            public void onPanelExpanded(View view) {
            }

            @Override
            public void onPanelAnchored(View view) {
            }

            @Override
            public void onPanelHidden(View view) {
            }
        });
    }

    private void changeGroupCardsVisibility(View childView, int invisible) {
        if (childView instanceof RelativeLayout) {
            RelativeLayout layout = (RelativeLayout) childView;
            layout.getChildAt(0).setVisibility(invisible);
            layout.getChildAt(1).setVisibility(invisible);
        }
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
                if (slidingLayout.getPanelState() == SlidingPanel.PanelState.COLLAPSED) {
                    slidingLayout.setPanelState(SlidingPanel.PanelState.EXPANDED);
                } else {
                    startActivity(MatchDetailActivity
                            .getIntent(MainActivity.this,
                                    data.id,
                                    ViewUtil.getBadgeResource(data.totalPerformance)));
                }
            }
        };
    }

    @OnClick(R.id.btnGG)
    public void onBtnClicked(View view) {
        ScaleAnimation scale = new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(200);
        view.startAnimation(scale);
    }
}
