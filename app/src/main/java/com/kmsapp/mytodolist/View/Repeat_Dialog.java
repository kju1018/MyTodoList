package com.kmsapp.mytodolist.View;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmsapp.mytodolist.R;


public class Repeat_Dialog extends DialogFragment {



    public Repeat_Dialog() {

    }

    public static Repeat_Dialog newInstance(String param1, String param2) {
        Repeat_Dialog fragment = new Repeat_Dialog();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repeat_dialog, container, false);



        return view;
    }
}