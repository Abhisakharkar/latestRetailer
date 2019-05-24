package com.example.retailer.implementation.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailer.BaseActivity;
import com.example.retailer.MainActivity;
import com.example.retailer.R;
import com.example.retailer.implementation.Profile.ProfileActivity;
import com.example.retailer.implementation.SignUp.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements SignInContract.view {

    private SignInPresenter signInPresenter;

    @BindView(R.id.mail_edittext) EditText mailEdittext;
    @BindView(R.id.pass_edittext) EditText passEdittext;
    @BindView(R.id.signin_button) Button signinButton;
    @BindView(R.id.goto_signup_button) TextView gotoSignupButton;
    @BindView(R.id.signinactivity_parent_layout) ConstraintLayout signinactivityParentLayout;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
        signInPresenter=new SignInPresenter(this,credentialValidator,firebaseAuthHelper,volleyHelper);
    }

    @Override
    protected View getParentLayout() { return signinactivityParentLayout;}

    @Override
    protected int getView() { return R.layout.activity_sign_in; }

    @OnClick({R.id.signin_button, R.id.goto_signup_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signin_button:
               signInPresenter.signIn(mailEdittext.getText().toString(),passEdittext.getText().toString());
                break;
            case R.id.goto_signup_button:
                gotoSignUpActivity();
                break;
        }
    }

    @Override
    public void gotoProfileActivity() {
        Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    @Override
    public void gotoSignUpActivity() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void gotoHomeActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
