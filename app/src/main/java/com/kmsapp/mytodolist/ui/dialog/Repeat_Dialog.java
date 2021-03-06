package com.kmsapp.mytodolist.ui.dialog;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.kmsapp.mytodolist.Interface.Repeat_Listener;
import com.kmsapp.mytodolist.R;

import java.util.ArrayList;
import java.util.List;


public class Repeat_Dialog extends DialogFragment {

    private static final String CHECKED = "checked";

    private ListView listView;
    private Button cancle, confirm;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList dayotweek = new ArrayList();
    private ArrayList dayotweekEn = new ArrayList();

    private Repeat_Listener repeat_listener;
    private String checked = "";


    public Repeat_Dialog() {

    }

    public static Repeat_Dialog newInstance(Repeat_Listener repeat_listener) {
        Repeat_Dialog fragment = new Repeat_Dialog();
        fragment.setRepeat_listener(repeat_listener);
        return fragment;
    }

    public static Repeat_Dialog newInstance(Repeat_Listener repeat_listener, String checkN) {
        Repeat_Dialog fragment = new Repeat_Dialog();
        fragment.setRepeat_listener(repeat_listener);
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

        dayotweek.add("월");
        dayotweek.add("화");
        dayotweek.add("수");
        dayotweek.add("목");
        dayotweek.add("금");
        dayotweek.add("토");
        dayotweek.add("일");

        dayotweekEn.add("MONDAY");
        dayotweekEn.add("TUESDAY");
        dayotweekEn.add("WEDNESDAY");
        dayotweekEn.add("THURSDAY");
        dayotweekEn.add("FRIDAY");
        dayotweekEn.add("SATURDAY");
        dayotweekEn.add("SUNDAY");

        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,dayotweek);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);
        if(checked.length() != 0){
            int position;
            for (int i = 0; i < checked.length(); i++) {
                position = Integer.parseInt(String.valueOf(checked.charAt(i)));
                listView.setItemChecked(position,true);
            }
        }
//        listView.setItemChecked();
//checkedItems.size()은 true의 개수
        confirm.setOnClickListener(view12 -> {
            SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

            String strDayNumber = "";
            String strDayKor = "";
            List dayEn = new ArrayList();
            List dayKor = new ArrayList();
            for (int i = 0; i <= dayotweek.size(); i++) {
                if (checkedItems.get(i)) {
                    strDayNumber += i;
                    strDayKor += dayotweek.get(i) + ", ";
                    dayEn.add(dayotweekEn.get(i));
                    dayKor.add(dayotweek.get(i));
                }
            }
            if(strDayKor.length() != 0) {
                strDayKor = strDayKor.substring(0, strDayKor.length() - 2);

            }
            repeat_listener.loadDay(strDayNumber, strDayKor, dayEn, dayKor, checkedItems);
            dismiss();
        });

        cancle.setOnClickListener(view1 -> dismiss());

        return view;
    }

    public void setRepeat_listener(Repeat_Listener listener){
        this.repeat_listener = listener;
    }
}