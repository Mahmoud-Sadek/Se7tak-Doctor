package com.sadek.se7takdoctor.fragment;


import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.DegreeDoctor;
import com.sadek.se7takdoctor.utils.Common;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditEducationFragment extends Fragment {

    public static Bitmap degreeImage;
    Unbinder unbinder;
    @BindView(R.id.degree_doctor_ar_input)
    EditText degree_doctor_ar_input;
    @BindView(R.id.degree_doctor_en_input)
    EditText degree_doctor_en_input;
    @BindView(R.id.college_doctor_ar_input)
    EditText college_doctor_ar_input;
    @BindView(R.id.college_doctor_en_input)
    EditText college_doctor_en_input;
    @BindView(R.id.year_doctor_en_input)
    Button year_doctor_en_input;

    public static ImageView degree_doctor_img;
    @BindView(R.id.tabTxt)
    TextView tabTxt;


    FireAuth auth;
    FireStorage storage;
    FireDatabase database;
    private DegreeDoctor model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_education, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        degree_doctor_img = view.findViewById(R.id.degree_doctor_img);
        degree_doctor_img.setImageBitmap(null);
        degree_doctor_img.setImageResource(R.drawable.camera_icon);
        auth = new FireAuth(getContext());
        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        model = new DegreeDoctor();

        try {
            model = (DegreeDoctor) getArguments().getSerializable(Common.FIREBASE_ABOUT_DOCTOR);
            if (model != null && degree_doctor_ar_input != null) {
                degree_doctor_ar_input.setText(model.getDegreeAR());
                degree_doctor_en_input.setText(model.getDegreeEN());
                college_doctor_ar_input.setText(model.getCollegeAR());
                college_doctor_en_input.setText(model.getCollegeEN());
                year_doctor_en_input.setText(model.getYear());
                Picasso.with(getContext()).load(model.getImage()).into(degree_doctor_img);

            }
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.saveBtn)
    void saveBtn(View view) {
        if (!valid())
            Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        else {
            final DegreeDoctor degreeDoctor = new DegreeDoctor();
            degreeDoctor.setDegreeAR(degree_doctor_ar_input.getText().toString());
            degreeDoctor.setDegreeEN(degree_doctor_en_input.getText().toString());
            degreeDoctor.setCollegeAR(college_doctor_ar_input.getText().toString());
            degreeDoctor.setCollegeEN(college_doctor_en_input.getText().toString());
            degreeDoctor.setYear(year_doctor_en_input.getText().toString());
            if (degreeDoctor.getImage() == null)
                if (degreeImage != null)
                    storage.uploadImage(degreeImage, new FireStorage.urlCallback() {
                        @Override
                        public void onCallback(String url) {
                            degreeDoctor.setImage(url);
                            database.editDegreeDoctor(degreeDoctor);
                        }
                    });
                else
                    Toast.makeText(getContext(), R.string.complete_date, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.degree_doctor_img)
    void reg_profile_imag(View view) {
        PickImageDialog.build(new PickSetup()).show(getFragmentManager());
    }

    @OnClick(R.id.year_doctor_en_input)
    void year_doctor_en_input(View view) {
        createDialogWithoutDateField().show();
    }


    private DatePickerDialog createDialogWithoutDateField() {

        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_doctor_en_input.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }


    private boolean valid() {
        degree_doctor_ar_input.setError(null);
        degree_doctor_en_input.setError(null);
        college_doctor_ar_input.setError(null);
        college_doctor_en_input.setError(null);

        if (degree_doctor_ar_input.getText().toString().isEmpty()) {
            degree_doctor_ar_input.setError(getString(R.string.complete_date));
            return false;
        } else if (degree_doctor_en_input.getText().toString().isEmpty()) {
            degree_doctor_en_input.setError(getString(R.string.complete_date));
            return false;
        } else if (college_doctor_ar_input.getText().toString().isEmpty()) {
            college_doctor_ar_input.setError(getString(R.string.complete_date));
            return false;
        } else if (college_doctor_en_input.getText().toString().isEmpty()) {
            college_doctor_en_input.setError(getString(R.string.complete_date));
            return false;
        } else if (year_doctor_en_input.getText().toString().isEmpty()) {
            year_doctor_en_input.setError(getString(R.string.complete_date));
            return false;
        } else if (degreeImage == null && model.getImage() == null) {

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
