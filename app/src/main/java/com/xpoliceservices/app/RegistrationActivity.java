package com.xpoliceservices.app;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.fragments.EndUserRegistrationFragment;
import com.xpoliceservices.app.fragments.XServiceManRegistrationFragment;

public class RegistrationActivity extends BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack;
    private Fragment fragment;
    private String userType ="";

    @Override
    public int getRootLayout() {
        return R.layout.activity_registration;
    }

    @Override
    public void initGUI() {

        if(null!=getIntent().getExtras()){
            userType = getIntent().getStringExtra(AppConstents.EXTRA_USER_TYPE);
        }

        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)){
            fragment =  XServiceManRegistrationFragment.getInstance(userType);
        }
        else if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_CUSTOMER)){
            fragment = EndUserRegistrationFragment.getInstance(userType);
        }

        loadFragment(fragment);

        tvTitle.setText("Registration");
    }

    @Override
    public void initData() {

    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_up_out)
                    .replace(R.id.flContainer, fragment)
                    .commit();
        }
    }
}
