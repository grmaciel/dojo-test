package com.gilson.dojotest.presenter;

import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MainPresenter {
    private MainView view;
    private RestApi api;

    public MainPresenter(MainView view, RestApi api) {
        this.view = view;
        this.api = api;
    }

    private void loadData() {
        view.showLoading();
        api.queryMatchs(-1)
                .subscribe(new Subscriber<List<MatchDto>>() {
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
                    public void onNext(List<MatchDto> matchDtos) {
                        MatchDto firstMatch = matchDtos.get(0);
                        view.renderCardsResume(firstMatch);
                    }
                });
    }


    public void onResume() {
        loadData();
    }
}
