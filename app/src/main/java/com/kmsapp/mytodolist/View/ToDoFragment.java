package com.kmsapp.mytodolist.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kmsapp.mytodolist.R;


public class ToDoFragment extends Fragment {

    private LinearLayout todo_today;

//    public static ToDoFragment newInstance(String param1, String param2) {
//        ToDoFragment fragment = new ToDoFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);

        todo_today = view.findViewById(R.id.todo_today);

        todo_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Todo_Today.class);
                startActivity(intent);
            }
        });

        return view;
    }
}