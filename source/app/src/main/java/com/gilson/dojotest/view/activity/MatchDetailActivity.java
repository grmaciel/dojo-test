package com.gilson.dojotest.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gilson.dojotest.R;
import com.gilson.dojotest.presenter.MatchDetailPresenter;
import com.gilson.dojotest.view.MatchDetailView;
import com.gilson.dojotest.view.adapter.MatchDetailAdapter;
import com.gilson.dojotest.view.adapter.MatchHistoryAdapter;
import com.gilson.dojotest.ws.RestApiFakeImpl;
import com.gilson.dojotest.ws.dto.MatchDetailDto;
import com.gilson.dojotest.ws.dto.PerformanceDto;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchDetailActivity extends BaseActivity implements MatchDetailView {
    private static final String PARAM_ID_MATCH = "PARAM_ID_MATCH";
    private static final String PARAM_ID_BADGE_RES= "PARAM_BADGE_RES";

    @Bind(R.id.imgBadgeDetail)
    ImageView imgBadge;
    @Bind(R.id.matchDetailRecycler)
    RecyclerView matchDetailRecycler;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    private long idMatch;

    public static Intent getIntent(Context context, Long idMatch, int badgeResId) {
        Intent intent = new Intent(context, MatchDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(PARAM_ID_MATCH, idMatch);
        bundle.putInt(PARAM_ID_BADGE_RES, badgeResId);

        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        this.idMatch = bundle.getLong(PARAM_ID_MATCH);

        imgBadge.setImageResource(bundle.getInt(PARAM_ID_BADGE_RES));

        MatchDetailPresenter presenter = new MatchDetailPresenter(this, new RestApiFakeImpl());
    }

    @Override
    public void renderMatchDetail(List<PerformanceDto> data) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        matchDetailRecycler.setLayoutManager(llm);
        matchDetailRecycler.setAdapter(new MatchDetailAdapter(this, data));
    }

    @Override
    public long getMatchId() {
        return idMatch;
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
