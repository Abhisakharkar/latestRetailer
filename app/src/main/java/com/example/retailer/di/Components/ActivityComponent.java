package com.example.retailer.di.Components;

import android.support.v7.app.AppCompatActivity;

import com.example.retailer.BaseActivity;
import com.example.retailer.di.Modules.ActivityModule;
import com.example.retailer.di.Scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

}
