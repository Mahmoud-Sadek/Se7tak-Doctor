package com.sadek.se7takdoctor.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Appointment;
import com.sadek.se7takdoctor.model.Order;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.utils.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddApointmentFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.patient_name_input)
    EditText patient_name_input;
    @BindView(R.id.date_input)
    EditText date_input;
    @BindView(R.id.notes_input)
    EditText notes_input;
    @BindView(R.id.examination_check)
    RadioButton examination_check;
    @BindView(R.id.consultation_check)
    RadioButton consultation_check;


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
        View view = inflater.inflate(R.layout.fragment_add_apointment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());

        model = (Patient) getArguments().getSerializable(Common.FIREBASE_PATIENTS);
        patient_name_input.setText(model.getName());



    }


    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {
        Order appointment = new Order();
        appointment.setUserId(model.getPatientID());
        appointment.setDoctorId(auth.mAuth.getUid());
        appointment.setOrderDate(date_input.getText().toString());
        appointment.setOrderTime("");
        appointment.setDoctorId(FirebaseAuth.getInstance().getUid());
        appointment.setUserName(model.getName());
        appointment.setPhoneNumber(model.getPhone());
        appointment.setStuatus(Common.ORDER_STATUS_ACCEPTED);
        if (examination_check.isChecked())
            appointment.setExamination(getString(R.string.examination));
        else appointment.setExamination(getString(R.string.consultation));
        database.addAppointment(appointment);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
