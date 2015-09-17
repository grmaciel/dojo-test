package com.gilson.dojotest.di;

import android.app.Application;

import com.gilson.dojotest.di.component.ApplicationComponent;
import com.gilson.dojotest.di.component.DaggerApplicationComponent;
import com.gilson.dojotest.di.module.ApplicationModule;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class AndroidApplication extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initializaInjector();
    }

    private void initializaInjector() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }
}
