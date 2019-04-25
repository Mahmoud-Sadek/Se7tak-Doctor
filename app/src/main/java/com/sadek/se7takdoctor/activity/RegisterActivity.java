package com.sadek.se7takdoctor.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sadek.se7takdoctor.BaseActivity;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.fragment.RegisterFragment;
import com.sadek.se7takdoctor.fragment.SpecialtyFragment;
import com.sadek.se7takdoctor.fragment.UploadLicenseFragment;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IPickResult {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        switchToPage(1, null, R.string.register);
    }

    public void switchToPage(int page, Bundle bundle, int nameId) {
        FragmentTransaction transaction = getTransaction();
        String name = getResources().getString(nameId);
        Fragment nextFragment;
        switch (page) {

            case 1:
                nextFragment = new RegisterFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.commit();
                break;

            case 2:
                nextFragment = new SpecialtyFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 3:
                nextFragment = new UploadLicenseFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;

        }
    }

    @OnClick(R.id.back_btn)
    void back_btn(View view) {
        onBackPressed();
    }
    @Override
    public void onPickResult(PickResult pickResult) {
        try {

            if (pickResult.getError() == null) {
                SpecialtyFragment.reg_specialty_img.setImageBitmap(pickResult.getBitmap());
                SpecialtyFragment.specialtyImage = pickResult.getBitmap();
            }
        }catch (Exception e){
            try {
                UploadLicenseFragment.uLicenseImg.setImageBitmap(pickResult.getBitmap());
                UploadLicenseFragment.licenseImage = pickResult.getBitmap();
            }
            catch (Exception ex){
                RegisterFragment.reg_profile_imag.setImageBitmap(pickResult.getBitmap());
                RegisterFragment.profileImage = pickResult.getBitmap();
            }
        }

    }

}
