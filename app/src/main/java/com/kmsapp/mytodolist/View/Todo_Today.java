package com.kmsapp.mytodolist.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kmsapp.mytodolist.R;

public class Todo_Today extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_title;
    private FloatingActionButton todo_add;
    private Todo_add_dialog todo_add_dialog;
    private ImageView arrow_dropdown, arrow_dropup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_today);

        toolbar = findViewById(R.id.m_toolbar);
        toolbar_title = findViewById(R.id.todo_priority);
        todo_add = findViewById(R.id.todo_add);
        arrow_dropdown = findViewById(R.id.arrow_dropdown);

        toolbar_title.setText("오늘 할 일");
        arrow_dropdown.setVisibility(View.VISIBLE);

        todo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_add_dialog = new Todo_add_dialog();
                FragmentManager fm = getSupportFragmentManager();
                todo_add_dialog.show(fm, "todo_add_dialog");
            }
        });
    }

}