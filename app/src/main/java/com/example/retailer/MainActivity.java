package com.example.retailer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.parent)
    ConstraintLayout parent;


    @Override
    protected View getParentLayout() {
        return parent;
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
