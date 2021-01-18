package com.kmsapp.mytodolist.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.OnItemClickListener;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.ui.Adapter.Todo_Repeat_Adapter;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.ActivityTodoRepeatBinding;
import com.kmsapp.mytodolist.ui.Adapter.Todo_Today_Adapter;
import com.kmsapp.mytodolist.ui.dialog.Todo_add_dialog;
import com.kmsapp.mytodolist.ui.viewModel.Todo_Repeat_ViewModel;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class Todo_RepeatActivity extends AppCompatActivity implements UserViewListener, Add_todoListener {

    private ActivityTodoRepeatBinding activityTodoRepeatBinding;
    private Todo_Repeat_ViewModel todo_repeat_viewModel;
    private Todo_Repeat_Adapter todo_repeat_adapter;
    private OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoRepeatBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_repeat);

        todo_repeat_viewModel = new ViewModelProvider(this).get(Todo_Repeat_ViewModel.class);
        todo_repeat_viewModel.init(this);


        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {

            }

            @Override
            public void checkBoxClick(Todo todo) {
                todo_repeat_viewModel.todoComplete(todo);
            }
        };

        ItemTouchHelper.SimpleCallback simpleCallback = getSimpleCallback();

        todo_repeat_adapter = new Todo_Repeat_Adapter();
        todo_repeat_adapter.setOnItemClickListener(onItemClickListener);
        activityTodoRepeatBinding.repeatRecycler.setAdapter(todo_repeat_adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(activityTodoRepeatBinding.repeatRecycler);

        todo_repeat_viewModel.loadRepeatTodo().observe(this, todos -> {
            todo_repeat_adapter.setDatas(todos);
            todo_repeat_adapter.notifyDataSetChanged();
        });

        activityTodoRepeatBinding.todoAdd.setOnClickListener(view -> {
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
                    todo_repeat_viewModel.deleteTodo(todo_repeat_adapter.getDatas().get(position));
                }
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
        activityTodoRepeatBinding.todoRepeatProgressBar.setVisibility(View.VISIBLE);
        activityTodoRepeatBinding.todoRepeatProgressBar.setIndeterminate(true);
    }

    @Override
    public void loadingEnd() {
        activityTodoRepeatBinding.todoRepeatProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void save(Todo todo) {
        todo_repeat_viewModel.todoUpload(todo);
    }
}