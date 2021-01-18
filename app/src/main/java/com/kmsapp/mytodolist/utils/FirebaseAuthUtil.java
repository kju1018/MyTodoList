package com.kmsapp.mytodolist.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthUtil {


    public static FirebaseAuth getAuth(){
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser(){
        return getAuth().getCurrentUser();
    }




}
