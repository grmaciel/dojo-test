package com.gilson.dojotest.di.component;

import com.gilson.dojotest.di.module.MainPresenterModule;
import com.gilson.dojotest.di.module.PerActivity;
import com.gilson.dojotest.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainPresenterModule.class)
public interface MainPresenterComponent {
    void inject(MainActivity activity);
}
