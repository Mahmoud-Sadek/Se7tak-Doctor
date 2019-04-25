package com.sadek.se7takdoctor.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.adapter.BottomBarAdapter;
import com.sadek.se7takdoctor.adapter.NoSwipePager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainFragment extends Fragment {

    Unbinder unbinder;


    //the icons of tablayout  icon white  don't selected
    private int[] tabIcons = {
            R.drawable.icons_doctor,
            R.drawable.icon_users,
            R.drawable.icon_schedule,
            R.drawable.icon_more

    };
    // icon of tab layout selected blue icons
    private int[] tabIconsSelected = {
            R.drawable.icons_doctor_filled,
            R.drawable.icon_user_filled,
            R.drawable.icons_schedule_filled,
            R.drawable.icon_more_filled
    };

    //inti the views
    @BindView(R.id.viewpager)
    NoSwipePager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private BottomBarAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        try {
            setupViewPager(view);

            setupBottomNavBehaviors();
            setupBottomNavStyle();
            bottomNavigation.setTitleTextSize(30f, 30f);

            addBottomNavigationItems();
            bottomNavigation.setCurrentItem(0);


            bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                @Override
                public boolean onTabSelected(int position, boolean wasSelected) {

                    if (!wasSelected)
                        viewPager.setCurrentItem(position);


                    return true;
                }
            });
        } catch (Exception e) {
//            BaseActitvty.restartApp(getActivity(), getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupViewPager(View view) {
        viewPager.setPagingEnabled(false);

        pagerAdapter = new BottomBarAdapter(getChildFragmentManager());

        pagerAdapter.addFragments(createFragment(android.R.color.darker_gray, new ProfileFragment()));
        pagerAdapter.addFragments(createFragment(android.R.color.darker_gray, new PatientListFragment()));
        pagerAdapter.addFragments(createFragment(android.R.color.darker_gray, new AppointmentsStatusFragment()));
        pagerAdapter.addFragments(createFragment(android.R.color.darker_gray, new MyAccountFragment()));

        viewPager.setAdapter(pagerAdapter);
    }

    @NonNull
    private Fragment createFragment(int color, Fragment fragment) {

        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }

    @NonNull
    private Bundle passFragmentArguments(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        return bundle;
    }


    public void setupBottomNavBehaviors() {

        bottomNavigation.setTranslucentNavigationEnabled(true);
    }


    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.
        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(fetchColor(R.color.colorAccent));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(fetchColor(android.R.color.white),
                fetchColor(android.R.color.darker_gray));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setCurrentItem(0);
    }


    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.profile, tabIcons[0], R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.orders, tabIcons[1], R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.scheduel, tabIcons[2], R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.profile_setting, tabIcons[3], R.color.colorPrimary);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
    }


    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(getActivity(), color);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
