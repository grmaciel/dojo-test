package com.gilson.dojotest.di.module;

import android.content.Context;

import com.gilson.dojotest.di.AndroidApplication;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.RestApiFakeImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    RestApi providePromotionRepository() {
        return new RestApiFakeImpl();
    }
}
