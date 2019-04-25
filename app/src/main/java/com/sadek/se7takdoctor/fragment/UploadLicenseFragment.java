package com.sadek.se7takdoctor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UploadLicenseFragment extends Fragment {

    Unbinder unbinder;
    public static ImageView uLicenseImg;
    public static Bitmap licenseImage;
    FireAuth auth;
    FireStorage storage;
    FireDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_license, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        uLicenseImg = view.findViewById(R.id.uploadLicenseImg);


        storage = new FireStorage(getContext());
        auth = new FireAuth(getContext());
        database = new FireDatabase(getContext());
    }

    @OnClick(R.id.uploadLicenseImg)
    void uploadLicenseImg(View view) {
        PickImageDialog.build(new PickSetup()).show(getFragmentManager());
    }

    @OnClick(R.id.btnSignup)
    void btnSignup(View view) {
        if (licenseImage == null)
            Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        else
            storage.uploadImage(licenseImage, new FireStorage.urlCallback() {
                @Override
                public void onCallback(String url) {
                    RegisterFragment.doctorModel.setCertificateImage(url);
                    storage.uploadImage(RegisterFragment.profileImage, new FireStorage.urlCallback() {
                        @Override
                        public void onCallback(String url) {
                            RegisterFragment.doctorModel.setProfileImage(url);
                            auth.signUp(RegisterFragment.doctorModel);
                        }
                    });
                }
            });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


}
