package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireAuthRepository;
import com.kmsapp.mytodolist.databinding.ActivitySignUpBinding;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class SignUpActivity extends AppCompatActivity implements UserViewListener, TextWatcher {

    private ActivitySignUpBinding activitySignUpBinding;
    private FireAuthRepository authRepository;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        activitySignUpBinding.signupPasswordchkText.addTextChangedListener(this);
        activitySignUpBinding.signupPasswordText.addTextChangedListener(this);

        activitySignUpBinding.signupCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authRepository = new FireAuthRepository(SignUpActivity.this);
                email = activitySignUpBinding.signupEmailText.getText().toString();
                password = activitySignUpBinding.signupPasswordText.getText().toString();
                authRepository.firebaseSignUp(email, password);
            }
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
    }

    @Override
    public void loadingStart() {
        activitySignUpBinding.signUpProgressBar.setVisibility(View.VISIBLE);
        activitySignUpBinding.signUpProgressBar.setIndeterminate(true);

    }

    @Override
    public void loadingEnd() {
        activitySignUpBinding.signUpProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(activitySignUpBinding.signupPasswordText.getText().toString()
                .equals(activitySignUpBinding.signupPasswordchkText.getText().toString())) {
            activitySignUpBinding.passwordWarn.setVisibility(View.GONE);
        }
        else {
            activitySignUpBinding.passwordWarn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}