package com.xpoliceservices.app;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.XServiceManData;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.NetworkUtils;
import com.xpoliceservices.app.utils.OkHttpUtils;
import com.xpoliceservices.app.utils.PreferenceUtils;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XServiceManProfileActivity extends BaseActivity {

    private TextView tvTitle, tvFirstName, tvLastName, tvArea,
            tvAccept, tvReject, tvDocList, tvCity, tvState,
            tvEmail, tvMobileNo, tvServices;
    private ImageView ivBack;
    private CircleImageView ivUserImage;
    private XServiceManData.XServiceman exServiceMan;
    private ToggleButton tbActive;
    private LinearLayout llActions;
    private boolean isFromMyProfile = false;
    private String userType = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_xservice_man_profile;
    }

    @Override
    public void initGUI() {
        if (null!=getIntent().getExtras()) {
            exServiceMan = (XServiceManData.XServiceman) getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_USER);

            isFromMyProfile = getIntent().getExtras()
                    .getBoolean(AppConstents.EXTRA_ISFROM_MYPROFILE,false);
        }
        userType = PreferenceUtils.getStringValue(AppConstents.USER_TYPE);
        tvTitle = findViewById(R.id.tvTitle);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvServices = findViewById(R.id.tvServices);
        tvArea = findViewById(R.id.tvListName);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        tvAccept = findViewById(R.id.tvAccept);
        tvReject = findViewById(R.id.tvReject);
        tvDocList = findViewById(R.id.tvDocList);
        ivBack = findViewById(R.id.ivBack);
        llActions = findViewById(R.id.llActions);
        ivUserImage = findViewById(R.id.ivUserImage);
        tbActive = findViewById(R.id.tbActive);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exServiceMan.status = 1;
//                new UpdateExserviceManStatus().execute(exServiceMan);
                updateXServiceManStatus(exServiceMan);
            }
        });

        tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exServiceMan.status = -1;
//                new UpdateExserviceManStatus().execute(exServiceMan);
                updateXServiceManStatus(exServiceMan);
            }
        });

        tbActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tbActive.isChecked()){
                    tbActive.setChecked(false);
                    exServiceMan.isActive = false;
                }
                else{
                    tbActive.setChecked(true);
                    exServiceMan.isActive = true;
                }
                new UpdateExserviceManActive().execute(exServiceMan);
            }
        });

        if(isFromMyProfile){
            tbActive.setVisibility(View.VISIBLE);
            llActions.setVisibility(View.GONE);
        }
        else{
            tbActive.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        if(null!=exServiceMan)
            setXServiceManData(exServiceMan);
        else{
            if(NetworkUtils.isNetworkAvailable(XServiceManProfileActivity.this)){
                getMyProfileFromServer(PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
            }
            else{
                moveToNoNetWorkActivity();
            }

        }
    }

    private class GetExserviceManDetails extends AsyncTask<Void,Void,XServiceManData.XServiceman> {

        @Override
        protected XServiceManData.XServiceman doInBackground(Void... voids) {
            return XServiceManDataHelper.getXServiceManByEmailId(XServiceManProfileActivity.this,
                    PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
        }

        @Override
        protected void onPostExecute(XServiceManData.XServiceman xServiceMan) {
            super.onPostExecute(xServiceMan);
            if(null!=xServiceMan) {
                setXServiceManData(xServiceMan);
            }
        }
    }


    private class UpdateExserviceManStatus extends AsyncTask<XServiceManData.XServiceman, Void, Boolean> {

        @Override
        protected Boolean doInBackground(XServiceManData.XServiceman... exServiceMEN) {
            boolean isUpdated = XServiceManDataHelper.updateStatus(XServiceManProfileActivity.this,
                    exServiceMEN[0].status+"",exServiceMEN[0].email);
            return isUpdated;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);
            if (isUpdated) {
                showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                        "Application Accepted Successfully");
                finish();
            } else {
                showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                        "Application Rejected Successfully");
                finish();
            }
        }
    }

    private void getMyProfileFromServer(String email) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.XSERVICEMAN_PROFILE+"email="+email);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString( R.string.error_message));
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                XServiceManData.XServiceman xServiceMan =
                                        new Gson().fromJson(body,XServiceManData.XServiceman.class);

                                setXServiceManData(xServiceMan);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class UpdateExserviceManActive extends AsyncTask<XServiceManData.XServiceman, Void, Boolean> {

        @Override
        protected Boolean doInBackground(XServiceManData.XServiceman... exServiceMEN) {
            boolean isUpdated = XServiceManDataHelper.updateUserActiveStatus(XServiceManProfileActivity.this,
                    exServiceMEN[0].isActive+"",exServiceMEN[0].email);
            return isUpdated;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);

        }
    }

    private void setXServiceManData(XServiceManData.XServiceman exServiceMan){
        try{
            if (null != exServiceMan) {
                tvTitle.setText(exServiceMan.firstName + " " + exServiceMan.lastName);
                tvFirstName.setText(exServiceMan.firstName + "");
                tvLastName.setText(exServiceMan.lastName + "");
                tvEmail.setText(exServiceMan.email + "");
                tvMobileNo.setText(exServiceMan.mobileNumber + "");
                tvServices.setText(exServiceMan.services + "");
                tvDocList.setText(exServiceMan.reqDocs + "");
                tvArea.setText(exServiceMan.divisionPoliceStation + "");
                tvCity.setText(exServiceMan.district + "");
                tvState.setText(exServiceMan.state + "");
                Bitmap bitmap = getUserImageBitMap(exServiceMan.image);
                if (bitmap != null)
                    ivUserImage.setImageBitmap(bitmap);

                if((exServiceMan.status == 0)&&userType.equalsIgnoreCase(AppConstents.ADMIN)){
                    llActions.setVisibility(View.VISIBLE);
                }
                else{
                    llActions.setVisibility(View.GONE);
                }

                if(exServiceMan.isActive){
                    tbActive.setChecked(true);
                }
                else{
                    tbActive.setChecked(false);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void updateXServiceManStatus(XServiceManData.XServiceman xServiceman){
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            if(userType.equalsIgnoreCase(AppConstents.ADMIN)){
                builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.UPDATE_XSERVICEMAN_STATUS
                        +"userType=ServiceMan&email="+xServiceman.email+"&status="+xServiceman.status);
            }
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           showToast(getString( R.string.error_message));
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(body.equalsIgnoreCase("success")){
                                    if(exServiceMan.status == 1){
                                        showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                                                "Application Accepted Successfully");
                                        finish();
                                    }
                                    else if(exServiceMan.status == -1){
                                        showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                                                "Application Rejected Successfully");
                                        finish();
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
