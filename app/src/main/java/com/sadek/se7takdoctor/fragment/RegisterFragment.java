package com.sadek.se7takdoctor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.RegisterActivity;
import com.sadek.se7takdoctor.model.Doctor;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends Fragment {

    public static Doctor doctorModel;
    Unbinder unbinder;
    public static ImageView reg_profile_imag;
    @BindView(R.id.firstname_input)
    EditText firstname_input;
    @BindView(R.id.lastname_input)
    EditText lastname_input;
    @BindView(R.id.phone_input)
    EditText phone_input;
    @BindView(R.id.email_input)
    EditText email_input;
    @BindView(R.id.password_input)
    EditText password_input;

    public static Bitmap profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        reg_profile_imag = view.findViewById(R.id.reg_profile_imag);
        if (doctorModel == null)
            doctorModel = new Doctor();
    }

    @OnClick(R.id.specialty_btn)
    void specialty_btn(View view) {
        ((RegisterActivity) getActivity()).switchToPage(2, null, R.string.specialty);
    }

    @OnClick(R.id.reg_profile_imag)
    void reg_profile_imag(View view) {
        PickImageDialog.build(new PickSetup()).show(getFragmentManager(), getResources().getString(R.string.register));
    }

    @OnClick(R.id.btnNextRegister)
    void btnNextRegister(View view) {
        if (!validate()) {
            return;
        }
        doctorModel.setFirstName(firstname_input.getText().toString());
        doctorModel.setLastName(lastname_input.getText().toString());
        doctorModel.setEmail(email_input.getText().toString());
        doctorModel.setPassword(password_input.getText().toString());
        doctorModel.setPhoneNumber(phone_input.getText().toString());
        ((RegisterActivity) getActivity()).switchToPage(3, null, R.string.upload_license_fragment);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (profileImage != null)
            reg_profile_imag.setImageBitmap(profileImage);
    }

    private Boolean validate() {
        email_input.setError(null);
        password_input.setError(null);
        firstname_input.setError(null);
        lastname_input.setError(null);
        phone_input.setError(null);

        String email = email_input.getText().toString();
        String firstName = firstname_input.getText().toString();
        String lastName = lastname_input.getText().toString();
        String phone = phone_input.getText().toString();
        String pass = password_input.getText().toString();
        if (firstName.isEmpty()) {
            firstname_input.setError(getString(R.string.enter_valid_name));
            return false;
        } else if (lastName.isEmpty()) {
            lastname_input.setError(getString(R.string.enter_valid_name));
            return false;
        } else if (phone.isEmpty()) {
            phone_input.setError(getString(R.string.enter_valid_phone));
            return false;
        } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input.setError(getString(R.string.enter_valid_email));
            return false;
        } else if (pass.isEmpty() || pass.length() < 6) {
            password_input.setError(getString(R.string.enter_valid_password));
            return false;
        } else if (doctorModel.getSpecialty() == null) {
            Toast.makeText(getContext(), R.string.enter_valid_specialty, Toast.LENGTH_SHORT).show();
            return false;
        }else if (profileImage == null) {
            Toast.makeText(getContext(), R.string.choose_profile_image, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

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
