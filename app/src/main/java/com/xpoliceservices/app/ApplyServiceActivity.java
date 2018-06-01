package com.xpoliceservices.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xpoliceservices.app.adapters.ServiceInstructionsAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.ApplicationDataHelper;
import com.xpoliceservices.app.database.EndUserDataHelper;
import com.xpoliceservices.app.model.ApplicationData;
import com.xpoliceservices.app.model.EndUser;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.CalenderUtils;
import com.xpoliceservices.app.utils.DataUtils;
import com.xpoliceservices.app.utils.DialogUtils;
import com.xpoliceservices.app.utils.NetworkUtils;
import com.xpoliceservices.app.utils.OkHttpUtils;
import com.xpoliceservices.app.utils.PreferenceUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApplyServiceActivity extends BaseActivity {

    private RecyclerView rvApplyServiceInstructions;
    private ImageView ivBack, ivCamera, ivUserImg;
    private TextView tvTitle,tvTermsandConditions;
    private CheckBox cbxTermsAndConditions;
    private LinearLayout llApply;
    private static final int CAMERA_CAPTURE = 1;
    private static final int PAYMENT_CODE = 105;
    private ServiceInstructionsAdapter serviceInstructionsAdapter;
    private String serviceType = "";
    private List<String> listServiceInstructions;
    private EndUser user;
    private List<ApplicationData.Application> listApplication;
    private boolean isPosted;

    @Override
    public int getRootLayout() {
        return R.layout.activity_apply_service;
    }

    @Override
    public void initGUI() {
        if(null!=getIntent().getStringExtra(AppConstents.EXTRA_SERVICE_TYPE)){
            serviceType = getIntent().getStringExtra(AppConstents.EXTRA_SERVICE_TYPE);
        }

        ivBack = findViewById(R.id.ivBack);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        tvTitle = findViewById(R.id.tvTitle);
        tvTermsandConditions = findViewById(R.id.tvTermsandConditions);
        cbxTermsAndConditions = findViewById(R.id.cbxTermsAndConditions);
        llApply = findViewById(R.id.llApply);
        rvApplyServiceInstructions = findViewById(R.id.rvApplyServiceInstructions);
        rvApplyServiceInstructions.setLayoutManager(new LinearLayoutManager(ApplyServiceActivity.this));
    }

    @Override
    public void initData() {
//        new GetUserDataAsyncTask().execute();
//        getEndUserDataFromServer(PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));


        listServiceInstructions = DataUtils.getApplicationInstructions(serviceType);
        serviceInstructionsAdapter = new ServiceInstructionsAdapter(listServiceInstructions);
        rvApplyServiceInstructions.setAdapter(serviceInstructionsAdapter);

        tvTitle.setText("Apply Service");

        tvTermsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyServiceActivity.this,
                        TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEndUserDataFromServer(PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private void getEndUserDataFromServer(String email) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.CUSTOMER_PROFILE+"email="+email);
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
                                user = new Gson().fromJson(body,EndUser.class);
                                if(isValidData()&&null!=user) {
                                    listApplication = new ArrayList<>();
                                    String uniqueID = UUID.randomUUID().toString();
                                    ApplicationData.Application application = new ApplicationData.Application();
                                    application.applicationNumber = uniqueID;
                                    application.firstName = user.firstName;
                                    application.lastName = user.lastName;
                                    application.applicationType = serviceType;
                                    application.mobileNumber = user.mobileNumber;
                                    application.email = user.email;
                                    application.area = user.area;
                                    application.city = user.city;
                                    application.state = user.state;
                                    application.district = user.district;
                                    application.subDivision = user.subDivision;
                                    application.applicationType = serviceType;
                                    application.circlePolicestation = user.divisionPoliceStation;
                                    application.status = 0;
                                    application.data = CalenderUtils.getCurrentDate();
                                    if(cbxTermsAndConditions.isChecked())
                                        application.accepted = true;
                                    application.userImage = userImg;
                                    if(NetworkUtils.isNetworkAvailable(ApplyServiceActivity.this))
                                        postDataToServer(application);
                                    else
                                        moveToNoNetWorkActivity();
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



    private boolean isValidData(){
        boolean isValid = true;
        if(TextUtils.isEmpty(userImg)){
            isValid = false;
            showToast("Please capture photo");
        } else if(!cbxTermsAndConditions.isChecked()){
            isValid = false;
            showToast("Please accept terms and conditions");
        }
        return isValid;
    }


    class GetUserDataAsyncTask extends AsyncTask<Void,Void,EndUser> {

        @Override
        protected EndUser doInBackground(Void... voids) {
            EndUser user = EndUserDataHelper.getUserByEmailId(ApplyServiceActivity.this,
                    PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
            return user;
        }

        @Override
        protected void onPostExecute(EndUser userdata) {
            super.onPostExecute(userdata);
            if(null != userdata)
                user = userdata;
        }
    }


    class SendApplicationAsyncTask extends AsyncTask<List<ApplicationData.Application>,Void,Boolean> {

        @Override
        protected Boolean doInBackground(List<ApplicationData.Application>[] arrayLists) {
            ApplicationDataHelper.insertApplicationData(ApplyServiceActivity.this,arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            DialogUtils.showDialog(ApplyServiceActivity.this, "Applied Successful",
//                    AppConstents.FINISH, false);
            Intent intent = new Intent(ApplyServiceActivity.this,PaymentActivity.class);
            intent.putExtra(AppConstents.EXTRA_APPLICATION_TYPE,serviceType);
            startActivityForResult(intent,PAYMENT_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE) {
            if(resultCode == RESULT_OK && null!=data ) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            }
            else{
                showToast("No Image Taken");
            }
        }
        else if(requestCode == PAYMENT_CODE) {

            if (requestCode == RESULT_OK) {
                DialogUtils.showDialog(ApplyServiceActivity.this, "Payment Successful", AppConstents.FINISH, false);
//                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
//                        .INTENT_EXTRA_TRANSACTION_RESPONSE);
//
//                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
//
////                Check which object is non-null
//                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
//                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
////                            new SendApplicationAsyncTask().execute(listApplications);
//                        //Success Transaction
//                        DialogUtils.showDialog(PermissionInstructionActivity.this, "Payment Successful", AppConstents.FINISH, false);
//                    } else {
//                        //Failure Transaction
//                        DialogUtils.showDialog(PermissionInstructionActivity.this, "Payment Failed, Please try again after sometime.", AppConstents.FINISH, false);
//                    }
//                }
            }
            else{
                DialogUtils.showDialog(ApplyServiceActivity.this, "Payment Failed, Please try again after sometime.", AppConstents.FINISH, false);
            }
        }
    }


    private boolean  postDataToServer(final ApplicationData.Application application) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applicationNumber", application.applicationNumber);
            jsonObject.put("firstName", application.firstName);
            jsonObject.put("lastName", application.lastName);
            jsonObject.put("email", application.email);
            jsonObject.put("mobileNumber", application.mobileNumber);
            jsonObject.put("state", application.state);
            jsonObject.put("city", "city");
            jsonObject.put( "area", "area");
            jsonObject.put("userImage", application.userImage);
            jsonObject.put("district",application.district);
            jsonObject.put("subDivision",application.subDivision);
            jsonObject.put("applicationType",application.applicationType);
            jsonObject.put("circlePolicestation",application.circlePolicestation);
            jsonObject.put("isAccepted",false);
            jsonObject.put("payableAmount",application.payableAmount);
            jsonObject.put("xserviceManEmail",application.xserviceManEmail);
            jsonObject.put("data",application.data+"");
            String body = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL + ApiServiceConstants.SAVE_APPLICATION)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .post(requestBody);
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isPosted = false;
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
                                if (body.equalsIgnoreCase("success")) {
                                    listApplication.add(application);
                                    new SendApplicationAsyncTask().execute(listApplication);
                                } else {
                                    isPosted = false;
                                    showToast(getString( R.string.error_message));
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
        return isPosted;
    }
}
