package com.gilson.dojotest.di.component;

import android.content.Context;

import com.gilson.dojotest.di.module.ApplicationModule;
import com.gilson.dojotest.ws.RestApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@Singleton
@Component(
        modules = {
                ApplicationModule.class
        })
public interface ApplicationComponent {
    Context context();

    RestApi restApi();
}
