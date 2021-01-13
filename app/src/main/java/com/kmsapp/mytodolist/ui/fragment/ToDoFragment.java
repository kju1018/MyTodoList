package com.kmsapp.mytodolist.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.TodoFragmentBinding;
import com.kmsapp.mytodolist.ui.activity.Todo_AllActivity;
import com.kmsapp.mytodolist.ui.activity.Todo_CompleteActivity;
import com.kmsapp.mytodolist.ui.activity.Todo_RepeatActivity;
import com.kmsapp.mytodolist.ui.activity.Todo_TodayActivity;


public class ToDoFragment extends Fragment {

    private TodoFragmentBinding todoFragmentBinding;

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
        todoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.todo_fragment, container, false);
        View view = todoFragmentBinding.getRoot();


        todoFragmentBinding.todoToday.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_TodayActivity.class);
            startActivity(intent);
        });
        todoFragmentBinding.todoAll.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_AllActivity.class);
            startActivity(intent);
        });

        todoFragmentBinding.todoRepeat.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_RepeatActivity.class);
            startActivity(intent);
        });

        todoFragmentBinding.todoComplete.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Todo_CompleteActivity.class);
            startActivity(intent);
        });



        return view;
    }
}