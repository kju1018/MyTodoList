package com.kmsapp.mytodolist.Interface;

public interface UserView {
    void showLoadError(String message);
    void showComplete(String message);
    void loadingStart();
    void loadingEnd();

}
