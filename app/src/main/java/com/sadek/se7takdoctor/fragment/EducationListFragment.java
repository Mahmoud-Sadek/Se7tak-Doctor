package com.sadek.se7takdoctor.fragment;


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
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.adapter.EducationAdatpter;
import com.sadek.se7takdoctor.adapter.SpecialtyAdatpter;
import com.sadek.se7takdoctor.dialog.AddSpecialtyDialog;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.DegreeDoctor;
import com.sadek.se7takdoctor.model.SpecialtyModel;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EducationListFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.education_list)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyTV)
    TextView emptyTV;

    EducationAdatpter adatpter;
    List<DegreeDoctor> list;
    FireDatabase database;
    FireStorage storage;
    SpecialtyModel model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<DegreeDoctor>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recycler.setLayoutManager(layoutManager);
        adatpter = new EducationAdatpter(getContext(), list);
        recycler.setAdapter(adatpter);

        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        loading.setVisibility(View.VISIBLE);
        database.getEducationDegree(new FireDatabase.DcotorDegreeCallback() {
            @Override
            public void onCallback(ArrayList<DegreeDoctor> model) {
                list = model;
                try {

                    if (list.size() == 0) {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.VISIBLE);
                    } else {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.GONE);
                        Collections.reverse(list);
                        adatpter = new EducationAdatpter(getContext(), list);
                        recycler.setAdapter(adatpter);
                    }
                }catch (Exception e){

                }
            }
        });

    }

    @OnClick(R.id.btn_add)
    void btn_add(View view) {
        ((MainActivity)getContext()).switchToPage(5,null,R.string.work);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
