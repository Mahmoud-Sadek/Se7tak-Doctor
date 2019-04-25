package com.sadek.se7takdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sadek.se7takdoctor.BaseActivity;
import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireAuth;
import com.sadek.se7takdoctor.firebase.FireDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class  LoginActivity extends BaseActivity {

    @BindView(R.id.username_input)
    EditText username_input;
    @BindView(R.id.password_input)
    EditText password_input;

    FireAuth auth;
    FireDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = new FireAuth(this);
        database = new FireDatabase(this);

    }

    @OnClick(R.id.btnLogin)
    void btnLogin(View view) {
        if (!validate()) {
            return;
        }
        auth.signIn(username_input.getText().toString(), password_input.getText().toString());
    }


    @OnClick(R.id.btnSignup)
    void btn_reg_loginpage() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btnForgetPass)
    void btn_forget_password() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));

    }

    private Boolean validate() {
        username_input.setError(null);
        password_input.setError(null);
        String email = username_input.getText().toString();
        String pass = password_input.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username_input.setError(getString(R.string.enter_valid_email));
            return false;
        } else if (pass.isEmpty() || pass.length() < 6) {
            password_input.setError(getString(R.string.enter_valid_password));
            return false;
        }
        return true;

    }

}
