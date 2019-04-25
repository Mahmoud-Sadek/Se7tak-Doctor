package com.sadek.se7takdoctor.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Doctor;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.doctor_profile_img)
    ImageView doctor_profile_img;
    @BindView(R.id.doctor_profile_name)
    TextView doctor_profile_name;
    @BindView(R.id.tabTxt)
    TextView tabTxt;
    @BindView(R.id.btnPublish)
    Button btnPublish;


    FireAuth auth;
    FireDatabase database;

    Doctor doctor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        tabTxt.setText(R.string.profile);
        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());
        database.getDoctor(new FireDatabase.DoctorCallback() {
            @Override
            public void onCallback(Doctor model) {
                try {
                    doctor = model;
                    Picasso.with(getContext()).load(model.getProfileImage()).into(doctor_profile_img);
                    doctor_profile_name.setText(model.getFirstName() + " " + model.getLastName());
                    if (model.isPublished()) {
                        btnPublish.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }
        });

    }

    @OnClick(R.id.profile_about_btn)
    void profile_about_btn(View view) {
        ((MainActivity) getContext()).switchToPage(2, null, R.string.about);
    }

    @OnClick(R.id.profile_edu_btn)
    void profile_edu_btn(View view) {
        ((MainActivity) getContext()).switchToPage(3, null, R.string.education);
    }

    @OnClick(R.id.profile_clinic_info_btn)
    void profile_clinic_info_btn(View view) {
        ((MainActivity) getContext()).switchToPage(4, null, R.string.work);
    }

    @OnClick(R.id.btnPublish)
    void btnPublish(View view) {
        if (doctor.getAboutDoctor() != null) {
            if (doctor.getAboutDoctor().getAboutAR() != null && doctor.getAboutDoctor().getAboutEN() != null) {
                if (doctor.getClinicDoctor() != null) {
                    if (doctor.getClinicDoctor().getImges() != null && doctor.getClinicDoctor().getClinicAssistantDoctor() != null
                            && doctor.getClinicDoctor().getExaminationFees() != null &&
                            doctor.getClinicDoctor().getLang() != 0 && doctor.getClinicDoctor().getLat() != 0
                            && doctor.getClinicDoctor().getLocationAR() != null && doctor.getClinicDoctor().getLocationEN() != null
                            && doctor.getClinicDoctor().getNameAR() != null && doctor.getClinicDoctor().getNameEN() != null
                            && doctor.getClinicDoctor().getPhone() != null) {
                        if (doctor.getWorkDaysDoctor() != null) {
                            database.publishDoctor(doctor);
                            return;
                        }
                    }
                }
            }
        }
        Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
