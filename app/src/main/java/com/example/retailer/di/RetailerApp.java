package com.example.retailer.di;

import android.app.Application;
import android.content.Context;

import com.example.retailer.di.Components.ApplicationComponent;
import com.example.retailer.di.Components.DaggerApplicationComponent;
import com.example.retailer.di.Modules.ApplicationModule;

public class RetailerApp extends Application {


    protected ApplicationComponent applicationComponent;

    public static RetailerApp get(Context context) {
        return (RetailerApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

}
