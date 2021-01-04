package com.kmsapp.mytodolist.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;

public class Todo_TodayActivity extends AppCompatActivity implements Add_todoListener {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private FloatingActionButton todo_add;
    private Todo_add_dialog todo_add_dialog;
    private ImageView arrow_dropdown, arrow_dropup;
    private FireBaseRepository fireBaseRepository;

    private TextView textView;
    //예시

    @Override
    public void save(Todo todo) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.TodoUpload(todo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_today);

        toolbar = findViewById(R.id.m_toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        todo_add = findViewById(R.id.todo_add);
//        arrow_dropdown = findViewById(R.id.arrow_dropdown);

        toolbar_title.setText("오늘 할 일");
//        arrow_dropdown.setVisibility(View.VISIBLE);
        textView = findViewById(R.id.example_text);
        //예시

        todo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_add_dialog = Todo_add_dialog.newInstance();
                todo_add_dialog.setAdd_todoListener(Todo_TodayActivity.this);
                FragmentManager fm = getSupportFragmentManager();
                todo_add_dialog.show(fm, "todo_add_dialog");
            }
        });

    }

}