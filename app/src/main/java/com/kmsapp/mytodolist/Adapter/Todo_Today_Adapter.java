package com.kmsapp.mytodolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmsapp.mytodolist.Model.Todo;
import com.kmsapp.mytodolist.R;

import java.util.List;

public class Todo_Today_Adapter extends RecyclerView.Adapter<Todo_Today_Adapter.Todo_Today_ViewHolder> {

    private List<Todo> datas;

    public Todo_Today_Adapter(List<Todo> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public Todo_Today_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Todo_Today_ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_today_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Today_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.contents.setText(data.getContents());
        holder.date.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class Todo_Today_ViewHolder extends RecyclerView.ViewHolder {

        private TextView contents;
        private TextView date;

        public Todo_Today_ViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.todo_content);
            date = itemView.findViewById(R.id.todo_date);

        }
    }
}
