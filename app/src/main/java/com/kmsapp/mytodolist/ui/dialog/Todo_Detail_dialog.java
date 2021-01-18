package com.kmsapp.mytodolist.ui.dialog;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.model.Todo;

public class Todo_Detail_dialog extends DialogFragment {
    private static String DETAIL_TODO = "detailTodo";
    private Todo editTodo;

    public Todo_Detail_dialog() {
    }

    public static Todo_Detail_dialog newInstance(Todo todo) {

        Bundle args = new Bundle();

        args.putParcelable(DETAIL_TODO, todo);
        Todo_Detail_dialog fragment = new Todo_Detail_dialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            editTodo = getArguments().getParcelable(DETAIL_TODO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_detail_dialog, container, false);

        TextView contents, date, time;

        contents = view.findViewById(R.id.todo_detail_contents);
        date = view.findViewById(R.id.todo_detail_date);
        time = view.findViewById(R.id.todo_detail_time);

        contents.setText(editTodo.getContents());

        if (editTodo.isRepeat()){
            date.setText(editTodo.getRepeatDayKor().toString() + "반복");
        }else {
            date.setText(editTodo.getDate());
        }

        time.setText(editTodo.getTime());
        return view;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout((int)(size.x * 0.7), (int)(size.y * 0.6));
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }
}
