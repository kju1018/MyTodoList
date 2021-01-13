package com.kmsapp.mytodolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.databinding.TodoViewBinding;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class Todo_All_Adapter extends RecyclerView.Adapter<Todo_All_Adapter.Todo_All_ViewHolder>{

    private List<Todo> datas = new ArrayList<>();

    public Todo_All_Adapter() {
    }

    public void setDatas(List<Todo> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public Todo_All_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TodoViewBinding todoViewBinding = TodoViewBinding.inflate(layoutInflater, parent, false);
        return new Todo_All_ViewHolder(todoViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_All_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.todoViewBinding.todoContent.setText(data.getContents());
        String dateTime;

        if(data.isRepeat()) {
            holder.todoViewBinding.repeatImage.setVisibility(View.VISIBLE);
            dateTime = data.getRepeatDayKor().toString() + " " + data.getTime();
        }else {
            holder.todoViewBinding.repeatImage.setVisibility(View.GONE);
            dateTime = data.getDate() + " " + data.getTime();
        }

        holder.todoViewBinding.todoDatetime.setText(dateTime);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Todo_All_ViewHolder extends RecyclerView.ViewHolder {

        private TodoViewBinding todoViewBinding;

        public Todo_All_ViewHolder(@NonNull TodoViewBinding todoViewBinding) {
            super(todoViewBinding.getRoot());
            this.todoViewBinding = todoViewBinding;
        }
    }
}
