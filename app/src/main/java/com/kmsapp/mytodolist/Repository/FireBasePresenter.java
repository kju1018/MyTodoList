package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.ID.TodoID;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Model.Todo;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FireBasePresenter {
    private FirebaseFirestore mStore;
    private UserView userView;
    private String TAG  =  "asdf";

    private ArrayList<Todo> datas = new ArrayList<>();
    private MutableLiveData<ArrayList<Todo>> liveDatas = new MutableLiveData<>();

    private String strToday = "";

    public FireBasePresenter(UserView userView) {
        mStore = FirebaseFirestore.getInstance();
        this.userView = userView;
    }

    public void TodoUpload(Todo todo){
        userView.loadingStart();
        String getid = mStore.collection("test").document().getId();
        todo.setTodoId(getid);

        mStore.collection("test").document(getid).set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userView.showComplete("추가 완료");
                    userView.loadingEnd();
                }).addOnFailureListener(e -> {
                    userView.showLoadError("할 일 추가 오류");
                    userView.loadingEnd();
                });
    }

    public MutableLiveData<ArrayList<Todo>> TodayTodoLoad(){
        userView.loadingStart();
        mStore.collection("test").addSnapshotListener((value, error) -> {
            if (value != null){
                datas.clear();
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                    if(documentSnapshot != null) {
                        Todo todo = null;
                        LocalDate today = LocalDate.now();
                        strToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        Map<String, Object> snap = documentSnapshot.getData();
                        String repeatComplete = (String) snap.get(TodoID.repeatComplete);

                        if(!repeatComplete.equals(strToday)){
                            String todoId = (String) snap.get(TodoID.todoId);
                            String contents = (String) snap.get(TodoID.contents);
                            String date = (String) snap.get(TodoID.date);
                            boolean repeat = (boolean) snap.get(TodoID.repeat);

                            List repeatDay = (ArrayList) snap.get(TodoID.repeatDay);
                            List repeatDayEn = (ArrayList) snap.get(TodoID.repeatDayEn);
                            String time = (String) snap.get(TodoID.time);


                            if (repeat) {
                                if (repeatDayEn.contains(String.valueOf(today.getDayOfWeek()))) {
                                    todo = new Todo(todoId, contents, strToday, time, repeat, repeatComplete, repeatDay, repeatDayEn);
                                    datas.add(todo);
                                }
                            } else {
                                if (date.equals(strToday)) {
                                    todo = new Todo(todoId, contents, date, time, repeat, repeatComplete, repeatDay, repeatDayEn);
                                    datas.add(todo);
                                }
                            }
                        }
                    }
                }
                liveDatas.setValue(datas);
            }
        });
        userView.loadingEnd();
        return liveDatas;
    }

    public void TodoComplete(Todo todo){
        userView.loadingStart();
        String getid = mStore.collection("complete").document().getId();
        todo.setCompleteId(getid);
        if (todo.isRepeat()) {// 습관이라면
            todo.setRepeatComplete(strToday);

            mStore.collection("test").document(todo.getTodoId()).set(todo, SetOptions.merge())
                    .addOnFailureListener(e -> {
                        userView.showLoadError("다시 시도해 주세요!");
                        userView.loadingEnd();
                    });


            mStore.collection("complete").document(getid).set(todo, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        userView.showComplete("할 일 완료");
                        userView.loadingEnd();
                    }).addOnFailureListener(e -> {
                userView.showLoadError("다시 시도해 주세요!");
                userView.loadingEnd();
            });
        }

        else {
            mStore.collection("test").document(todo.getTodoId()).delete()
                    .addOnFailureListener(e -> {
                        userView.showLoadError("다시 시도해 주세요!");
                        userView.loadingEnd();
                    });

            mStore.collection("complete").document(getid).set(todo, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        userView.showComplete("할 일 완료");
                        userView.loadingEnd();
                    }).addOnFailureListener(e -> {
                userView.showLoadError("다시 시도해 주세요!");
                userView.loadingEnd();
            });
            Log.d(TAG, "TodoComplete: dfd");
        }
    }
}


