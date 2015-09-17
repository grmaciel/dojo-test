package com.gilson.dojotest.presenter;

import android.util.Log;

import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.RestApiFakeImpl;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MainPresenter {
    private MainView view;
    private RestApi api;

    /**
     * I would use dagger 2 to inject the dependencies here
     */
    public MainPresenter(MainView view, RestApi api) {
        this.view = view;
        this.api = api;
    }

    private void loadData() {
        view.showLoading();
        api.queryMatches(-1)
                .subscribe(new Subscriber<List<MatchDto>>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Show message in view
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
