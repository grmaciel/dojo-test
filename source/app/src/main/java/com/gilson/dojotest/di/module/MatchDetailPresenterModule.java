package com.gilson.dojotest.di.module;

import com.gilson.dojotest.presenter.MatchDetailPresenter;
import com.gilson.dojotest.view.MatchDetailView;
import com.gilson.dojotest.ws.RestApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
@Module
public class MatchDetailPresenterModule {
    private final MatchDetailView matchView;

    public MatchDetailPresenterModule(MatchDetailView matchView) {
        this.matchView = matchView;
    }

    @Provides
    @PerActivity
    MatchDetailPresenter provideDetailPresenter(RestApi api) {
        return new MatchDetailPresenter(matchView, api);
    }
}
