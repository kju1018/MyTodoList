package com.kmsapp.mytodolist.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.ui.activity.Todo_CompleteActivity;
import com.kmsapp.mytodolist.ui.activity.Todo_TodayActivity;


public class ToDoFragment extends Fragment {

    private LinearLayout todo_today, todo_complete;

    public static ToDoFragment newInstance() {
        ToDoFragment fragment = new ToDoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);

        todo_today = view.findViewById(R.id.todo_today);
        todo_complete = view.findViewById(R.id.todo_complete);

        todo_today.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_TodayActivity.class);
            startActivity(intent);
        });

        todo_complete.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_CompleteActivity.class);
            startActivity(intent);
        });

        return view;
    }
}