package com.kmsapp.mytodolist.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.ui.activity.LoginActivity;
import com.kmsapp.mytodolist.utils.FirebaseAuthUtil;


public class SettingFragment extends Fragment {


    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        TextView setting_id = view.findViewById(R.id.setting_Id);

        TextView logout = view.findViewById(R.id.logout);

        FirebaseUser user = FirebaseAuthUtil.getUser();

        setting_id.setText(user.getEmail());
        logout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return view;
    }
}