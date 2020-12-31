package com.kmsapp.mytodolist.View;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.internal.ParcelableSparseBooleanArray;
import com.kmsapp.mytodolist.Interface.Repeat_Listener;
import com.kmsapp.mytodolist.R;

import java.util.ArrayList;
import java.util.List;


public class Repeat_Dialog extends DialogFragment {

    private static final String CHECKED = "checked";

    private ListView listView;
    private Button cancle, confirm;

    private ArrayAdapter<String> arrayAdapter;
    private List dayotweek = new ArrayList();

    private Repeat_Listener repeat_listener;
    private String checked = "";


    public Repeat_Dialog() {

    }

    public static Repeat_Dialog newInstance() {
        Repeat_Dialog fragment = new Repeat_Dialog();
        return fragment;
    }

    public static Repeat_Dialog newInstance(String checkN) {
        Repeat_Dialog fragment = new Repeat_Dialog();
        Bundle args = new Bundle();
        args.putString(CHECKED, checkN);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checked = getArguments().getString(CHECKED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repeat_dialog, container, false);

        confirm = view.findViewById(R.id.repeat_confirm);
        cancle = view.findViewById(R.id.repeat_cancle);
        listView = view.findViewById(R.id.todo_repeat_listview);

        dayotweek.add("일");//1
        dayotweek.add("월");//2
        dayotweek.add("화");//3
        dayotweek.add("수");//4
        dayotweek.add("목");//5
        dayotweek.add("금");//6
        dayotweek.add("토");//7

        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,dayotweek);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);
        if(checked.length() != 0){
            int position;
            for (int i = 0; i < checked.length(); i++) {
                position = Integer.parseInt(String.valueOf(checked.charAt(i)))-1;
                listView.setItemChecked(position,true);
            }
        }
//        listView.setItemChecked();
//checkedItems.size()은 true의 개수
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                //TODO 일단은 스트링으로 넘겨주고 나중에 바꿔줌
                String dayNumber = "";
                String dayKor = "";
                for (int i = 0; i <= dayotweek.size(); i++) {
                    if (checkedItems.get(i)) {
                        dayNumber += (i+1);
                        dayKor += dayotweek.get(i) + ", ";
                    }
                    Log.d("asdf", "onClick: " + checkedItems.get(i));
                }
                if(dayKor.length() != 0) {
                    dayKor = dayKor.substring(0, dayKor.length() - 2);

                }
                repeat_listener.loadDay(dayNumber, dayKor, checkedItems);
                dismiss();
            }
        });

        return view;
    }

    public void setRepeat_listener(Repeat_Listener listener){
        this.repeat_listener = listener;
    }
}