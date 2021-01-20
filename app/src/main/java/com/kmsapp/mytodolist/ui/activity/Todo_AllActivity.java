package com.kmsapp.mytodolist.ui.activity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.Interface.Add_todoListener;
import com.kmsapp.mytodolist.Interface.OnItemClickListener;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.ActivityTodoAllBinding;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.ui.Adapter.Todo_All_Adapter;
import com.kmsapp.mytodolist.ui.dialog.Todo_Detail_dialog;
import com.kmsapp.mytodolist.ui.dialog.Todo_add_dialog;
import com.kmsapp.mytodolist.ui.viewModel.Todo_all_ViewModel;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class Todo_AllActivity extends AppCompatActivity implements Add_todoListener, UserViewListener {

    private ActivityTodoAllBinding activityTodoAllBinding;
    private Todo_all_ViewModel todo_all_viewModel;
    private Todo_All_Adapter todo_all_adapter;
    private OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTodoAllBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo_all);

        todo_all_viewModel = new ViewModelProvider(this).get(Todo_all_ViewModel.class);
        todo_all_viewModel.init(this);

        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Todo_Detail_dialog todo_detail_dialog = Todo_Detail_dialog.newInstance(todo);
                FragmentManager fm = getSupportFragmentManager();
                todo_detail_dialog.show(fm, "todo_detail_dialog");
            }

            @Override
            public void checkBoxClick(Todo todo) {
            }
        };

        ItemTouchHelper.SimpleCallback simpleCallback = getSimpleCallback();

        todo_all_adapter = new Todo_All_Adapter();
        todo_all_adapter.setOnItemClickListener(onItemClickListener);
        activityTodoAllBinding.allRecycler.setAdapter(todo_all_adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(activityTodoAllBinding.allRecycler);

        todo_all_viewModel.loadAllTodo().observe(this, todos -> {
            todo_all_adapter.setDatas(todos);
            todo_all_adapter.notifyDataSetChanged();
        });

        activityTodoAllBinding.todoAdd.setOnClickListener(view ->{
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
                            todo_all_viewModel.deleteTodo(todo_all_adapter.getDatas().get(position));
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
        activityTodoAllBinding.todoAllProgressBar.setVisibility(View.VISIBLE);
        activityTodoAllBinding.todoAllProgressBar.setIndeterminate(true);
    }

    @Override
    public void loadingEnd() {
        activityTodoAllBinding.todoAllProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void save(Todo todo) {
        todo_all_viewModel.todoUpload(todo);
    }
}
