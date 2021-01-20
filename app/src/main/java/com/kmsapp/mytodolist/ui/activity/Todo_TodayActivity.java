package com.kmsapp.mytodolist.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.Interface.OnItemClickListener;
import com.kmsapp.mytodolist.databinding.ActivityTodoTodayBinding;
import com.kmsapp.mytodolist.ui.Adapter.Todo_Today_Adapter;
import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.ui.dialog.Todo_Detail_dialog;
import com.kmsapp.mytodolist.ui.dialog.Todo_add_dialog;
import com.kmsapp.mytodolist.ui.viewModel.Todo_Today_ViewModel;
import com.kmsapp.mytodolist.utils.PushAlarmController;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class Todo_TodayActivity extends AppCompatActivity implements Add_todoListener, UserViewListener {

    private Todo_Today_Adapter todo_today_adapter;
    private OnItemClickListener onItemClickListener;
    private Todo_Today_ViewModel todo_today_viewModel;

    private ActivityTodoTodayBinding activityTodoTodayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoTodayBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_today);

        todo_today_viewModel = new ViewModelProvider(this).get(Todo_Today_ViewModel.class);
        todo_today_viewModel.init(this);

        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Todo_Detail_dialog todo_detail_dialog = Todo_Detail_dialog.newInstance(todo);
                FragmentManager fm = getSupportFragmentManager();
                todo_detail_dialog.show(fm, "todo_detail_dialog");
            }

            @Override
            public void checkBoxClick(Todo todo) {
                todo_today_viewModel.todoComplete(todo);
                PushAlarmController pushAlarmController = new PushAlarmController();
                pushAlarmController.cancelAlarm(todo, getApplicationContext());
            }
        };

        ItemTouchHelper.SimpleCallback simpleCallback = getSimpleCallback();


        todo_today_adapter = new Todo_Today_Adapter();
        todo_today_adapter.setOnItemClickListener(onItemClickListener);
        activityTodoTodayBinding.todayRecycler.setAdapter(todo_today_adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(activityTodoTodayBinding.todayRecycler);

        todo_today_viewModel.loadTodayTodo().observe(this, todos -> {
            todo_today_adapter.setDatas(todos);
            todo_today_adapter.notifyDataSetChanged();
        });

        activityTodoTodayBinding.todoAdd.setOnClickListener(view -> {
            Todo_add_dialog todo_add_dialog = Todo_add_dialog.newInstance(this);
            FragmentManager fm = getSupportFragmentManager();
            todo_add_dialog.show(fm, "todo_add_dialog");
        });


    }

    private ItemTouchHelper.SimpleCallback getSimpleCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if(direction == ItemTouchHelper.LEFT){
                    todo_today_viewModel.deleteTodo(todo_today_adapter.getDatas().get(position));
                }
                PushAlarmController pushAlarmController = new PushAlarmController();
                pushAlarmController.cancelAlarm(todo_today_adapter.getDatas().get(position), getApplicationContext());
            }
        };
    }

    @Override
    public void showLoadError(String message) {
        ToastUtil.toastPrint(this, message);
    }

    @Override
    public void showComplete(String message) {
        ToastUtil.toastPrint(this, message);
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

    @Override
    public void save(Todo todo) {
        todo_today_viewModel.todoUpload(todo);
        if(!todo.getTime().equals("알림 없음")){
            PushAlarmController pushAlarmController = new PushAlarmController();
            pushAlarmController.setAlarm(todo, getApplicationContext());
        }
//
    }

}