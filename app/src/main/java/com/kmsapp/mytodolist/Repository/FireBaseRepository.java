package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.ID.FirebaseID;
import com.kmsapp.mytodolist.ID.TodoID;
import com.kmsapp.mytodolist.Interface.FirebaseListener;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.utils.FirebaseAuthUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class FireBaseRepository {
    private FirebaseFirestore db;
    private UserViewListener userViewListener;
    private FirebaseListener firebaseListener;

    private ArrayList<Todo> todoDatas = new ArrayList<>();
    private MutableLiveData<ArrayList<Todo>> todoLiveDatas = new MutableLiveData<>();
    private ArrayList<EventDay> eventDatas = new ArrayList<>();

    private String strToday = "";
    private String userId = FirebaseAuthUtil.getUser().getUid();

    public FireBaseRepository() {
        db = FirebaseFirestore.getInstance();
        LocalDate today = LocalDate.now();
        strToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setUserViewListener(UserViewListener userViewListener) {
        this.userViewListener = userViewListener;
    }

    public void setFirebaseListener(FirebaseListener firebaseListener){
        this.firebaseListener = firebaseListener;
    }

    public CollectionReference todoCollectionReference(){
        return db.collection(FirebaseID.user)
                .document(userId)
                .collection(FirebaseID.todoCollection);
    }

    public CollectionReference completeCollectionReference(){
        return db.collection(FirebaseID.user)
                .document(userId)
                .collection(FirebaseID.completeCollection);
    }


    public MutableLiveData<ArrayList<Todo>> todayTodoLoad(){

        userViewListener.loadingStart();
        LocalDate today = LocalDate.now();
        todoCollectionReference()
                .whereNotEqualTo(TodoID.repeatComplete, strToday)
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                        todoDatas.clear();
                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                            if(documentSnapshot != null) {
                                Todo todo = documentSnapshot.toObject(Todo.class);

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
                        todoLiveDatas.setValue(todoDatas);
                    }
                });
        userViewListener.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> allTodoLoad() {
        userViewListener.loadingStart();
        todoCollectionReference()
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
        userViewListener.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> repeatTodoLoad() {


        userViewListener.loadingStart();
        todoCollectionReference()
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
        userViewListener.loadingEnd();
        return todoLiveDatas;
    }

    public MutableLiveData<ArrayList<Todo>> completeTodoLoad(){
        completeCollectionReference()
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
        userViewListener.loadingStart();
        String getid = "";
        if(todo.getTodoId() != null){
            getid = todo.getTodoId();
        }else {
            getid = todoCollectionReference()
                    .document()
                    .getId();
        }
        todo.setTodoId(getid);
        todo.setTimestamp(Timestamp.now());

        todoCollectionReference()
                .document(getid)
                .set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userViewListener.showComplete("추가 완료");
                    userViewListener.loadingEnd();
                }).addOnFailureListener(e -> {
            userViewListener.showLoadError("할 일 추가 오류");
            userViewListener.loadingEnd();
        });
    }



    public void todoComplete(Todo todo){
        userViewListener.loadingStart();


        if(todo.isRepeat()) {// 습관이라면
            todo.setRepeatComplete(strToday);
            todo.setDate(strToday);

            todoCollectionReference()
                    .document(todo.getTodoId())
                    .set(todo, SetOptions.merge())
                    .addOnFailureListener(e -> {
                        userViewListener.showLoadError("다시 시도해 주세요!");
                        userViewListener.loadingEnd();
                    });
        } else {
            todo.setTimestamp(Timestamp.now());
            todoCollectionReference()
                    .document(todo.getTodoId())
                    .delete()
                    .addOnFailureListener(e -> {
                        userViewListener.showLoadError("다시 시도해 주세요!");
                        userViewListener.loadingEnd();
                    });
        }
        String getid = completeCollectionReference().document().getId();

        todo.setCompleteId(getid);

        completeCollectionReference()
                .document(getid)
                .set(todo, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    userViewListener.showComplete("할 일 완료");
                    userViewListener.loadingEnd();
                }).addOnFailureListener(e -> {
            userViewListener.showLoadError("다시 시도해 주세요!");
            userViewListener.loadingEnd();
        });
    }

    public void deleteTodo(Todo todo) {
        todoCollectionReference()
                .document(todo.getTodoId()).delete()
                .addOnSuccessListener(aVoid -> {
                    userViewListener.showComplete("삭제 되었습니다.");
                })
                .addOnFailureListener(e -> {
                    userViewListener.showLoadError("다시 시도해 주세요!");
                    userViewListener.loadingEnd();
                });
    }

    public void loadEvent() {

        userViewListener.loadingStart();
        todoCollectionReference()
                .whereEqualTo("repeat", false)
                .addSnapshotListener((value, error) -> {
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
                    firebaseListener.onSuccess(eventDatas);
                    userViewListener.loadingEnd();
                });

    }

    public MutableLiveData<ArrayList<Todo>> loadSelectTodo(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(calendar.getTime());

        todoCollectionReference()
                .whereEqualTo("date",date)
                .whereEqualTo("repeat", false)
                .addSnapshotListener((value, error) -> {
                    todoDatas.clear();
                    for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                        if(documentSnapshot != null) {
                            Todo todo = documentSnapshot.toObject(Todo.class);
                            todoDatas.add(todo);
                        }
                    }
                    todoLiveDatas.setValue(todoDatas);
                });

        return todoLiveDatas;
    }
}


