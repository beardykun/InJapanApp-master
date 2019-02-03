package com.example.user.injapanapp.ui.general_activity;


public interface IGeneralView {

    void showProgress();

    void hideProgress();

    void showError(String error, int... code);

    void showError(int error, int... code);
}
