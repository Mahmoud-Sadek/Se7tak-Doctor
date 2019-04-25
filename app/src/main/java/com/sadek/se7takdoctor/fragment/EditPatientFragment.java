package com.sadek.se7takdoctor.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.utils.Common;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditPatientFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.patient_name_input)
    EditText patient_name_input;
    @BindView(R.id.patient_phone_input)
    EditText patient_phone_input;
    @BindView(R.id.patient_email_input)
    EditText patient_email_input;
    @BindView(R.id.patient_birthday_input)
    Button patient_birthday_input;
    @BindView(R.id.patient_id_input)
    EditText patient_id_input;
    @BindView(R.id.notes_input)
    EditText notes_input;
    @BindView(R.id.male_check)
    RadioButton male_check;
    @BindView(R.id.female_check)
    RadioButton female_check;


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
        View view = inflater.inflate(R.layout.fragment_edit_patient, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());
        try {
            model = (Patient) getArguments().getSerializable(Common.FIREBASE_PATIENTS);
            if (model != null) {
                patient_name_input.setText(model.getName());
                patient_email_input.setText(model.getEmail());
                patient_phone_input.setText(model.getPhone());
                patient_id_input.setText(model.getPatientID());
                patient_birthday_input.setText(model.getBirthday());
                notes_input.setText(model.getNotes());
                if (model.getGender().equals(getString(R.string.male)))
                    male_check.setChecked(true);
                else female_check.setChecked(true);
            }
        }catch(Exception e){

        }

    }


    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {

        if (!valid())
            Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        else {
            Patient patient = new Patient();
            if (model != null)
                patient = model;
            patient.setName(patient_name_input.getText().toString());
            patient.setEmail(patient_email_input.getText().toString());
            patient.setPhone(patient_phone_input.getText().toString());
            patient.setPatientID(patient_id_input.getText().toString());
            patient.setBirthday(patient_birthday_input.getText().toString());
            patient.setNotes(notes_input.getText().toString());
            if (male_check.isChecked())
                patient.setGender(getString(R.string.male));
            else patient.setGender(getString(R.string.femal));
            database.addPatient(patient);

        }
    }

    @OnClick(R.id.patient_birthday_input)
    void patient_birthday_input(View view) {

        createDialogWithoutDateField().show();

    }


    private DatePickerDialog createDialogWithoutDateField() {

        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                patient_birthday_input.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    private boolean valid() {
        patient_name_input.setError(null);
        patient_email_input.setError(null);
        patient_phone_input.setError(null);
        patient_id_input.setError(null);
        patient_birthday_input.setError(null);
        notes_input.setError(null);

        if (patient_name_input.getText().toString().isEmpty()) {
            patient_name_input.setError(getString(R.string.complete_date));
            return false;
        } else if (patient_email_input.getText().toString().isEmpty()) {
            patient_email_input.setError(getString(R.string.complete_date));
            return false;
        }else if (patient_phone_input.getText().toString().isEmpty()) {
            patient_phone_input.setError(getString(R.string.complete_date));
            return false;
        }else if (patient_id_input.getText().toString().isEmpty()) {
            patient_id_input.setError(getString(R.string.complete_date));
            return false;
        }else if (patient_birthday_input.getText().toString().isEmpty()) {
            patient_birthday_input.setError(getString(R.string.complete_date));
            return false;
        }else if (notes_input.getText().toString().isEmpty()) {
            notes_input.setError(getString(R.string.complete_date));
            return false;
        }else if (!male_check.isChecked()&&!female_check.isChecked()) {
            return false;
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
