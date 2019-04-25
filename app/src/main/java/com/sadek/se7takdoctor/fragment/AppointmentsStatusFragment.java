package com.sadek.se7takdoctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.adapter.CustomFragmentPagerAdapter;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.utils.Common;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppointmentsStatusFragment extends Fragment {

    Unbinder unbinder;

    ViewPager p;
    TabLayout tabsStrip;
    CustomFragmentPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointments_status, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            unbinder = ButterKnife.bind(this, view);
//            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbars);
//            toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
            TextView tabTxt = view.findViewById(R.id.tabTxt);
            tabTxt.setText(R.string.appintments);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().onBackPressed();
//                }
//            });


            adapter = new CustomFragmentPagerAdapter(getChildFragmentManager());

            AppointmentsListFragment pindingOrdersFragment = new AppointmentsListFragment();
            AppointmentsListFragment acceptedOrdersFragment = new AppointmentsListFragment();
            AppointmentsListFragment completedOrdersFragment = new AppointmentsListFragment();
            AppointmentsListFragment rejectedOrdersFragment = new AppointmentsListFragment();
            AppointmentsListFragment canceledOedersFragment = new AppointmentsListFragment();
            adapter.addFragment(pindingOrdersFragment, getString(R.string.pending), Common.ORDER_STATUS_PENDING);
            adapter.addFragment(acceptedOrdersFragment, getString(R.string.accepted), Common.ORDER_STATUS_ACCEPTED);
            adapter.addFragment(completedOrdersFragment, getString(R.string.compeleted), Common.ORDER_STATUS_COMPLETED);
            adapter.addFragment(rejectedOrdersFragment, getString(R.string.rejected), Common.ORDER_STATUS_REJECTED);
            adapter.addFragment(canceledOedersFragment, getString(R.string.canceled), Common.ORDER_STATUS_CANCELED);
            p = view.findViewById(R.id.orders_pager);
            tabsStrip = (TabLayout) view.findViewById(R.id.orders_status_tabs);
            p.setAdapter(adapter);
            tabsStrip.setupWithViewPager(p);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_add)
    void btn_add(View view) {
        ((MainActivity)getContext()).switchToPage(13,null,R.string.work);
    }
}
