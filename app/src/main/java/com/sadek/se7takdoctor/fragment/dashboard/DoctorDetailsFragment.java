package com.sadek.se7takdoctor.fragment.dashboard;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.adapter.PhotosAdapter;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Doctor;
import com.sadek.se7takdoctor.utils.Common;
import com.sadek.se7takdoctor.utils.LocaleUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.paperdb.Paper;


public class DoctorDetailsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.docotr_profile_img)
    ImageView docotr_profile_img;
    @BindView(R.id.doctor_name_TV)
    TextView doctor_name_TV;
    @BindView(R.id.doctor_about_TV)
    TextView doctor_about_TV;
    @BindView(R.id.doctor_rating_bar)
    RatingBar doctor_rating_bar;
    @BindView(R.id.doctor_rating_TV)
    TextView doctor_rating_TV;
    @BindView(R.id.doctor_specialty_TV)
    TextView doctor_specialty_TV;
    @BindView(R.id.doctor_location_TV)
    TextView doctor_location_TV;
    @BindView(R.id.docotr_examinations_TV)
    TextView docotr_examinations_TV;
    @BindView(R.id.doctor_about2_TV)
    TextView doctor_about2_TV;
    @BindView(R.id.clinic_name_TV)
    TextView clinic_name_TV;
    @BindView(R.id.assistant_name_TV)
    TextView assistant_name_TV;
    @BindView(R.id.clinic_photos_recycler)
    RecyclerView clinic_photos_recycler;

    @BindView(R.id.doctor_publish)
    Button doctor_publish;


    @BindView(R.id.call_clinic_img)
    ImageView call_clinic_img;
    @BindView(R.id.call_assistant_img)
    ImageView call_assistant_img;


    Doctor model;
    FireDatabase fireDatabase;


    PhotosAdapter photosAdaptet;
    List<String> photosList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_detail, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        Paper.init(getContext());

        Bundle bundle = this.getArguments();
        fireDatabase = new FireDatabase(getContext());


        String doctorId = getArguments().getString(Common.FIREBASE_DOCTORS);
        if (doctorId != null) {
            fireDatabase.getDoctor(doctorId, new FireDatabase.DoctorCallback() {
                @Override
                public void onCallback(Doctor modelDoctor) {
                    model = modelDoctor;
                    initData();
                }
            });
        } else {
            model = (Doctor) getArguments().getSerializable(Common.FIREBASE_DOCTORS);
            initData();
        }
    }

    private void initData() {
        try {
            if (Paper.book().read(Common.language).equals(LocaleUtils.ARABIC)) {
                doctor_name_TV.setText(model.getLastName());
                doctor_specialty_TV.setText(model.getSpecialty().getTitleAr() + "");
                doctor_about_TV.setText(model.getClinicDoctor().getNameAR() + "");
                doctor_about2_TV.setText(model.getAboutDoctor().getAboutAR() + "");
                doctor_location_TV.setText(model.getClinicDoctor().getLocationAR() + "");
                clinic_name_TV.setText(model.getClinicDoctor().getNameAR() + "");
                assistant_name_TV.setText(model.getClinicDoctor().getClinicAssistantDoctor().getNameAR() + "");
            } else {
                doctor_name_TV.setText(model.getFirstName());
                doctor_specialty_TV.setText(model.getSpecialty().getTitleEn() + "");
                doctor_about_TV.setText(model.getClinicDoctor().getNameEN() + "");
                doctor_about2_TV.setText(model.getAboutDoctor().getAboutEN() + "");
                doctor_location_TV.setText(model.getClinicDoctor().getLocationEN() + "");
                clinic_name_TV.setText(model.getClinicDoctor().getNameEN() + "");
                assistant_name_TV.setText(model.getClinicDoctor().getClinicAssistantDoctor().getNameEN() + "");
            }

            if (model.isPublished())
                doctor_publish.setText(R.string.unpublish_doctor);
            else
                doctor_publish.setText(R.string.publish_doctor);
            Picasso.with(getContext()).load(model.getProfileImage()).into(docotr_profile_img);

            doctor_rating_TV.setText(getString(R.string.over_rate) + " " +
                    (model.getRateCount() == null ? "0" : model.getRateCount()) +
                    " " + getString(R.string.vistors));


            docotr_examinations_TV.setText(model.getClinicDoctor().getExaminationFees() + getString(R.string.egp) + "");

            doctor_rating_bar.setRating(Float.parseFloat(Double.parseDouble(model.getRateTotal()) / Double.parseDouble(model.getRateCount()) + ""));


            //home_new_offers_recycler
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            clinic_photos_recycler.setLayoutManager(mLayoutManager);
            photosList = new ArrayList<>();
            photosList = model.getClinicDoctor().getImges();
            photosAdaptet = new PhotosAdapter(photosList, getContext());
            clinic_photos_recycler.setAdapter(photosAdaptet);
        } catch (Exception e) {

        }


    }


    @OnClick(R.id.call_clinic_img)
    void call_clinic_img(View view) {
        dialContactPhone(model.getClinicDoctor().getPhone());
    }

    @OnClick(R.id.call_assistant_img)
    void call_assistant_img(View view) {
        dialContactPhone(model.getClinicDoctor().getClinicAssistantDoctor().getPhone());
    }

    @OnClick(R.id.doctor_publish)
    void doctor_publish(View view) {
        fireDatabase.publishOrUnPublishDoctor(!model.isPublished(), model);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }


    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
