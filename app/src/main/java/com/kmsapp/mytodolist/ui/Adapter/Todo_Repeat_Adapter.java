package com.kmsapp.mytodolist.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.Interface.OnItemClickListener;
import com.kmsapp.mytodolist.databinding.TodoViewBinding;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class Todo_Repeat_Adapter extends RecyclerView.Adapter<Todo_Repeat_Adapter.Todo_Repeat_ViewHolder>{

    private List<Todo> datas = new ArrayList<>();
    private OnItemClickListener listener;

    public Todo_Repeat_Adapter(){

    }
    public void setDatas(List<Todo> datas) {
        this.datas = datas;
    }

    public List<Todo> getDatas() {
        return datas;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Todo_Repeat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TodoViewBinding todoViewBinding = TodoViewBinding.inflate(layoutInflater, parent, false);
        return new Todo_Repeat_ViewHolder(todoViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Repeat_ViewHolder holder, int position) {
        Todo data = datas.get(position);
        holder.todoViewBinding.repeatImage.setVisibility(View.VISIBLE);
        holder.todoViewBinding.todoContent.setText(data.getContents());
        holder.todoViewBinding.todoDatetime.setText(data.getRepeatDayKor().toString()+ " " + data.getTime());

        holder.todoViewBinding.todoCheck.setOnClickListener(view ->{
            holder.todoViewBinding.todoCheck.setChecked(false);
            listener.checkBoxClick(data);
        });
        holder.todoViewBinding.todoLinear.setOnClickListener(view -> {
            listener.onItemClick(data);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Todo_Repeat_ViewHolder extends RecyclerView.ViewHolder{

        private TodoViewBinding todoViewBinding;

        public Todo_Repeat_ViewHolder(@NonNull TodoViewBinding todoViewBinding) {
            super(todoViewBinding.getRoot());
            this.todoViewBinding = todoViewBinding;
        }
    }
}
