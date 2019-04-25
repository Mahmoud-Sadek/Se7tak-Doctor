package com.sadek.se7takdoctor.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.adapter.ClinicPhotoAdatpter;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.ClinicDoctor;
import com.sadek.se7takdoctor.model.Doctor;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ClinicPhotoListFragment extends Fragment {

    private static FireStorage storage;
    Unbinder unbinder;
    @BindView(R.id.clinic_photo_list)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyTV)
    TextView emptyTV;

    ClinicPhotoAdatpter adatpter;
    List<String> list;
    static FireDatabase database;

    static boolean flagAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinic_photo_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);


        list = new ArrayList<String>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recycler.setLayoutManager(layoutManager);
        adatpter = new ClinicPhotoAdatpter(getContext(), list);
        recycler.setAdapter(adatpter);

        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        loading.setVisibility(View.VISIBLE);
        database.getClinicPhoto(new FireDatabase.ClinicPhotoCallback() {
            @Override
            public void onCallback(ArrayList<String> model) {
                list = model;
                try {

                    if (list.size() == 0) {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.VISIBLE);
                    } else {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.GONE);
                        Collections.reverse(list);
                        adatpter = new ClinicPhotoAdatpter(getContext(), list);
                        recycler.setAdapter(adatpter);
                    }
                } catch (Exception e) {

                }
            }
        });

    }

    @OnClick(R.id.btn_add)
    void btn_add(View view) {
        flagAdd = true;
        PickImageDialog.build(new PickSetup()).show(getChildFragmentManager());

    }

    public static void uploadClinicImage(Bitmap Image) {
        storage.uploadImage(Image, new FireStorage.urlCallback() {
            @Override
            public void onCallback(final String url) {
                if (!flagAdd)
                    return;
                database.getDoctor(new FireDatabase.DoctorCallback() {
                    @Override
                    public void onCallback(Doctor model) {
                        if (!flagAdd)
                            return;
                        flagAdd = false;
                        ClinicDoctor clinicDoctor = new ClinicDoctor();
                        if (model.getClinicDoctor() != null)
                            clinicDoctor = model.getClinicDoctor();
                        if (model.getClinicDoctor().getImges() != null)
                            clinicDoctor.getImges().add(url);
                        else {
                            List<String> imgs = new ArrayList<String>();
                            imgs.add(url);
                            clinicDoctor.setImges(imgs);
                        }
                        database.editClinicDoctor(clinicDoctor);

                    }
                });
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
