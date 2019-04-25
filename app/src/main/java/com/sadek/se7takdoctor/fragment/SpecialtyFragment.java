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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.adapter.SpecialtyAdatpter;
import com.sadek.se7takdoctor.dialog.AddSpecialtyDialog;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.SpecialtyModel;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SpecialtyFragment extends Fragment implements IPickResult {

    Unbinder unbinder;
    @BindView(R.id.specialty_list)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyTV)
    TextView emptyTV;

    public static ImageView reg_specialty_img;
    SpecialtyAdatpter adatpter;
    AddSpecialtyDialog addSpecialtyDialog;
    List<SpecialtyModel> list;
    FireDatabase database;
    FireStorage storage;
    SpecialtyModel model;

    public static Bitmap specialtyImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specialty, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<SpecialtyModel>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recycler.setLayoutManager(layoutManager);
        adatpter = new SpecialtyAdatpter(getContext(), list);
        recycler.setAdapter(adatpter);

        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        loading.setVisibility(View.VISIBLE);
        database.getSpecialty(new FireDatabase.SpecialtyCallback() {
            @Override
            public void onCallback(ArrayList<SpecialtyModel> model) {
                list = model;
                if (list.size() == 0) {
                    loading.setVisibility(View.GONE);
                    emptyTV.setVisibility(View.VISIBLE);
                } else {
                    loading.setVisibility(View.GONE);
                    emptyTV.setVisibility(View.GONE);
                    Collections.reverse(list);
                    adatpter = new SpecialtyAdatpter(getContext(), list);
                    recycler.setAdapter(adatpter);
                }
            }
        });
    }

    @OnClick(R.id.btn_add)
    void btn_add(View view) {
        addSpecialtyDialog = new AddSpecialtyDialog(getContext(), new AddSpecialtyDialog.AddSpecialtyAction() {

            @Override
            public void onGetData(String specialtyEnName, String specialtyArName) {
                addSpecialtyDialog.dismiss();

                model = new SpecialtyModel();
                model.setTitleEn(specialtyEnName);
                model.setTitleAr(specialtyArName);
                if (specialtyEnName != null)
                    storage.uploadImage(specialtyImage, new FireStorage.urlCallback() {
                        @Override
                        public void onCallback(String url) {
                            model.setImage(url);
                            database.addSpecialty(model);
                        }
                    });
                else Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadImage(ImageView image) {
                reg_specialty_img = image;
                PickImageDialog.build(new PickSetup()).show(getChildFragmentManager());

            }
        });
        addSpecialtyDialog.show();
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


    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            reg_specialty_img.setImageBitmap(pickResult.getBitmap());
            specialtyImage = pickResult.getBitmap();
        }

    }

}
