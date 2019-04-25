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
import com.sadek.se7takdoctor.adapter.EducationAdatpter;
import com.sadek.se7takdoctor.adapter.PatientAdatpter;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.firebase.FireStorage;
import com.sadek.se7takdoctor.model.DegreeDoctor;
import com.sadek.se7takdoctor.model.Patient;
import com.sadek.se7takdoctor.model.SpecialtyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PatientListFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.patient_list)
    RecyclerView recycler;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.emptyTV)
    TextView emptyTV;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    PatientAdatpter adatpter;
    List<Patient> list;
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
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<Patient>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recycler.setLayoutManager(layoutManager);
        adatpter = new PatientAdatpter(getContext(), list);
        recycler.setAdapter(adatpter);

        intSearch();
        storage = new FireStorage(getContext());
        database = new FireDatabase(getContext());
        loading.setVisibility(View.VISIBLE);
        database.getPatients(new FireDatabase.PatientCallback() {
            @Override
            public void onCallback(List<Patient> model) {
                list = model;
                try {

                    if (list.size() == 0) {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.VISIBLE);
                    } else {
                        loading.setVisibility(View.GONE);
                        emptyTV.setVisibility(View.GONE);
                        Collections.reverse(list);
                        adatpter = new PatientAdatpter(getContext(), list);
                        recycler.setAdapter(adatpter);
                    }
                }catch (Exception e){

                }
            }
        });

    }

    @OnClick(R.id.btn_add)
    void btn_add(View view) {
        ((MainActivity)getContext()).switchToPage(12,null,R.string.work);
    }

    private void intSearch() {
        //Search
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                mSearchView.showProgress();
               List<Patient> suggest = new ArrayList<>();
                for (Patient search : list) {
                    if (search.getName().toLowerCase().contains(currentQuery.toLowerCase()))
                        suggest.add(search);
                }
                adatpter = new PatientAdatpter(getContext(), suggest);
                recycler.setAdapter(adatpter);
                mSearchView.hideProgress();
            }
        });
        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                mSearchView.showProgress();
                adatpter = new PatientAdatpter(getContext(), list);
                recycler.setAdapter(adatpter);
                mSearchView.hideProgress();
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (newQuery.equals("")) {
                    mSearchView.showProgress();
                    adatpter = new PatientAdatpter(getContext(), list);
                    recycler.setAdapter(adatpter);
                    mSearchView.hideProgress();
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
