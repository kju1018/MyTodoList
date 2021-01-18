package com.kmsapp.mytodolist.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.kmsapp.mytodolist.Interface.FirebaseListener;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.databinding.CalendarFragmentBinding;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.ui.Adapter.Todo_Calendar_Adapter;
import com.kmsapp.mytodolist.ui.viewModel.CalendarFragment_ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment implements UserViewListener, FirebaseListener {

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
        calendarFragment_viewModel.init(this, this);

        todo_calendar_adapter = new Todo_Calendar_Adapter();
        calendarFragmentBinding.calendarRecycler.setAdapter(todo_calendar_adapter);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        calendarFragmentBinding.currentDay.setText(format.format(calendar.getTime()));

        calendarFragment_viewModel.loadEvent();

        calendarFragment_viewModel.todosLive.observe(getViewLifecycleOwner(), new Observer<ArrayList<Todo>>() {
            @Override
            public void onChanged(ArrayList<Todo> todos) {
                todo_calendar_adapter.setDatas(todos);
                todo_calendar_adapter.notifyDataSetChanged();
            }
        });

        calendarFragmentBinding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                calendarFragment_viewModel.selectDayload(eventDay.getCalendar());
                Calendar selectCalendar = eventDay.getCalendar();

                String selectDay = format.format(selectCalendar.getTime());
                calendarFragmentBinding.currentDay.setText(selectDay);
            }
        });
        //리스너로 바꿔주기
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


    @Override
    public void onSuccess(List<EventDay> eventDays) {
        calendarFragmentBinding.calendarView.setEvents(eventDays);
    }
}
