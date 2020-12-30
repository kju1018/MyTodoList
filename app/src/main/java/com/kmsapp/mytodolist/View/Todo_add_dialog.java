package com.kmsapp.mytodolist.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kmsapp.mytodolist.R;

import java.util.Calendar;

public class Todo_add_dialog extends BottomSheetDialogFragment {

    private Add_totoListener add_totoListener;
    private EditText todo_add_content;
    private ImageButton todo_add_save,todo_add_calendar_off,
            todo_add_calendar_on, todo_add_repeat_off, todo_add_repeat_on;
    private TextView todo_add_date;

    private Calendar cal;
    private int y, m, d;


    public static Todo_add_dialog newInstance() {
        Todo_add_dialog fragment = new Todo_add_dialog();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_add_dialog, container, false);

        todo_add_content = view.findViewById(R.id.todo_add_content);

        todo_add_date = view.findViewById(R.id.todo_add_date);

        todo_add_calendar_off = view.findViewById(R.id.todo_add_calendar_off);
        todo_add_calendar_on = view.findViewById(R.id.todo_add_calendar_on);
        todo_add_repeat_off = view.findViewById(R.id.todo_add_repeat_off);
        todo_add_repeat_on = view.findViewById(R.id.todo_add_repeat_on);

        todo_add_save = view.findViewById(R.id.todo_add_save);

        cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) + 1;
        d = cal.get(Calendar.DATE);
        String today =  y + "년 " + m + "월 " + d + "일";
        todo_add_date.setText(today);

        todo_add_calendar_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        todo_add_calendar_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        todo_add_repeat_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_add_repeat_off.setVisibility(View.GONE);
                todo_add_repeat_on.setVisibility(View.VISIBLE);
                todo_add_calendar_off.setVisibility(View.VISIBLE);
                todo_add_calendar_on.setVisibility(View.GONE);
            }
        });

        todo_add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_totoListener.save(todo_add_content.getText().toString());
            }
        });

        return view;
    }

    private void selectDate() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                todo_add_calendar_off.setVisibility(View.GONE);
                todo_add_calendar_on.setVisibility(View.VISIBLE);
                todo_add_repeat_on.setVisibility(View.GONE);
                todo_add_repeat_off.setVisibility(View.VISIBLE);
                y = i;
                m = i1 + 1;
                d = i2;
                String date = y + "년 " + m + "월 " + d + "일";
                todo_add_date.setText(date);
            }
        }, y, m - 1, d);

        datePickerDialog.show();
    }


    interface Add_totoListener {
        void save(String example);
    }

    public void setAdd_totoListener(Add_totoListener listener){
        this.add_totoListener = listener;
    }

}

