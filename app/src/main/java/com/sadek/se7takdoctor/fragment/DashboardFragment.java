package com.sadek.se7takdoctor.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.activity.SplashActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardFragment extends Fragment {

    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.specialty_btn)
    void specialty_btn(View view) {
        ((MainActivity) getContext()).switchToPage(14, null, R.string.publish_doctor);
    }

    @OnClick(R.id.publish_doctor_layout)
    void publish_doctor_layout(View view) {

        Bundle bundle = new Bundle();
        bundle.putBoolean("publish", false);
        ((MainActivity) getContext()).switchToPage(17, bundle, R.string.publish_doctor);
    }
    @OnClick(R.id.unpublish_doctor_layout)
    void unpublish_doctor_layout(View view) {

        Bundle bundle = new Bundle();
        bundle.putBoolean("publish", true);
        ((MainActivity) getContext()).switchToPage(17, bundle, R.string.publish_doctor);
    }

    @OnClick(R.id.logout_layout)
    void logout_layout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
