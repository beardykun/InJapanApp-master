package com.example.user.injapanapp.ui.generalActivity;


public interface IGeneralView {

    void showProgress();

    void hideProgress();

    void showError(String error, int... code);

    void showError(int error, int... code);
}
