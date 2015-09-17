package com.gilson.dojotest.di.module;

import com.gilson.dojotest.presenter.MainPresenter;
import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.ws.RestApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@Module
public class MainPresenterModule {
    private final MainView mainView;

    public MainPresenterModule(MainView mainView) {
        this.mainView = mainView;
    }

    @Provides
    @PerActivity
    MainPresenter provideMainPresenter(RestApi api) {
        return new MainPresenter(mainView, api);
    }
}
