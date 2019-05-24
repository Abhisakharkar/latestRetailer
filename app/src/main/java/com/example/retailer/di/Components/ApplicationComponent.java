package com.example.retailer.di.Components;

import android.app.Application;
import android.content.Context;

import com.example.retailer.SingletonClasses.CredentialValidator;
import com.example.retailer.SingletonClasses.FirebaseAuthHelper;
import com.example.retailer.SingletonClasses.SharedPreferencesHelper;
import com.example.retailer.SingletonClasses.VolleyHelper;
import com.example.retailer.di.Modules.ApplicationModule;
import com.example.retailer.di.RetailerApp;
import com.example.retailer.di.Scopes.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(RetailerApp retailerApp);

    @ApplicationContext
    Context getContext();
    Application getApplication();
    VolleyHelper getVolleyHelper();
    SharedPreferencesHelper getSharedPreferencesHelper();
    FirebaseAuthHelper getFirebaseAuthHelper();
    CredentialValidator getCredentailvalidator();

}
