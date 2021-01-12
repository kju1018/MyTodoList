package com.kmsapp.mytodolist.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.databinding.TodoTodayViewBinding;

import java.util.ArrayList;
import java.util.List;

public class Todo_Today_Adapter extends RecyclerView.Adapter<Todo_Today_Adapter.Todo_Today_ViewHolder> {

    private List<Todo> datas = new ArrayList<>();
    private OnItemClickListener listener;


    public Todo_Today_Adapter() {

    }

    public void setDatas(List<Todo> datas) {
        this.datas = datas;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Todo todo);
        void checkBoxClick(Todo todo);
    }
    @NonNull
    @Override
    public Todo_Today_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TodoTodayViewBinding todoTodayViewBinding = TodoTodayViewBinding.inflate(layoutInflater, parent, false);
        return new Todo_Today_ViewHolder(todoTodayViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Today_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.todoTodayViewBinding.setTodo(data);

        if(data.isRepeat()) {
            holder.todoTodayViewBinding.repeatImage.setVisibility(View.VISIBLE);
        }else {
            holder.todoTodayViewBinding.repeatImage.setVisibility(View.GONE);
        }

        holder.todoTodayViewBinding.todoTodayCheck.setOnClickListener(view ->{
            holder.todoTodayViewBinding.todoTodayCheck.setChecked(false);
            listener.checkBoxClick(data);
        });
        holder.todoTodayViewBinding.todoTodayLinear.setOnClickListener(view -> {
            holder.todoTodayViewBinding.repeatImage.setVisibility(View.GONE);
            listener.onItemClick(data);
        });

    }

    @Override
    public int getItemCount() {
            return datas.size();
    }


    public class Todo_Today_ViewHolder extends RecyclerView.ViewHolder {

        private TodoTodayViewBinding todoTodayViewBinding;

        public Todo_Today_ViewHolder(@NonNull TodoTodayViewBinding todoTodayViewBinding) {
            super(todoTodayViewBinding.getRoot());
            this.todoTodayViewBinding = todoTodayViewBinding;
        }
    }
}
