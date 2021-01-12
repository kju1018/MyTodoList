package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.kmsapp.mytodolist.Adapter.Todo_Complete_Adapter;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.databinding.ActivityTodoCompleteBinding;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.ViewModel.Todo_Complete_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Todo_CompleteActivity extends AppCompatActivity {


    private List<Todo> datas = new ArrayList<>();
    private Todo_Complete_Adapter todo_complete_adapter;

    private Todo_Complete_ViewModel todo_complete_viewModel;
    private ActivityTodoCompleteBinding activityTodoCompleteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoCompleteBinding = DataBindingUtil.setContentView(this,R.layout.activity_todo_complete);

        todo_complete_viewModel = new ViewModelProvider(this).get(Todo_Complete_ViewModel.class);
        todo_complete_viewModel.init();

        todo_complete_adapter = new Todo_Complete_Adapter();
        activityTodoCompleteBinding.completeRecycler.setAdapter(todo_complete_adapter);

        todo_complete_viewModel.loadComplete().observe(this, todos -> {
            todo_complete_adapter.setDatas(todos);
            todo_complete_adapter.notifyDataSetChanged();
        });

    }

}