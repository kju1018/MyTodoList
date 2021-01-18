package com.kmsapp.mytodolist.Interface;

public interface UserViewListener {
    void showLoadError(String message);
    void showComplete(String message);
    void loadingStart();
    void loadingEnd();

}
