package com.xpoliceservices.app;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactUsActivity extends BaseActivity {

    private WebView wvContactUs;
    private TextView tvTitle;
    private ImageView ivBack;

    @Override
    public int getRootLayout() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void initGUI() {
        wvContactUs = findViewById(R.id.wvContactUs);
        wvContactUs.loadUrl("http://www.cyberabadpolice.gov.in/contacts.html");
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void initData() {

        tvTitle.setText("Contact Us");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
