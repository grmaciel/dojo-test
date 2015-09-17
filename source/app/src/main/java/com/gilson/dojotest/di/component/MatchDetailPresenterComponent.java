package com.gilson.dojotest.di.component;

import com.gilson.dojotest.di.module.MatchDetailPresenterModule;
import com.gilson.dojotest.di.module.PerActivity;
import com.gilson.dojotest.view.activity.MatchDetailActivity;

import dagger.Component;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MatchDetailPresenterModule.class)
public interface MatchDetailPresenterComponent {
    void inject(MatchDetailActivity activity);
}
