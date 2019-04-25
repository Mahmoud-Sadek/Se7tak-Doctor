package com.sadek.se7takdoctor.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileClinicDetails extends Fragment {

    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinick_detail, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.clinic_name_layout)
    void clinic_name_layout(View view) {
        ((MainActivity) getContext()).switchToPage(6, null, R.string.about);
    }

    @OnClick(R.id.clinic_photo_layout)
    void clinic_photo_layout(View view) {
        ((MainActivity) getContext()).switchToPage(9, null, R.string.about);
    }
    @OnClick(R.id.clinic_address_layout)
    void clinic_address_layout(View view) {
        ((MainActivity) getContext()).startActivity(new Intent(getContext(), MapsActivity.class));
    }
    @OnClick(R.id.clinic_examination_layout)
    void clinic_examination_layout(View view) {
        ((MainActivity) getContext()).switchToPage(7, null, R.string.about);
    }
    @OnClick(R.id.clinic_assistant_layout)
    void clinic_assistant_layout(View view) {
        ((MainActivity) getContext()).switchToPage(8, null, R.string.about);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (MapsActivity.mPosition!=null)
//            Toast.makeText(getContext(), ""+MapsActivity.mPosition.latitude, Toast.LENGTH_SHORT).show();
    }
}
