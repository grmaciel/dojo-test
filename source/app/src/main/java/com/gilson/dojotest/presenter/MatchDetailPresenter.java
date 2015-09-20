package com.gilson.dojotest.presenter;

import android.support.annotation.NonNull;

import com.gilson.dojotest.view.MatchDetailView;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.dto.MatchDetailDto;
import com.gilson.dojotest.ws.dto.MatchDto;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public class MatchDetailPresenter {
    private final MatchDetailView view;
    private final RestApi api;

    public MatchDetailPresenter(MatchDetailView view, RestApi api) {
        this.view = view;
        this.api = api;
        this.loadData();
    }

    private void loadData() {
        view.configureToolbar();
        view.showLoading();

        api.queryMatch(view.getMatchId())
                .subscribe(displayMatchInfo());

        api.queryMatchDetail(view.getMatchId())
                .subscribe(new MatchDetailObserver());
    }

    @NonNull
    private Action1<MatchDto> displayMatchInfo() {
        return new Action1<MatchDto>() {
            @Override
            public void call(MatchDto matchDto) {
                view.renderToolbarInfo(matchDto);
            }
        };
    }

    private class MatchDetailObserver implements Observer<MatchDetailDto> {
        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.toString());
            view.hideLoading();
        }

        @Override
        public void onNext(MatchDetailDto matchDetailDto) {
            view.renderMatchDetail(matchDetailDto.performance);
        }
    }
}
