package com.gilson.dojotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gilson.dojotest.R;
import com.gilson.dojotest.di.component.DaggerMatchPresenterComponent;
import com.gilson.dojotest.di.module.MatchPresenterModule;
import com.gilson.dojotest.presenter.MatchHistoryPresenter;
import com.gilson.dojotest.view.MatchView;
import com.gilson.dojotest.view.adapter.CustomItemDecorator;
import com.gilson.dojotest.view.adapter.IOnItemClickListener;
import com.gilson.dojotest.view.adapter.MatchHistoryAdapter;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchHistoryActivity extends BaseActivity implements MatchView {
    @Bind(R.id.matchInfoRecycler)
    RecyclerView matchInfoRecycler;
    @Inject
    MatchHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        ButterKnife.bind(this);

        DaggerMatchPresenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .matchPresenterModule(new MatchPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderMatches(List<MatchDto> matches) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        matchInfoRecycler.setLayoutManager(llm);
        matchInfoRecycler.setAdapter(new
                MatchHistoryAdapter(this, matches,
                getOnItemClickListener()));
        matchInfoRecycler.addItemDecoration(new CustomItemDecorator(this));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void hideLoading() {

    }

    public IOnItemClickListener<MatchDto> getOnItemClickListener() {
        return new IOnItemClickListener<MatchDto>() {
            @Override
            public void onClick(View view, MatchDto data) {
                startActivity(MatchDetailActivity
                        .getIntent(MatchHistoryActivity.this, data.id));
            }
        };
    }
}
