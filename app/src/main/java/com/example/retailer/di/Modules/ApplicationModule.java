package com.example.retailer.di.Modules;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.retailer.di.Scopes.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    @Singleton
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){
        return mApplication.getSharedPreferences("mypref",0);
    }

    @Provides
    @Singleton
    FirebaseAuth getFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
}