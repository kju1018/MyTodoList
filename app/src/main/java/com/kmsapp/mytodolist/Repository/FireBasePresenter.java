package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.ID.TodoID;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Model.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FireBasePresenter {
    private FirebaseFirestore mStore;
    private UserView userView;
    private String TAG  =  "asdf";

    private ArrayList<Todo> datas = new ArrayList<>();
    private MutableLiveData<ArrayList<Todo>> todoDatas = new MutableLiveData<>();

    private String strToday = "";

    public FireBasePresenter() {
        mStore = FirebaseFirestore.getInstance();
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void TodoUpload(Todo todo){
        userView.loadingStart();
        String getid = mStore.collection("test").document().getId();
        todo.setTodoId(getid);
        todo.setTimestamp(FieldValue.serverTimestamp());

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
                                    todo = new Todo(todoId, contents, "", time, true, repeatComplete, repeatDay, repeatDayEn);
                                    datas.add(todo);
                                }
                            } else {
                                if (date.equals(strToday)) {
                                    todo = new Todo(todoId, contents, date, time, false, repeatComplete, repeatDay, repeatDayEn);
                                    datas.add(todo);
                                }
                            }
                        }
                    }
                }
                todoDatas.setValue(datas);
            }
        });
        userView.loadingEnd();
        return todoDatas;
    }

    public MutableLiveData<ArrayList<Todo>> CompleteTodoLoad(){
        userView.loadingStart();
        mStore.collection("complete")
                .orderBy(TodoID.timestamp, Query.Direction.DESCENDING).addSnapshotListener((value, error) -> {
            if (value != null){
                datas.clear();
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                    if(documentSnapshot != null) {
                        Todo todo = null;
                        Map<String, Object> snap = documentSnapshot.getData();

                        String todoId = (String) snap.get(TodoID.todoId);
                        String contents = (String) snap.get(TodoID.contents);
                        String date = (String) snap.get(TodoID.date);
                        boolean repeat = (boolean) snap.get(TodoID.repeat);
                        String repeatComplete = (String) snap.get(TodoID.repeatComplete);
                        String completeId = (String) snap.get(TodoID.completeId);

                        List repeatDay = (ArrayList) snap.get(TodoID.repeatDay);
                        List repeatDayEn = (ArrayList) snap.get(TodoID.repeatDayEn);
                        String time = (String) snap.get(TodoID.time);
                        Log.d(TAG, "CompleteTodoLoad: "+ contents);
                        todo = new Todo(todoId, completeId, contents, date, time, repeat, repeatComplete, repeatDay, repeatDayEn);
                        datas.add(todo);
                    }
                }
                todoDatas.setValue(datas);
            }

        });
        userView.loadingEnd();
        return todoDatas;
    }



    public void TodoComplete(Todo todo){
        userView.loadingStart();
        String getid = mStore.collection("complete").document().getId();
        todo.setCompleteId(getid);
        todo.setTimestamp(FieldValue.serverTimestamp());
        if(todo.isRepeat()) {// 습관이라면
            todo.setRepeatComplete(strToday);
            todo.setDate(strToday);

            mStore.collection("test").document(todo.getTodoId()).set(todo, SetOptions.merge())
                    .addOnFailureListener(e -> {
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
        }

        mStore.collection("complete").document(getid).set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userView.showComplete("할 일 완료");
                    userView.loadingEnd();
                }).addOnFailureListener(e -> {
            userView.showLoadError("다시 시도해 주세요!");
            userView.loadingEnd();
        });
    }
}


