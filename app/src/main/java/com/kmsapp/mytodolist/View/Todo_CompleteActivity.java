package com.kmsapp.mytodolist.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.kmsapp.mytodolist.Adapter.Todo_Complete_Adapter;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class Todo_CompleteActivity extends AppCompatActivity implements UserView {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private FireBasePresenter fireBasePresenter;
    private RecyclerView recyclerView;

    private List<Todo> datas = new ArrayList<>();
    private Todo_Complete_Adapter todo_complete_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_complete);

        fireBasePresenter = new FireBasePresenter();
        fireBasePresenter.setUserView(this);

        toolbar = findViewById(R.id.m_toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        recyclerView = findViewById(R.id.complete_recycler);

        toolbar_title.setText("완료");

        fireBasePresenter.CompleteTodoLoad().observe(this, todos -> {
            datas = todos;
            todo_complete_adapter = new Todo_Complete_Adapter(todos);
            recyclerView.setAdapter(todo_complete_adapter);
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