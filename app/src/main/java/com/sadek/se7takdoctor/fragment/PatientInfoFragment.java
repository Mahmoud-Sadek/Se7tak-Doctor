package com.sadek.se7takdoctor.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.utils.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PatientInfoFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.patientNameTV)
    TextView patientNameTV;
    @BindView(R.id.patientGenderTV)
    TextView patientGenderTV;
    @BindView(R.id.patientIdTV)
    TextView patientIdTV;
    @BindView(R.id.patientNotesTV)
    TextView patientNotesTV;


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
        View view = inflater.inflate(R.layout.fragment_patient_info, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());

        model = (Patient) getArguments().getSerializable("model");
        patientNameTV.setText(model.getName());
        patientGenderTV.setText(model.getGender());
        patientIdTV.setText(model.getPatientID() != null ? "#" + model.getPatientID() : "");
        patientNotesTV.setText(model.getNotes());


    }

    @OnClick(R.id.add_apointment)
    void add_apointment(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Common.FIREBASE_PATIENTS, model);
        ((MainActivity) getContext()).switchToPage(13, bundle, R.string.about);
    }

    @OnClick(R.id.call)
    void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + model.getPhone()));
        startActivity(intent);
    }

    @OnClick(R.id.delete)
    void delete(View view) {
        database.deletePatient(model);
    }

    @OnClick(R.id.edit)
    void edit(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Common.FIREBASE_PATIENTS, model);
        ((MainActivity) getContext()).switchToPage(12, bundle, R.string.about);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
