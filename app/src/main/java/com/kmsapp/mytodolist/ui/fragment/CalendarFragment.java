package com.kmsapp.mytodolist.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.CalendarFragmentBinding;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.ui.Adapter.Todo_Calendar_Adapter;
import com.kmsapp.mytodolist.ui.viewModel.CalendarFragment_ViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment implements UserView {

    private CalendarFragmentBinding calendarFragmentBinding;
    private CalendarFragment_ViewModel calendarFragment_viewModel;

    private Todo_Calendar_Adapter todo_calendar_adapter;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        calendarFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.calendar_fragment, container, false);
        View view = calendarFragmentBinding.getRoot();

        calendarFragment_viewModel = new ViewModelProvider(this).get(CalendarFragment_ViewModel.class);
        calendarFragment_viewModel.init(this);

        todo_calendar_adapter = new Todo_Calendar_Adapter();
        calendarFragmentBinding.calendarRecycler.setAdapter(todo_calendar_adapter);

        calendarFragment_viewModel.loadEvent().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventDay>>() {
            @Override
            public void onChanged(ArrayList<EventDay> eventDays) {
                calendarFragmentBinding.calendarView.setEvents(eventDays);
            }
        });
        calendarFragment_viewModel.selectTodo().observe(getViewLifecycleOwner(), new Observer<ArrayList<Todo>>() {
            @Override
            public void onChanged(ArrayList<Todo> todos) {
                Log.d("asdf", "onChanged: " + "작동");
                todo_calendar_adapter.setDatas(todos);
                todo_calendar_adapter.notifyDataSetChanged();
            }
        });

        calendarFragmentBinding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                calendarFragment_viewModel.selectDayload(eventDay.getCalendar());
            }
        });

        return view;
    }


    @Override
    public void showLoadError(String message) {

    }

    @Override
    public void showComplete(String message) {

    }

    @Override
    public void loadingStart() {
        calendarFragmentBinding.calendarLinear.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingEnd() {


        calendarFragmentBinding.calendarLinear.setVisibility(View.GONE);
    }
}