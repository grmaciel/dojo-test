package com.gilson.dojotest.di.component;

import com.gilson.dojotest.di.module.MatchPresenterModule;
import com.gilson.dojotest.di.module.PerActivity;
import com.gilson.dojotest.view.activity.MainActivity;
import com.gilson.dojotest.view.activity.MatchHistoryActivity;

import dagger.Component;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MatchPresenterModule.class)
public interface MatchPresenterComponent {
    void inject(MatchHistoryActivity activity);
}
