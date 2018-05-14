package com.xpoliceservices.app;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.EndUserDataHelper;
import com.xpoliceservices.app.model.EndUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class EndUserProfileActivity extends BaseActivity {

    private TextView tvTitle, tvFirstName, tvLastName, tvArea,
            tvCity, tvState,tvEmail,tvMobileNo;
    private ImageView ivBack;
    private CircleImageView ivUserImage;
    private EndUser user;
    private String email;


    @Override
    public int getRootLayout() {
        return R.layout.activity_end_user_profile;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            if(getIntent().hasExtra(AppConstents.EXTRA_EMAIL_ID))
                email = getIntent().getStringExtra(AppConstents.EXTRA_EMAIL_ID);
            if(getIntent().hasExtra(AppConstents.EXTRA_USER))
                user = (EndUser) getIntent().getExtras().getSerializable(AppConstents.EXTRA_USER);
        }

        tvTitle = findViewById(R.id.tvTitle);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvArea = findViewById(R.id.tvListName);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        ivBack = findViewById(R.id.ivBack);
        ivUserImage = findViewById(R.id.ivUserImage);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initData() {
        if (user != null) {
            setUserData(user);
        }
        else if(!TextUtils.isEmpty(email)){
            new GetUserDetailsTask().execute(email);
        }
    }

    class GetUserDetailsTask extends AsyncTask<String, Void, EndUser> {

        @Override
        protected EndUser doInBackground(String... strings) {
            user = EndUserDataHelper.getUserByEmailId(EndUserProfileActivity.this,strings[0]);
            return user;
        }

        @Override
        protected void onPostExecute(EndUser user) {
            super.onPostExecute(user);
            setUserData(user);
        }
    }


    private void setUserData(EndUser user){
        if (null!=user) {
            tvTitle.setText(user.firstName+" "+user.lastName);
            tvFirstName.setText(user.firstName+ "");
            tvLastName.setText(user.lastName + "");
            tvEmail.setText(user.email+"");
            tvMobileNo.setText(user.mobileNo+"");
            tvArea.setText(user.circlePolicestation + "");
            tvCity.setText(user.subDivision + "");
            tvState.setText(user.state + "");
            Bitmap bitmap = getUserImageBitMap(user.userImg);
            if(null!=bitmap)
                ivUserImage.setImageBitmap(bitmap);
        }
    }
}
