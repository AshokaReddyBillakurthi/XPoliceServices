package com.xpoliceservices.app;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class NoNetWorkActivity extends BaseActivity {

    private Button btnTryAgain;

    @Override
    public int getRootLayout() {
        return R.layout.activity_no_net_work;
    }

    @Override
    public void initGUI() {
        btnTryAgain = findViewById(R.id.btnTryAgain);
    }

    @Override
    public void initData() {
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoNetWorkActivity.this,SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}
