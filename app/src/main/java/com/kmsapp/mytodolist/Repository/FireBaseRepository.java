package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.ID.TodoID;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.model.Todo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class FireBaseRepository {
    private FirebaseFirestore db;
    private UserView userView;
    private String TAG  =  "asdf";

    private ArrayList<Todo> todoDatas = new ArrayList<>();
    private MutableLiveData<ArrayList<Todo>> todoLiveDatas = new MutableLiveData<>();
    private ArrayList<EventDay> eventDatas = new ArrayList<>();
    private MutableLiveData<ArrayList<EventDay>> eventLiveDatas = new MutableLiveData<>();

    private String strToday = "";

    public FireBaseRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }


    public MutableLiveData<ArrayList<Todo>> todayTodoLoad(){
        userView.loadingStart();
        db.collection("test").addSnapshotListener((value, error) -> {
            if (value != null){
                todoDatas.clear();
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                    if(documentSnapshot != null) {
                        Todo todo = documentSnapshot.toObject(Todo.class);
                        LocalDate today = LocalDate.now();
                        strToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        String repeatComplete = todo.getRepeatComplete();
                        Log.d(TAG, "todayTodoLoad: "+ repeatComplete);
                        if(!repeatComplete.equals(strToday)){
                            if (todo.isRepeat()) {
                                if ((todo.getRepeatDayEn()).contains(String.valueOf(today.getDayOfWeek()))) {
                                    todoDatas.add(todo);
                                }
                            } else {
                                if (todo.getDate().equals(strToday)) {
                                    todoDatas.add(todo);
                                }
                            }
                        }
                    }
                }
                todoLiveDatas.setValue(todoDatas);
            }
        });
        userView.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> allTodoLoad() {
        userView.loadingStart();
        db.collection("test").addSnapshotListener((value, error) -> {
            if (value != null){
                todoDatas.clear();
                for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                    if(documentSnapshot != null) {
                        Todo todo = documentSnapshot.toObject(Todo.class);
                        todoDatas.add(todo);
                    }
                }
                todoLiveDatas.setValue(todoDatas);
            }
        });
        userView.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> repeatTodoLoad() {

        userView.loadingStart();
        db.collection("test")
                .whereEqualTo(TodoID.repeat, true)
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                        todoDatas.clear();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            if(documentSnapshot != null) {
                                Todo todo = documentSnapshot.toObject(Todo.class);
                                todoDatas.add(todo);

                            }
                        }
                        todoLiveDatas.setValue(todoDatas);
                    }
                });
        userView.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> completeTodoLoad(){
        db.collection("complete")
                .orderBy(TodoID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                        todoDatas.clear();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            if(documentSnapshot != null) {
                                Todo todo = documentSnapshot.toObject(Todo.class);
                                todoDatas.add(todo);
                            }
                        }
                        todoLiveDatas.setValue(todoDatas);
                    }

                });
        return todoLiveDatas;
    }

    public void todoUpload(Todo todo){
        userView.loadingStart();
        String getid = db.collection("test").document().getId();
        todo.setTodoId(getid);
        todo.setTimestamp(Timestamp.now());
        Log.d(TAG, "todoUpload: "+ strToday);
        db.collection("test").document(getid).set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userView.showComplete("추가 완료");
                    userView.loadingEnd();
                }).addOnFailureListener(e -> {
            userView.showLoadError("할 일 추가 오류");
            userView.loadingEnd();
        });
    }



    public void todoComplete(Todo todo){
        userView.loadingStart();
        String getid = db.collection("complete").document().getId();
        todo.setCompleteId(getid);
        todo.setTimestamp(Timestamp.now());
        if(todo.isRepeat()) {// 습관이라면
            todo.setRepeatComplete(strToday);
            todo.setDate(strToday);

            db.collection("test").document(todo.getTodoId()).set(todo, SetOptions.merge())
                    .addOnFailureListener(e -> {
                        userView.showLoadError("다시 시도해 주세요!");
                        userView.loadingEnd();
                    });
        }

        else {
            db.collection("test").document(todo.getTodoId()).delete()
                    .addOnFailureListener(e -> {
                        userView.showLoadError("다시 시도해 주세요!");
                        userView.loadingEnd();
                    });
        }

        db.collection("complete").document(getid).set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userView.showComplete("할 일 완료");
                    userView.loadingEnd();
                }).addOnFailureListener(e -> {
            userView.showLoadError("다시 시도해 주세요!");
            userView.loadingEnd();
        });
    }

    public MutableLiveData<ArrayList<EventDay>> loadEvent() {
        userView.loadingStart();
        db.collection("test")
                .whereEqualTo("repeat", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        eventDatas.clear();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            if(documentSnapshot != null) {
                                Todo todo = documentSnapshot.toObject(Todo.class);
                                LocalDate todoDate = LocalDate.parse(todo.getDate());
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(todoDate.getYear(), todoDate.getMonthValue() - 1, todoDate.getDayOfMonth());

                                EventDay eventDay = new EventDay(calendar, R.drawable.three_icons);
                                eventDatas.add(eventDay);
                            }
                        }
                        eventLiveDatas.setValue(eventDatas);
                        userView.loadingEnd();
                    }
                });
        Log.d(TAG, "loadEvent: " + eventDatas.size());

        return eventLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> loadSelectTodo(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());

        Log.d(TAG, "loadSelectTodo: "+ date);
        db.collection("test")
                .whereEqualTo("date",date)
                .whereEqualTo("repeat", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        todoDatas.clear();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            if(documentSnapshot != null) {
                                Todo todo = documentSnapshot.toObject(Todo.class);
                                todoDatas.add(todo);
                            }
                        }
                        Log.d(TAG, "loadSelectTodo: "+ todoDatas.size());

                        todoLiveDatas.setValue(todoDatas);
                    }
                });

        return todoLiveDatas;
    }
}


