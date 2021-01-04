package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.Model.Todo;

public class FireBaseRepository {
    private FirebaseFirestore mStore;
    private String TAG  =  "asdf";

    public FireBaseRepository() {
        mStore = FirebaseFirestore.getInstance();
    }

    public void TodoUpload(Todo todo){
        String getid = mStore.collection("test").document().getId();
        mStore.collection("test").document(getid).set(todo, SetOptions.merge());
    }
}
