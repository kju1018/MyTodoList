package com.kmsapp.mytodolist.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kmsapp.mytodolist.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ToDoFragment toDoFragment = new ToDoFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nov);
        toolbar = findViewById(R.id.m_toolbar);

        toolbar.setTitle("Todo List");


        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, toDoFragment).commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.todofragment_item: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, toDoFragment).commit();
                        break;
                    }
                    case R.id.calendar_item: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, calendarFragment).commit();
                        break;
                    }
                    case R.id.settingfragment_item: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, settingFragment).commit();
                        break;
                    }
                }
                return true;
            }
        });
    }

}