package com.kmsapp.mytodolist.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.kmsapp.mytodolist.ui.Adapter.Todo_Today_Adapter;
import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.ui.Todo_add_dialog;
import com.kmsapp.mytodolist.ui.viewModel.Todo_Today_ViewModel;
import com.kmsapp.mytodolist.databinding.ActivityTodoTodayBinding;

public class Todo_TodayActivity extends AppCompatActivity implements Add_todoListener, UserView {

    private Todo_add_dialog todo_add_dialog;
    private Todo_Today_Adapter todo_today_adapter;
    private Todo_Today_Adapter.OnItemClickListener onItemClickListener;
    private Todo_Today_ViewModel todo_today_viewModel;

    private ActivityTodoTodayBinding activityTodoTodayBinding;

    @Override
    public void save(Todo todo) {
        todo_today_viewModel.todoUpload(todo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoTodayBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_today);

        todo_today_viewModel = new ViewModelProvider(this).get(Todo_Today_ViewModel.class);
        todo_today_viewModel.init(this);

        onItemClickListener = new Todo_Today_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //TODO 상세정보
            }

            @Override
            public void checkBoxClick(Todo todo) {
                todo_today_viewModel.todoComplete(todo);
            }
        };

        todo_today_adapter = new Todo_Today_Adapter();
        todo_today_adapter.setOnItemClickListener(onItemClickListener);
        activityTodoTodayBinding.todayRecycler.setAdapter(todo_today_adapter);

        todo_today_viewModel.loadTodayTodo().observe(this, todos -> {
            todo_today_adapter.setDatas(todos);
            todo_today_adapter.notifyDataSetChanged();
        });

        activityTodoTodayBinding.todoAdd.setOnClickListener(view -> {
            todo_add_dialog = Todo_add_dialog.newInstance(this);
            FragmentManager fm = getSupportFragmentManager();
            todo_add_dialog.show(fm, "todo_add_dialog");
        });

    }

    @Override
    public void showLoadError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingStart() {
        activityTodoTodayBinding.todoTodayProgressBar.setVisibility(View.VISIBLE);
        activityTodoTodayBinding.todoTodayProgressBar.setIndeterminate(true);

    }

    @Override
    public void loadingEnd() {
        activityTodoTodayBinding.todoTodayProgressBar.setVisibility(View.GONE);
    }

}