package com.kmsapp.mytodolist.ui.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.databinding.TodoViewBinding;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class Todo_Calendar_Adapter extends RecyclerView.Adapter<Todo_Calendar_Adapter.Todo_Calendar_ViewHolder> {

    private List<Todo> datas = new ArrayList<>();

    public Todo_Calendar_Adapter() {
    }

    public void setDatas(List<Todo> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public Todo_Calendar_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterr = LayoutInflater.from(parent.getContext());
        TodoViewBinding todoCompleteViewBinding = TodoViewBinding.inflate(layoutInflaterr, parent, false);
        return new Todo_Calendar_ViewHolder(todoCompleteViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Calendar_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.todoViewBinding.todoContent.setText(data.getContents());
        holder.todoViewBinding.todoDatetime.setText(data.getTime());
        holder.todoViewBinding.todoCheck.setVisibility(View.GONE);

        Log.d("asdf", "onBindViewHolder: dfdf");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Todo_Calendar_ViewHolder extends RecyclerView.ViewHolder{
        TodoViewBinding todoViewBinding;

        public Todo_Calendar_ViewHolder(@NonNull TodoViewBinding todoViewBinding) {
            super(todoViewBinding.getRoot());
            this.todoViewBinding = todoViewBinding;
        }
    }
}
