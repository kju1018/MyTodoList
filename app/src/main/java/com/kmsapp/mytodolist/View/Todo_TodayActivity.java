package com.kmsapp.mytodolist.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kmsapp.mytodolist.Adapter.Todo_Today_Adapter;
import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class Todo_TodayActivity extends AppCompatActivity implements Add_todoListener, UserView {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private FloatingActionButton todo_add;
    private Todo_add_dialog todo_add_dialog;
    private FireBasePresenter fireBasePresenter;
    private RecyclerView recyclerView;
    private Todo_Today_Adapter todo_today_adapter;
    private Todo_Today_Adapter.OnItemClickListener onItemClickListener;
    private ProgressBar progressBar;


    private List<Todo> datas = new ArrayList<>();

    @Override
    public void save(Todo todo) {
        fireBasePresenter.TodoUpload(todo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_today);
        fireBasePresenter = new FireBasePresenter(this);

        toolbar = findViewById(R.id.m_toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        recyclerView = findViewById(R.id.today_recycler);
        todo_add = findViewById(R.id.todo_add);

        progressBar = findViewById(R.id.todo_today_progressBar);

        toolbar_title.setText("오늘 할 일");

        onItemClickListener = new Todo_Today_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //TODO 상세정보
            }

            @Override
            public void checkBoxClick(Todo todo) {
                fireBasePresenter.TodoComplete(todo);
            }
        };


        fireBasePresenter.TodayTodoLoad().observe(this, todos -> {
            datas = todos;
            todo_today_adapter = new Todo_Today_Adapter(todos);
            todo_today_adapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(todo_today_adapter);
        });

        todo_add.setOnClickListener(view -> {
            todo_add_dialog = Todo_add_dialog.newInstance();
            todo_add_dialog.setAdd_todoListener(Todo_TodayActivity.this);
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
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

    }

    @Override
    public void loadingEnd() {
        progressBar.setVisibility(View.GONE);
    }
}