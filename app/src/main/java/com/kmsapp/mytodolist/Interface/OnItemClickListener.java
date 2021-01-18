package com.kmsapp.mytodolist.Interface;

import com.kmsapp.mytodolist.model.Todo;

public interface OnItemClickListener {
        void onItemClick(Todo todo);
        void checkBoxClick(Todo todo);
}
