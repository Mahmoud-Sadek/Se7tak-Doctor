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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.adapter.AppointmentAdatpter;
import com.sadek.se7takdoctor.adapter.PatientAdatpter;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.Appointment;
import com.sadek.se7takdoctor.model.Order;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.model.SpecialtyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppointmentsListFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.appointments_list)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyTV)
    TextView emptyTV;

    AppointmentAdatpter adatpter;
    List<Order> list;
    FireDatabase database;
    FireStorage storage;
    SpecialtyModel model;

    String status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<Order>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recycler.setLayoutManager(layoutManager);
        adatpter = new AppointmentAdatpter(getContext(), list);
        recycler.setAdapter(adatpter);


        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        loading.setVisibility(View.VISIBLE);

        status = getArguments().getString("status");
        database.getAppointments(status,new FireDatabase.AppointmentCallback() {
            @Override
            public void onCallback(List<Order> model) {
                list = model;
                try {

                    if (list.size() == 0) {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.VISIBLE);
                    } else {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.GONE);
                        Collections.reverse(list);
                        adatpter = new AppointmentAdatpter(getContext(), list);
                        recycler.setAdapter(adatpter);
                    }
                }catch (Exception e){

                }
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
