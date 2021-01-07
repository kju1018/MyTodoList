package com.kmsapp.mytodolist.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kmsapp.mytodolist.ID.TodoID;
import com.kmsapp.mytodolist.Model.Todo;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FireBaseRepository {
    private FirebaseFirestore mStore;
    private String TAG  =  "asdf";

    private ArrayList<Todo> datas = new ArrayList<>();
    private MutableLiveData<ArrayList<Todo>> liveDatas = new MutableLiveData<>();

    public FireBaseRepository() {
        mStore = FirebaseFirestore.getInstance();
    }

    public void TodoUpload(Todo todo){
        String getid = mStore.collection("test").document().getId();
        mStore.collection("test").document(getid).set(todo, SetOptions.merge());
    }

    public MutableLiveData<ArrayList<Todo>> TodayTodoLoad(){
        mStore.collection("test").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){
                    datas.clear();
                    for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                        Todo todo = null;
                        Map<String, Object> snap = documentSnapshot.getData();
                        String contents = (String) snap.get(TodoID.contents);
                        String date = (String) snap.get(TodoID.date);
                        boolean repeat = (boolean) snap.get(TodoID.repeat);
                        List repeatDay = (ArrayList) snap.get(TodoID.repeatDay);
                        List repeatDayEn = (ArrayList) snap.get(TodoID.repeatDayEn);
                        String time = (String) snap.get(TodoID.time);

                        LocalDate today = LocalDate.now();
                        String strToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if(repeat == true){
                            if(repeatDayEn.contains(String.valueOf(today.getDayOfWeek()))){
                                todo = new Todo(contents, strToday, time, repeat, repeatDay, repeatDayEn);
                                datas.add(todo);
                            }
                        }else {
                            if(date.equals(strToday)){
                                todo = new Todo(contents, date, time, repeat, repeatDay, repeatDayEn);
                                datas.add(todo);
                            }
                        }

                    }
                    liveDatas.setValue(datas);
                }
            }
        });

        return liveDatas;
    }
}



/*

2. MutableLivedate로 리턴하고
엑티비티에서는 LiveDate<List<Todo>>로 선언

3. 여기서 LiveData도 만들어주고
LiveData = Mutabledata 이런식으로 해줌


 */
