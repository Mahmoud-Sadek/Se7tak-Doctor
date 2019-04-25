package com.sadek.se7takdoctor.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.model.WorkDaysDoctor;
import com.sadek.se7takdoctor.utils.Common;
import com.sadek.se7takdoctor.utils.TimePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddWorkdaysFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.saturday_radioGroup)
    RadioGroup saturday_radioGroup;
    @BindView(R.id.saturday_holiday_check)
    RadioButton saturday_holiday_check;
    @BindView(R.id.saturday_work_check)
    RadioButton saturday_work_check;
    @BindView(R.id.saturday_buttons)
    View saturday_buttons;
    @BindView(R.id.saturday_start_time_btn)
    Button saturday_start_time_btn;
    @BindView(R.id.saturday_end_time_btn)
    Button saturday_end_time_btn;

    @BindView(R.id.sunday_radioGroup)
    RadioGroup sunday_radioGroup;
    @BindView(R.id.sunday_holiday_check)
    RadioButton sunday_holiday_check;
    @BindView(R.id.sunday_work_check)
    RadioButton sunday_work_check;
    @BindView(R.id.sunday_buttons)
    View sunday_buttons;
    @BindView(R.id.sunday_start_time_btn)
    Button sunday_start_time_btn;
    @BindView(R.id.sunday_end_time_btn)
    Button sunday_end_time_btn;

    @BindView(R.id.monday_radioGroup)
    RadioGroup monday_radioGroup;
    @BindView(R.id.monday_holiday_check)
    RadioButton monday_holiday_check;
    @BindView(R.id.monday_work_check)
    RadioButton monday_work_check;
    @BindView(R.id.monday_buttons)
    View monday_buttons;
    @BindView(R.id.monday_start_time_btn)
    Button monday_start_time_btn;
    @BindView(R.id.monday_end_time_btn)
    Button monday_end_time_btn;


    @BindView(R.id.tuesday_radioGroup)
    RadioGroup tuesday_radioGroup;
    @BindView(R.id.tuesday_holiday_check)
    RadioButton tuesday_holiday_check;
    @BindView(R.id.tuesday_work_check)
    RadioButton tuesday_work_check;
    @BindView(R.id.tuesday_buttons)
    View tuesday_buttons;
    @BindView(R.id.tuesday_start_time_btn)
    Button tuesday_start_time_btn;
    @BindView(R.id.tuesday_end_time_btn)
    Button tuesday_end_time_btn;

    @BindView(R.id.wednesday_radioGroup)
    RadioGroup wednesday_radioGroup;
    @BindView(R.id.wednesday_holiday_check)
    RadioButton wednesday_holiday_check;
    @BindView(R.id.wednesday_work_check)
    RadioButton wednesday_work_check;
    @BindView(R.id.wednesday_buttons)
    View wednesday_buttons;
    @BindView(R.id.wednesday_start_time_btn)
    Button wednesday_start_time_btn;
    @BindView(R.id.wednesday_end_time_btn)
    Button wednesday_end_time_btn;

    @BindView(R.id.thursday_radioGroup)
    RadioGroup thursday_radioGroup;
    @BindView(R.id.thursday_holiday_check)
    RadioButton thursday_holiday_check;
    @BindView(R.id.thursday_work_check)
    RadioButton thursday_work_check;
    @BindView(R.id.thursday_buttons)
    View thursday_buttons;
    @BindView(R.id.thursday_start_time_btn)
    Button thursday_start_time_btn;
    @BindView(R.id.thursday_end_time_btn)
    Button thursday_end_time_btn;

    @BindView(R.id.friday_radioGroup)
    RadioGroup friday_radioGroup;
    @BindView(R.id.friday_holiday_check)
    RadioButton friday_holiday_check;
    @BindView(R.id.friday_work_check)
    RadioButton friday_work_check;
    @BindView(R.id.friday_buttons)
    View friday_buttons;
    @BindView(R.id.friday_start_time_btn)
    Button friday_start_time_btn;
    @BindView(R.id.friday_end_time_btn)
    Button friday_end_time_btn;


    FireAuth auth;
    FireDatabase database;

    Patient model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_work_day, container, false);


        return view;
    }

    private void initRadioButton() {
        saturday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.saturday_holiday_check) {
                    saturday_buttons.setVisibility(View.GONE);
                } else
                    saturday_buttons.setVisibility(View.VISIBLE);
            }
        });
        sunday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sunday_holiday_check) {
                    sunday_buttons.setVisibility(View.GONE);
                } else
                    sunday_buttons.setVisibility(View.VISIBLE);
            }
        });
        monday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.monday_holiday_check) {
                    monday_buttons.setVisibility(View.GONE);
                } else
                    monday_buttons.setVisibility(View.VISIBLE);
            }
        });
        friday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.friday_holiday_check) {
                    friday_buttons.setVisibility(View.GONE);
                } else
                    friday_buttons.setVisibility(View.VISIBLE);
            }
        });
        tuesday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tuesday_holiday_check) {
                    tuesday_buttons.setVisibility(View.GONE);
                } else
                    tuesday_buttons.setVisibility(View.VISIBLE);
            }
        });
        thursday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.thursday_holiday_check) {
                    thursday_buttons.setVisibility(View.GONE);
                } else
                    thursday_buttons.setVisibility(View.VISIBLE);
            }
        });
        wednesday_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.wednesday_holiday_check) {
                    wednesday_buttons.setVisibility(View.GONE);
                } else
                    wednesday_buttons.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());

        initRadioButton();
    }

/*
    @OnCheckedChanged(R.id.saturday_radioGroup)
    void saturday_radioGroup(CompoundButton group, int checkedId) {

    }*/

    @OnClick(R.id.saturday_start_time_btn)
    void saturday_start_time_btn(View view) {
        showTimeDialog(saturday_start_time_btn);
    }

    @OnClick(R.id.saturday_end_time_btn)
    void saturday_end_time_btn(View view) {
        showTimeDialog(saturday_end_time_btn);
    }

  /*  @OnCheckedChanged(R.id.sunday_radioGroup)
    void sunday_radioGroup(RadioGroup group, int checkedId) {
        if (checkedId == R.id.sunday_holiday_check) {
            sunday_buttons.setVisibility(View.GONE);
        } else
            sunday_buttons.setVisibility(View.VISIBLE);
    }*/

    @OnClick(R.id.sunday_start_time_btn)
    void sunday_start_time_btn(View view) {
        showTimeDialog(sunday_start_time_btn);
    }

    @OnClick(R.id.sunday_end_time_btn)
    void sunday_end_time_btn(View view) {
        showTimeDialog(sunday_end_time_btn);
    }



    @OnClick(R.id.monday_start_time_btn)
    void monday_start_time_btn(View view) {
        showTimeDialog(monday_start_time_btn);
    }

    @OnClick(R.id.monday_end_time_btn)
    void monday_end_time_btn(View view) {
        showTimeDialog(monday_end_time_btn);
    }


    @OnClick(R.id.tuesday_start_time_btn)
    void tuesday_start_time_btn(View view) {
        showTimeDialog(tuesday_start_time_btn);
    }

    @OnClick(R.id.tuesday_end_time_btn)
    void tuesday_end_time_btn(View view) {
        showTimeDialog(tuesday_end_time_btn);
    }


    @OnClick(R.id.wednesday_start_time_btn)
    void wednesday_start_time_btn(View view) {
        showTimeDialog(wednesday_start_time_btn);
    }

    @OnClick(R.id.wednesday_end_time_btn)
    void wednesday_end_time_btn(View view) {
        showTimeDialog(wednesday_end_time_btn);
    }


    @OnClick(R.id.thursday_start_time_btn)
    void thursday_start_time_btn(View view) {
        showTimeDialog(thursday_start_time_btn);
    }

    @OnClick(R.id.thursday_end_time_btn)
    void thursday_end_time_btn(View view) {
        showTimeDialog(thursday_end_time_btn);
    }


    @OnClick(R.id.friday_start_time_btn)
    void friday_start_time_btn(View view) {
        showTimeDialog(friday_start_time_btn);
    }

    @OnClick(R.id.friday_end_time_btn)
    void friday_end_time_btn(View view) {
        showTimeDialog(friday_end_time_btn);
    }

    private void showTimeDialog(final Button view) {
        TimePickerDialog.fire(getContext(), new TimePickerDialog.TimeSelectionView() {
            @Override
            public void onTimeSelected(String time, Date dateTime) {
                view.setText(time);

                Log.d("date", "onTimeSelected: " + time);
            }
        });
    }


    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {
        if (!valid())
            return;
        WorkDaysDoctor workDaysDoctor[] = new WorkDaysDoctor[7];

        workDaysDoctor[Common.SATURDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.SATURDAY-1].setDay(Common.SATURDAY);
        workDaysDoctor[Common.SATURDAY-1].setHoliday(saturday_holiday_check.isChecked());
        workDaysDoctor[Common.SATURDAY-1].setHourFrom(saturday_start_time_btn.getText().toString());
        workDaysDoctor[Common.SATURDAY-1].setHourTo(saturday_end_time_btn.getText().toString());

        workDaysDoctor[Common.SUNDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.SUNDAY-1].setDay(Common.SUNDAY);
        workDaysDoctor[Common.SUNDAY-1].setHoliday(sunday_holiday_check.isChecked());
        workDaysDoctor[Common.SUNDAY-1].setHourFrom(sunday_start_time_btn.getText().toString());
        workDaysDoctor[Common.SUNDAY-1].setHourTo(sunday_end_time_btn.getText().toString());

        workDaysDoctor[Common.MONDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.MONDAY-1].setDay(Common.MONDAY);
        workDaysDoctor[Common.MONDAY-1].setHoliday(monday_holiday_check.isChecked());
        workDaysDoctor[Common.MONDAY-1].setHourFrom(monday_start_time_btn.getText().toString());
        workDaysDoctor[Common.MONDAY-1].setHourTo(monday_end_time_btn.getText().toString());

        workDaysDoctor[Common.TUESDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.TUESDAY-1].setDay(Common.TUESDAY);
        workDaysDoctor[Common.TUESDAY-1].setHoliday(tuesday_holiday_check.isChecked());
        workDaysDoctor[Common.TUESDAY-1].setHourFrom(tuesday_start_time_btn.getText().toString());
        workDaysDoctor[Common.TUESDAY-1].setHourTo(tuesday_end_time_btn.getText().toString());

        workDaysDoctor[Common.WEDNESDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.WEDNESDAY-1].setDay(Common.WEDNESDAY);
        workDaysDoctor[Common.WEDNESDAY-1].setHoliday(wednesday_holiday_check.isChecked());
        workDaysDoctor[Common.WEDNESDAY-1].setHourFrom(wednesday_start_time_btn.getText().toString());
        workDaysDoctor[Common.WEDNESDAY-1].setHourTo(wednesday_end_time_btn.getText().toString());

        workDaysDoctor[Common.THURSDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.THURSDAY-1].setDay(Common.THURSDAY);
        workDaysDoctor[Common.THURSDAY-1].setHoliday(thursday_holiday_check.isChecked());
        workDaysDoctor[Common.THURSDAY-1].setHourFrom(thursday_start_time_btn.getText().toString());
        workDaysDoctor[Common.THURSDAY-1].setHourTo(thursday_end_time_btn.getText().toString());

        workDaysDoctor[Common.FRIDAY-1] = new WorkDaysDoctor();
        workDaysDoctor[Common.FRIDAY-1].setDay(Common.FRIDAY);
        workDaysDoctor[Common.FRIDAY-1].setHoliday(friday_holiday_check.isChecked());
        workDaysDoctor[Common.FRIDAY-1].setHourFrom(friday_start_time_btn.getText().toString());
        workDaysDoctor[Common.FRIDAY-1].setHourTo(friday_end_time_btn.getText().toString());


        database.editWorkDaysDoctor(new ArrayList<WorkDaysDoctor>(Arrays.asList(workDaysDoctor)));




    }

    private boolean valid() {
        if (!saturday_holiday_check.isChecked()) {
            if (!saturday_work_check.isChecked())
                return false;
            if (saturday_work_check.isChecked()) {
                if (saturday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (saturday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!sunday_holiday_check.isChecked()) {
            if (!sunday_work_check.isChecked())
                return false;
            if (sunday_work_check.isChecked()) {
                if (sunday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (sunday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!monday_holiday_check.isChecked()) {
            if (!monday_work_check.isChecked())
                return false;
            if (monday_work_check.isChecked()) {
                if (monday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (monday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!tuesday_holiday_check.isChecked()) {
            if (!tuesday_work_check.isChecked())
                return false;
            if (tuesday_work_check.isChecked()) {
                if (tuesday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (tuesday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!wednesday_holiday_check.isChecked()) {
            if (!wednesday_work_check.isChecked())
                return false;
            if (wednesday_work_check.isChecked()) {
                if (wednesday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (wednesday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!thursday_holiday_check.isChecked()) {
            if (!thursday_work_check.isChecked())
                return false;
            if (thursday_work_check.isChecked()) {
                if (thursday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (thursday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        if (!friday_holiday_check.isChecked()) {
            if (!friday_work_check.isChecked())
                return false;
            if (friday_work_check.isChecked()) {
                if (friday_start_time_btn.getText().equals(getString(R.string.start)))
                    return true;
                if (friday_end_time_btn.getText().equals(getString(R.string.end)))
                    return true;
            }
        }
        return true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
