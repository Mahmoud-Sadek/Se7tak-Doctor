package com.sadek.se7takdoctor.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.sadek.se7takdoctor.BaseActivity;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.fragment.AddApointmentFragment;
import com.sadek.se7takdoctor.fragment.AddWorkdaysFragment;
import com.sadek.se7takdoctor.fragment.ClinicPhotoListFragment;
import com.sadek.se7takdoctor.fragment.EditAboutFragment;
import com.sadek.se7takdoctor.fragment.EditAccountFragment;
import com.sadek.se7takdoctor.fragment.EditClinicAssistantFragment;
import com.sadek.se7takdoctor.fragment.EditClinicNameFragment;
import com.sadek.se7takdoctor.fragment.EditEducationFragment;
import com.sadek.se7takdoctor.fragment.EditExaminationFragment;
import com.sadek.se7takdoctor.fragment.EditPatientFragment;
import com.sadek.se7takdoctor.fragment.EducationListFragment;
import com.sadek.se7takdoctor.fragment.MainFragment;
import com.sadek.se7takdoctor.fragment.PatientInfoFragment;
import com.sadek.se7takdoctor.fragment.ProfileClinicDetails;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

public class MainActivity extends BaseActivity implements IPickResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchToPage(1, null, R.string.profile);


    }

    public void switchToPage(int page, Bundle bundle, int nameId) {
        FragmentTransaction transaction = getTransaction();
        String name = getResources().getString(nameId);
        Fragment nextFragment;
        switch (page) {

            case 1:
                nextFragment = new MainFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.commit();
                break;

            case 2:
                nextFragment = new EditAboutFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 3:
                nextFragment = new AddWorkdaysFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 4:
                nextFragment = new ProfileClinicDetails();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 5:
                nextFragment = new EditEducationFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;

            case 6:
                nextFragment = new EditClinicNameFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 7:
                nextFragment = new EditExaminationFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 8:
                nextFragment = new EditClinicAssistantFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 9:
                nextFragment = new ClinicPhotoListFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 10:
                nextFragment = new EditAccountFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 11:
                nextFragment = new PatientInfoFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 12:
                nextFragment = new EditPatientFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
            case 13:
                nextFragment = new AddApointmentFragment();
                nextFragment.setArguments(bundle);
                transaction.replace(R.id.head_container, nextFragment, name);
                transaction.addToBackStack(name);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
//        try {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.head_container);
        if (f instanceof ClinicPhotoListFragment) {
            ClinicPhotoListFragment.uploadClinicImage(pickResult.getBitmap());

        } else if (pickResult.getError() == null) {
            EditEducationFragment.degree_doctor_img.setImageBitmap(pickResult.getBitmap());
            EditEducationFragment.degreeImage = pickResult.getBitmap();
        }
//        }catch (Exception e){
//            try {
//                UploadLicenseFragment.uLicenseImg.setImageBitmap(pickResult.getBitmap());
//                UploadLicenseFragment.licenseImage = pickResult.getBitmap();
//            }
//            catch (Exception ex){
//                RegisterFragment.reg_profile_imag.setImageBitmap(pickResult.getBitmap());
//                RegisterFragment.profileImage = pickResult.getBitmap();
//            }
//        }

    }
}
