package com.example.retailer.implementation.SignUp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retailer.BaseActivity;
import com.example.retailer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.signupactivity_mail_edittext)
    EditText signupactivityMailEdittext;
    @BindView(R.id.signupactivity_pass_edittext)
    EditText signupactivityPassEdittext;
    @BindView(R.id.signupactivity_confirm_pass_edittext)
    EditText signupactivityConfirmPassEdittext;
    @BindView(R.id.signupactivity_signin_button)
    Button signupactivitySigninButton;
    @BindView(R.id.signupactivity_goto_login_textview)
    TextView signupactivityGotoLoginTextview;
    @BindView(R.id.signupactivity_login_button_textview)
    TextView signupactivityLoginButtonTextview;
    @BindView(R.id.signupactivity_parent_layout)
    ConstraintLayout signupactivityParentLayout;

    @Override
    protected View getParentLayout() {
        return signupactivityParentLayout;
    }

    @Override
    protected int getView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.signupactivity_signin_button, R.id.signupactivity_login_button_textview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signupactivity_signin_button:
                break;
            case R.id.signupactivity_login_button_textview:
                break;
        }
    }
}
