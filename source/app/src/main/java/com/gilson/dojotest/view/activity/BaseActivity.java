package com.gilson.dojotest.view.activity;

import android.support.v7.app.AppCompatActivity;

import com.gilson.dojotest.di.AndroidApplication;
import com.gilson.dojotest.di.component.ApplicationComponent;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).component();
    }
}
