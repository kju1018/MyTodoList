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

import com.kmsapp.mytodolist.Model.Todo;
import com.kmsapp.mytodolist.R;

import java.util.List;

public class Todo_Today_Adapter extends RecyclerView.Adapter<Todo_Today_Adapter.Todo_Today_ViewHolder> {

    private List<Todo> datas;
    private OnItemClickListener listener;

    public Todo_Today_Adapter(List<Todo> datas) {
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
        return new Todo_Today_ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_today_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Todo_Today_ViewHolder holder, int position) {
        Todo data = datas.get(position);

        holder.contents.setText(data.getContents());
        if (data.getTime() != null){
            holder.time.setText(data.getTime());
        }else {
            holder.time.setText("시간 없음");
        }

        if(data.isRepeat())
            holder.repeateImage.setVisibility(View.VISIBLE);

        holder.checkBox.setOnClickListener(view -> listener.checkBoxClick(data));

        holder.todo_Linear.setOnClickListener(view -> listener.onItemClick(data));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class Todo_Today_ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView contents;
        private TextView time;
        private ImageView repeateImage;
        private LinearLayout todo_Linear;

        public Todo_Today_ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox =itemView.findViewById(R.id.todo_check);
            contents = itemView.findViewById(R.id.todo_content);
            repeateImage = itemView.findViewById(R.id.repeatImage);
            time = itemView.findViewById(R.id.todo_time);
            todo_Linear = itemView.findViewById(R.id.todo_linear);

        }
    }
}
