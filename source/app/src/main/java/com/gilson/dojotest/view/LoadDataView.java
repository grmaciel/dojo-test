package com.gilson.dojotest.view;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public interface LoadDataView {
    void showLoading();

    void showError(String error);

    void hideLoading();
}
