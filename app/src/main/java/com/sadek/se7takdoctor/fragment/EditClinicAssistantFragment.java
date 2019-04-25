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
import com.sadek.se7takdoctor.model.ClinicAssistantDoctor;
import com.sadek.se7takdoctor.model.ClinicDoctor;
import com.sadek.se7takdoctor.model.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditClinicAssistantFragment extends Fragment {

    Unbinder unbinder;


    @BindView(R.id.assistant_name_ar_input)
    TextView assistant_name_ar_input;
    @BindView(R.id.assistant_name_en_input)
    TextView assistant_name_en_input;
    @BindView(R.id.assistant_phone_en_input)
    TextView assistant_phone_en_input;
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
        View view = inflater.inflate(R.layout.fragment_edit_clinic_assistant, container, false);

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
                if (model.getClinicDoctor() != null && assistant_name_ar_input != null) {
                    if (model.getClinicDoctor().getClinicAssistantDoctor() != null) {
                        assistant_name_ar_input.setText(model.getClinicDoctor().getClinicAssistantDoctor().getNameAR() != null ? model.getClinicDoctor().getClinicAssistantDoctor().getNameAR() : "");
                        assistant_name_en_input.setText(model.getClinicDoctor().getClinicAssistantDoctor().getNameEN() != null ? model.getClinicDoctor().getClinicAssistantDoctor().getNameEN() : "");
                        assistant_phone_en_input.setText(model.getClinicDoctor().getClinicAssistantDoctor().getPhone() != null ? model.getClinicDoctor().getClinicAssistantDoctor().getPhone() : "");
                    }
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
                    ClinicAssistantDoctor clinicAssistantDoctor = new ClinicAssistantDoctor();
                    clinicAssistantDoctor.setNameAR(assistant_name_ar_input.getText().toString());
                    clinicAssistantDoctor.setNameEN(assistant_name_en_input.getText().toString());
                    clinicAssistantDoctor.setPhone(assistant_phone_en_input.getText().toString());
                    clinicDoctor.setClinicAssistantDoctor(clinicAssistantDoctor);
                    database.editClinicDoctor(clinicDoctor);

                }
            });

    }

    private boolean valid() {
        assistant_name_ar_input.setError(null);
        assistant_name_en_input.setError(null);
        assistant_phone_en_input.setError(null);

        if (assistant_name_ar_input.getText().toString().isEmpty()) {
            assistant_name_ar_input.setError(getString(R.string.complete_date));
            return false;
        } else if (assistant_name_en_input.getText().toString().isEmpty()) {
            assistant_name_en_input.setError(getString(R.string.complete_date));
            return false;
        } else if (assistant_phone_en_input.getText().toString().isEmpty()) {
            assistant_phone_en_input.setError(getString(R.string.complete_date));
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
