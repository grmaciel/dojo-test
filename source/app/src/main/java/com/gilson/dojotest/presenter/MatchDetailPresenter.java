package com.gilson.dojotest.presenter;

import android.util.Log;

import com.gilson.dojotest.view.MatchDetailView;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.dto.MatchDetailDto;

import rx.Subscriber;

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
        view.showLoading();

        api.queryMatchDetail(view.getMatchId())
                .subscribe(new Subscriber<MatchDetailDto>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "ERRO");
                    }

                    @Override
                    public void onNext(MatchDetailDto matchDetailDto) {
                        Log.d("", "DETAIL" + matchDetailDto.idGame);
                    }
                });
    }
}
