package com.xpoliceservices.app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsAndConditionsActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    public int getRootLayout() {
        return R.layout.activity_terms_and_conditions;
    }

    @Override
    public void initGUI() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
    }

    @Override
    public void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setText(getString(R.string.termsandconditions));
    }
}
