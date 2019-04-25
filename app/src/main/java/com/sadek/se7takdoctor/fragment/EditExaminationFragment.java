package com.sadek.se7takdoctor.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.ClinicDoctor;
import com.sadek.se7takdoctor.model.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditExaminationFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.clinic_examination_en_input)
    TextView clinic_examination_en_input;
    @BindView(R.id.tabTxt)
    TextView tabTxt;


    FireAuth auth;
    FireDatabase database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_examination, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());
        database.getDoctor(new FireDatabase.DoctorCallback() {
            @Override
            public void onCallback(Doctor model) {
                if (model.getClinicDoctor() != null && clinic_examination_en_input != null) {
                    clinic_examination_en_input.setText(model.getClinicDoctor().getExaminationFees() != null ? model.getClinicDoctor().getExaminationFees() : "");
                }
            }
        });
    }

    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {
        if (!valid())
            Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        else
        database.getDoctor(new FireDatabase.DoctorCallback() {
            @Override
            public void onCallback(Doctor model) {
                ClinicDoctor clinicDoctor = new ClinicDoctor();
                if (model.getClinicDoctor() != null)
                    clinicDoctor = model.getClinicDoctor();
                clinicDoctor.setExaminationFees(clinic_examination_en_input.getText().toString());
                database.editClinicDoctor(clinicDoctor);
            }
        });

    }

    private boolean valid() {
        clinic_examination_en_input.setError(null);

        if (clinic_examination_en_input.getText().toString().isEmpty()) {
            clinic_examination_en_input.setError(getString(R.string.complete_date));
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
