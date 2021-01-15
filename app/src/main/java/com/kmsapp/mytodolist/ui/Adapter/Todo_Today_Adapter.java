package com.kmsapp.mytodolist.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.databinding.TodoViewBinding;
import com.kmsapp.mytodolist.model.Todo;

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
        TodoViewBinding todoViewBinding = TodoViewBinding.inflate(layoutInflater, parent, false);
        return new Todo_Today_ViewHolder(todoViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Today_ViewHolder holder, int position) {
        Todo data = datas.get(position);


        if(data.isRepeat()) {
            holder.todoViewBinding.repeatImage.setVisibility(View.VISIBLE);
        }else {
            holder.todoViewBinding.repeatImage.setVisibility(View.GONE);
        }

        holder.todoViewBinding.todoContent.setText(data.getContents());
        holder.todoViewBinding.todoDatetime.setText(data.getTime());

        holder.todoViewBinding.todoCheck.setOnClickListener(view ->{
            holder.todoViewBinding.todoCheck.setChecked(false);
            listener.checkBoxClick(data);
        });
        holder.todoViewBinding.todoLinear.setOnClickListener(view -> {
            holder.todoViewBinding.repeatImage.setVisibility(View.GONE);
            listener.onItemClick(data);
        });

    }

    @Override
    public int getItemCount() {
            return datas.size();
    }


    public class Todo_Today_ViewHolder extends RecyclerView.ViewHolder {

        private TodoViewBinding todoViewBinding;

        public Todo_Today_ViewHolder(@NonNull TodoViewBinding todoViewBinding) {
            super(todoViewBinding.getRoot());
            this.todoViewBinding = todoViewBinding;
        }
    }
}
