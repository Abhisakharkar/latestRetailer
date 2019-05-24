package com.example.retailer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.retailer.SingletonClasses.CredentialValidator;
import com.example.retailer.SingletonClasses.FirebaseAuthHelper;
import com.example.retailer.SingletonClasses.SharedPreferencesHelper;
import com.example.retailer.SingletonClasses.VolleyHelper;
import com.example.retailer.di.Components.ActivityComponent;
import com.example.retailer.di.Components.DaggerActivityComponent;
import com.example.retailer.di.Modules.ActivityModule;
import com.example.retailer.di.RetailerApp;

import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Inject
    protected VolleyHelper volleyHelper;
    @Inject
    protected SharedPreferencesHelper sharedPreferencesHelper;
    @Inject
    protected CredentialValidator credentialValidator;
    @Inject
    protected FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        getActivityComponent().inject(this);
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(RetailerApp.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    public void showSnackbar(String message, int length) {
        final Snackbar snackbar = Snackbar.make(getParentLayout(), message, length);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    protected abstract View getParentLayout();

    protected abstract int getView();


}
