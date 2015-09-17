package com.gilson.dojotest.view.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.gilson.dojotest.di.AndroidApplication;
import com.gilson.dojotest.di.component.ApplicationComponent;
import com.gilson.dojotest.view.LoadDataView;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements LoadDataView {
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).component();
    }

    @Override
    public void showError(String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(e);
        builder.create().show();
    }
}
