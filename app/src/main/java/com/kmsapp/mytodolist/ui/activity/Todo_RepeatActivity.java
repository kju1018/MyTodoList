package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.kmsapp.mytodolist.Adapter.Todo_Repeat_Adapter;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.ActivityTodoRepeatBinding;
import com.kmsapp.mytodolist.ui.viewModel.Todo_Repeat_ViewModel;

public class Todo_RepeatActivity extends AppCompatActivity implements UserView {

    private ActivityTodoRepeatBinding activityTodoRepeatBinding;
    private Todo_Repeat_ViewModel todo_repeat_viewModel;
    private Todo_Repeat_Adapter todo_repeat_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoRepeatBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_repeat);

        todo_repeat_viewModel = new ViewModelProvider(this).get(Todo_Repeat_ViewModel.class);
        todo_repeat_viewModel.init(this);

        todo_repeat_adapter = new Todo_Repeat_Adapter();

        activityTodoRepeatBinding.repeatRecycler.setAdapter(todo_repeat_adapter);

        todo_repeat_viewModel.loadRepeatTodo().observe(this, todos -> {
            todo_repeat_adapter.setDatas(todos);
            todo_repeat_adapter.notifyDataSetChanged();
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
        activityTodoRepeatBinding.todoRepeatProgressBar.setVisibility(View.VISIBLE);
        activityTodoRepeatBinding.todoRepeatProgressBar.setIndeterminate(true);
    }

    @Override
    public void loadingEnd() {
        activityTodoRepeatBinding.todoRepeatProgressBar.setVisibility(View.GONE);
    }
}