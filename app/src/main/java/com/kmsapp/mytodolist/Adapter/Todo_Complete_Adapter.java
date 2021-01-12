package com.kmsapp.mytodolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.databinding.TodoCompleteViewBinding;
import com.kmsapp.mytodolist.databinding.TodoTodayViewBinding;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
        TodoCompleteViewBinding todoCompleteViewBinding = TodoCompleteViewBinding.inflate(layoutInflaterr, parent, false);
        return new Todo_Complete_ViewHolder(todoCompleteViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Complete_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.todoCompleteViewBinding.setTodo(data);

        if(data.isRepeat())
            holder.todoCompleteViewBinding.repeatImage.setVisibility(View.VISIBLE);
        else
            holder.todoCompleteViewBinding.repeatImage.setVisibility(View.GONE);

        holder.todoCompleteViewBinding.todoCompleteCheck.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Todo_Complete_ViewHolder extends RecyclerView.ViewHolder {

        TodoCompleteViewBinding todoCompleteViewBinding;

        public Todo_Complete_ViewHolder(@NonNull TodoCompleteViewBinding todoCompleteViewBinding) {
            super(todoCompleteViewBinding.getRoot());
            this.todoCompleteViewBinding = todoCompleteViewBinding;

        }
    }
}
