package com.kmsapp.mytodolist.ui.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.Repeat_Listener;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Todo_add_dialog extends BottomSheetDialogFragment {

    private Add_todoListener add_todoListener;
    private EditText todo_add_content;
    private ImageButton todo_add_save,todo_add_calendar_off,
            todo_add_calendar_on, todo_add_repeat_off, todo_add_repeat_on,
            todo_add_time_on, todo_add_time_off, todo_add_time_reset;
    private TextView todo_add_date, todo_add_time;


    private LocalDate selectDate;
    private LocalTime selectTime;
    private String textViewDate = "", textViewTime = "", textViewRepeatDayK = "", textViewRepeatDayN = "";

    private int y, m, d, hour, min;
    private boolean isRepeat = false;

    private List repeatDayEn = new ArrayList();
    private List repeatDayKor = new ArrayList();

    public static Todo_add_dialog newInstance(Add_todoListener listener) {
        Todo_add_dialog fragment = new Todo_add_dialog();
        fragment.setAdd_todoListener(listener);
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
        todo_add_time = view.findViewById(R.id.todo_add_time);

        todo_add_calendar_off = view.findViewById(R.id.todo_add_calendar_off);
        todo_add_calendar_on = view.findViewById(R.id.todo_add_calendar_on);
        todo_add_repeat_off = view.findViewById(R.id.todo_add_repeat_off);
        todo_add_repeat_on = view.findViewById(R.id.todo_add_repeat_on);
        todo_add_time_off = view.findViewById(R.id.todo_add_time_off);
        todo_add_time_on = view.findViewById(R.id.todo_add_time_on);
        todo_add_time_reset = view.findViewById(R.id.todo_add_time_reset);

        todo_add_save = view.findViewById(R.id.todo_add_save);
        selectDate = LocalDate.now();//오늘날짜
        selectTime = null;

        y = selectDate.getYear();
        m = selectDate.getMonthValue();
        d = selectDate.getDayOfMonth();
        hour = 9;
        min = 0;

        textViewDate =  y + "년 " + m + "월 " + d + "일" ;
        todo_add_date.setText(textViewDate);

        textViewTime = "알림 없음";
        todo_add_time.setText(textViewTime);

        todo_add_calendar_off.setOnClickListener(view14 -> selectDate());

        todo_add_calendar_on.setOnClickListener(view15 -> selectDate());

        todo_add_repeat_off.setOnClickListener(view16 -> selectRepeat());

        todo_add_repeat_on.setOnClickListener(view17 -> selectRepeat());

        todo_add_time_off.setOnClickListener(view18 -> {
            todo_add_time_off.setVisibility(View.GONE);
            todo_add_time_on.setVisibility(View.VISIBLE);
            selectTimeSetting();
        });

        todo_add_time_on.setOnClickListener(view1 -> selectTimeSetting());

        todo_add_time_reset.setOnClickListener(view12 -> {
            hour = 9;
            min = 0;
            textViewTime = "알림없음";
            selectTime = null;
            todo_add_time.setText(textViewTime);
        });


        todo_add_save.setOnClickListener(view13 -> {
            if(isRepeat){
                saveRepeat();
            }else{
                saveSchedule();
            }
        });

        return view;
    }

    private void saveSchedule() {
        Todo todo = new Todo();

        String content = todo_add_content.getText().toString();
        String strSelectDate = LocalDate.of(selectDate.getYear(), selectDate.getMonthValue(), selectDate.getDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        todo.setCompleteId("");
        todo.setContents(content);
        todo.setDate(strSelectDate);
        todo.setRepeat(isRepeat);
        todo.setRepeatComplete("notComplete");
        todo.setRepeatDayKor(new ArrayList());
        todo.setRepeatDayEn(new ArrayList());

        if(selectTime != null){
            String strSelectTime = selectTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            todo.setTime(strSelectTime);
        }else{
            todo.setTime(textViewTime);
        }

        add_todoListener.save(todo);
        dismiss();
    }

    private void saveRepeat() {
        Todo todo = new Todo();
        String content = todo_add_content.getText().toString();

        todo.setCompleteId("");
        todo.setContents(content);
        todo.setDate("");
        todo.setRepeat(isRepeat);
        todo.setRepeatComplete("notComplete");
        todo.setRepeatDayEn(repeatDayEn);
        todo.setRepeatDayKor(repeatDayKor);

        if(selectTime != null){
            String strsSelectTime = selectTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            todo.setTime(strsSelectTime);
        }else{
            todo.setTime(textViewTime);
        }

        add_todoListener.save(todo);
        dismiss();
    }

    private void selectTimeSetting() {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(),R.style.DialogTheme, (timePicker, i, i1) -> {
            hour = i;
            min = i1;
            textViewTime = i+"시 "+ i1 + "분";
            selectTime = LocalTime.of(i, i1);

            todo_add_time.setText(textViewTime);
        }, hour, min, false);
        dialog.setTitle("알림 시간");
        dialog.show();
    }

    private void selectRepeat() {
        Repeat_Dialog repeat_dialog;
        Repeat_Listener repeat_listener = (strDayNumber, strDayKor, dayEn, dayKor, checkedItems) -> {
            isRepeat = true;
            todo_add_repeat_off.setVisibility(View.GONE);
            todo_add_repeat_on.setVisibility(View.VISIBLE);
            todo_add_calendar_off.setVisibility(View.VISIBLE);
            todo_add_calendar_on.setVisibility(View.GONE);

            textViewRepeatDayN = strDayNumber;//123
            textViewRepeatDayK = strDayKor;//월화수
            repeatDayEn = dayEn;
            repeatDayKor = dayKor;

            if(textViewRepeatDayK.length() == 0) {
                isRepeat = false;
                todo_add_date.setText(textViewDate);
                todo_add_repeat_off.setVisibility(View.VISIBLE);
                todo_add_repeat_on.setVisibility(View.GONE);
                repeatDayEn.clear();
                repeatDayKor.clear();
            }
            else
                todo_add_date.setText(textViewRepeatDayK);
        };

        if(textViewRepeatDayN.length() == 0) {
            repeat_dialog = Repeat_Dialog.newInstance(repeat_listener);
        }else {
            repeat_dialog = Repeat_Dialog.newInstance(repeat_listener, textViewRepeatDayN);
        }
        repeat_dialog.show(getFragmentManager(),"repeat_dialog");
    }

    private void selectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme, (datePicker, i, i1, i2) -> {
            isRepeat = false;
            todo_add_calendar_off.setVisibility(View.GONE);
            todo_add_calendar_on.setVisibility(View.VISIBLE);
            todo_add_repeat_on.setVisibility(View.GONE);
            todo_add_repeat_off.setVisibility(View.VISIBLE);
            y = i;
            m = i1 + 1;
            d = i2;
            selectDate = LocalDate.of(y, m, d);
            textViewDate = y + "년 " + m + "월 " + d + "일";
            todo_add_date.setText(textViewDate);

        }, y, m - 1, d);
        datePickerDialog.show();
    }


    public void setAdd_todoListener(Add_todoListener listener){
        this.add_todoListener = listener;
    }

}

