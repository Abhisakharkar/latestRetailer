package com.example.retailer.implementation.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.retailer.BaseActivity;
import com.example.retailer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.parent_profile)
    RelativeLayout parentProfile;

    @Override
    protected View getParentLayout() { return parentProfile;    }

    @Override
    protected int getView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


}
