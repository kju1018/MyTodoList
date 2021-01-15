package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireAuthRepository;
import com.kmsapp.mytodolist.databinding.ActivityLoginBinding;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity implements UserView {

    private ActivityLoginBinding activityLoginBinding;
    private FireAuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        authRepository = new FireAuthRepository(this);

        activityLoginBinding.loginLoginBtn.setOnClickListener(view -> {
            String email = activityLoginBinding.loginEmailText.getText().toString();
            String password = activityLoginBinding.loginPasswordText.getText().toString();

            authRepository.firebaseLogin(email, password);
        });

        activityLoginBinding.loginSignupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void showLoadError(String message) {
        ToastUtil.toastPrint(this, message);
    }

    @Override
    public void showComplete(String message) {
        ToastUtil.toastPrint(this, message);
        finish();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadingStart() {
        activityLoginBinding.loginProgressBar.setVisibility(View.VISIBLE);
        activityLoginBinding.loginProgressBar.setIndeterminate(true);
    }

    @Override
    public void loadingEnd() {
        activityLoginBinding.loginProgressBar.setVisibility(View.GONE);
    }
}