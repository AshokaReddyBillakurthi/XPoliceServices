package com.xpoliceservices.app;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {

    private WebView wvAboutUs;
    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    public int getRootLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initGUI() {
        wvAboutUs = findViewById(R.id.wvAboutUs);
        wvAboutUs.loadUrl("http://www.cyberabadpolice.gov.in/about.html");

        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvTitle.setText("About Us");
    }

    @Override
    public void initData() {

    }
}
