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

public class Todo_Complete_Adapter extends RecyclerView.Adapter<Todo_Complete_Adapter.Todo_Complete_ViewHolder> {

    private List<Todo> datas = new ArrayList<>();

    public Todo_Complete_Adapter(){
    }

    public void setDatas(List<Todo> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public Todo_Complete_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterr = LayoutInflater.from(parent.getContext());
        TodoViewBinding todoCompleteViewBinding = TodoViewBinding.inflate(layoutInflaterr, parent, false);
        return new Todo_Complete_ViewHolder(todoCompleteViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Complete_ViewHolder holder, int position) {
        Todo data = datas.get(position);
        String dateTime = data.getDate() + " " + data.getTime();

        if(data.isRepeat())
            holder.todoViewBinding.repeatImage.setVisibility(View.VISIBLE);
        else
            holder.todoViewBinding.repeatImage.setVisibility(View.GONE);

        holder.todoViewBinding.todoContent.setText(data.getContents());
        holder.todoViewBinding.todoDatetime.setText(dateTime);

        holder.todoViewBinding.todoCheck.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Todo_Complete_ViewHolder extends RecyclerView.ViewHolder {

        TodoViewBinding todoViewBinding;

        public Todo_Complete_ViewHolder(@NonNull TodoViewBinding todoViewBinding) {
            super(todoViewBinding.getRoot());
            this.todoViewBinding = todoViewBinding;

        }
    }
}
