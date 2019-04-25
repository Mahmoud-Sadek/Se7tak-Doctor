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
import com.sadek.se7takdoctor.dialog.LanguageDialog;
import com.sadek.se7takdoctor.utils.Common;
import com.sadek.se7takdoctor.utils.LocaleUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.paperdb.Paper;

import static com.sadek.se7takdoctor.BaseActivity.restartApp;

public class EditAccountFragment extends Fragment {

    Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);


    }

    @OnClick(R.id.editEmailBtn)
    void editEmailBtn(View view) {
        ((MainActivity) getContext()).switchToPage(10, null, R.string.about);
    }

    @OnClick(R.id.editPhoneBtn)
    void editPhoneBtn(View view) {

    }

    @OnClick(R.id.editPasswordBtn)
    void editPasswordBtn(View view) {

    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
