package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.kmsapp.mytodolist.ui.Adapter.Todo_All_Adapter;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.ActivityTodoAllBinding;
import com.kmsapp.mytodolist.ui.viewModel.Todo_all_ViewModel;

public class Todo_AllActivity extends AppCompatActivity implements UserView {

    private ActivityTodoAllBinding activityTodoAllBinding;
    private Todo_all_ViewModel todo_all_viewModel;
    private Todo_All_Adapter todo_all_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoAllBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_all);

        todo_all_viewModel = new ViewModelProvider(this).get(Todo_all_ViewModel.class);
        todo_all_viewModel.init(this);

        todo_all_adapter = new Todo_All_Adapter();
        activityTodoAllBinding.allRecycler.setAdapter(todo_all_adapter);

        todo_all_viewModel.loadAllTodo().observe(this, todos -> {
            todo_all_adapter.setDatas(todos);
            todo_all_adapter.notifyDataSetChanged();
        });


    }

    @Override
    public void showLoadError(String message) {

    }

    @Override
    public void showComplete(String message) {

    }

    @Override
    public void loadingStart() {

    }

    @Override
    public void loadingEnd() {

    }
}