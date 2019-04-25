package com.sadek.se7takdoctor.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.activity.SplashActivity;
import com.sadek.se7takdoctor.dialog.LanguageDialog;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Doctor;
import com.sadek.se7takdoctor.utils.Common;
import com.sadek.se7takdoctor.utils.LocaleUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.paperdb.Paper;

import static com.sadek.se7takdoctor.BaseActivity.restartApp;

public class MyAccountFragment extends Fragment {

    Unbinder unbinder;
    LanguageDialog languageDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);


    }

    @OnClick(R.id.editAccountBtn)
    void editAccountBtn(View view) {
        ((MainActivity) getContext()).switchToPage(10, null, R.string.about);
    }

    @OnClick(R.id.languageBtn)
    void languageBtn(View view) {
        channgeLanguage();
    }

    @OnClick(R.id.logoutBtn)
    void logoutBtn(View view) {
        //Ask to Login
        new MaterialDialog.Builder(getContext())
                .title(R.string.app_name)
                .content(R.string.logoutask)
                .positiveText(R.string.log_out)
                .negativeText(R.string.dismisss)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), SplashActivity.class));
                        getActivity().finish();
                    }
                })
                .show();
    }


    private void channgeLanguage() {

        languageDialog = new LanguageDialog(getContext(), new LanguageDialog.languageDialogAction() {
            @Override
            public void onGetCode(boolean langStatus) {
                if (langStatus) {

                    Paper.book().write(Common.language, LocaleUtils.ARABIC);
                    restartApp(getContext(), getActivity());
                } else {
                    Paper.book().write(Common.language, LocaleUtils.ENGLISH);
                    restartApp(getContext(), getActivity());
                }
            }
        });
        languageDialog.show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

}
