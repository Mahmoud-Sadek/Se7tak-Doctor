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
import com.sadek.se7takdoctor.model.AboutDoctor;
import com.sadek.se7takdoctor.model.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditAboutFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.about_doctour_ar_input)
    TextView about_doctour_ar_input;
    @BindView(R.id.about_doctour_en_input)
    TextView about_doctour_en_input;
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
        View view = inflater.inflate(R.layout.fragment_edit_about, container, false);

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
                if (model.getAboutDoctor() != null && about_doctour_ar_input != null) {
                    about_doctour_ar_input.setText(model.getAboutDoctor().getAboutAR() != null ? model.getAboutDoctor().getAboutAR() : "");
                    about_doctour_en_input.setText(model.getAboutDoctor().getAboutEN() != null ? model.getAboutDoctor().getAboutEN() : "");
                }
            }
        });

    }

    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {
        if (!valid())
            Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        else {
            AboutDoctor aboutDoctor = new AboutDoctor();
            aboutDoctor.setAboutAR(about_doctour_ar_input.getText().toString());
            aboutDoctor.setAboutEN(about_doctour_en_input.getText().toString());
            database.editAboutDoctor(aboutDoctor);
        }
    }

    private boolean valid() {
        about_doctour_ar_input.setError(null);
        about_doctour_en_input.setError(null);

        if (about_doctour_ar_input.getText().toString().isEmpty()) {
            about_doctour_ar_input.setError(getString(R.string.complete_date));
            return false;
        } else if (about_doctour_en_input.getText().toString().isEmpty()) {
            about_doctour_en_input.setError(getString(R.string.complete_date));
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
