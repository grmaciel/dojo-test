package com.gilson.dojotest.di.module;

import com.gilson.dojotest.presenter.MainPresenter;
import com.gilson.dojotest.presenter.MatchHistoryPresenter;
import com.gilson.dojotest.view.MatchView;
import com.gilson.dojotest.ws.RestApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@Module
public class MatchPresenterModule {
    private final MatchView matchView;

    public MatchPresenterModule(MatchView mainView) {
        this.matchView = mainView;
    }

    @Provides
    @PerActivity
    MatchHistoryPresenter provideMainPresenter(RestApi api) {
        return new MatchHistoryPresenter(matchView, api);
    }
}
