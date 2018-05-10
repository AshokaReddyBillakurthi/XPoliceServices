package com.xpoliceservices.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootLayout());
        initGUI();
        initData();
    }

    public abstract int getRootLayout();

    public abstract void initGUI();

    public abstract void initData();
}
