package com.kmsapp.mytodolist.Repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kmsapp.mytodolist.Interface.UserView;

public class FireAuthRepository {
    private FirebaseAuth mAuth;
    private UserView loginListenr;

    public FireAuthRepository(UserView loginListenr) {
        mAuth = FirebaseAuth.getInstance();
        this.loginListenr = loginListenr;
    }

    public void firebaseSignUp(String email, String password){
        loginListenr.loadingStart();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginListenr.showComplete(user.getEmail() + "로 회원가입 완료");
                            loginListenr.loadingEnd();
                        }else {
                            if(password.length() < 8){
                                loginListenr.showLoadError("비밀번호를 8자리 이상 입력해주세요.");
                                loginListenr.loadingEnd();
                            }else {
                                loginListenr.showLoadError("회원 가입 실패");
                                loginListenr.loadingEnd();
                            }
                        }
                    }
                });
    }

    public void firebaseLogin(String email, String password) {
        loginListenr.loadingStart();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginListenr.showComplete(user.getEmail() + "로 로그인 완료");
                            loginListenr.loadingEnd();
                        }else {
                            loginListenr.showLoadError("로그인 실패");
                            loginListenr.loadingEnd();
                        }
                    }
                });
    }
}
