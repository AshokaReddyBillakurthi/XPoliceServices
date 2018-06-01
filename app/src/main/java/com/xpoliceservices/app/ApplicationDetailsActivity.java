package com.xpoliceservices.app;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xpoliceservices.app.adapters.AssignXServiceManAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.ApplicationDataHelper;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.ApplicationData;
import com.xpoliceservices.app.model.XServiceManData;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.DialogUtils;
import com.xpoliceservices.app.utils.OkHttpUtils;
import com.xpoliceservices.app.utils.PreferenceUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApplicationDetailsActivity extends BaseActivity {

    private TextView tvTitle, tvFullName, tvEmail,
            tvMobileNo, tvApplicationType,
            tvArea, tvOccupation, tvStatus,tvComments;
    private ImageView ivBack, ivUserImage;
    private EditText edtComments;
    private ApplicationData.Application application;
    private LinearLayout llAssign, llComments, llUpdateStatus,llEndUserComments;
    private String applicationNo = "";
    private String applicationType = "", circlePolicestation = "", email = "";
    private String userType = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_details;
    }

    @Override
    public void initGUI() {
        if (getIntent().getExtras() != null) {
            application = (ApplicationData.Application) getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_PERMISSION_APPLICATION);
        }

        userType = PreferenceUtils.getStringValue(AppConstents.USER_TYPE);
        tvTitle = findViewById(R.id.tvTitle);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvApplicationType = findViewById(R.id.tvApplicationType);
        tvArea = findViewById(R.id.tvListName);
        tvStatus = findViewById(R.id.tvStatus);
        tvComments = findViewById(R.id.tvComments);
        edtComments = findViewById(R.id.edtComments);
        ivBack = findViewById(R.id.ivBack);
        ivUserImage = findViewById(R.id.ivUserImage);
        llAssign = findViewById(R.id.llAssign);
        llComments = findViewById(R.id.llComments);
        llEndUserComments = findViewById(R.id.llEndUserComments);
        llUpdateStatus = findViewById(R.id.llUpdateStatus);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if (null != application) {
            applicationNo = application.applicationNumber;
            applicationType = application.applicationType;
            circlePolicestation = application.circlePolicestation;
            email = application.email;
            tvFullName.setText(application.firstName + "");
            tvOccupation.setText(application.lastName + "");
            tvEmail.setText(application.email + "");
            tvMobileNo.setText(application.mobileNumber + "");
            tvApplicationType.setText(application.applicationType + "");
            tvArea.setText(application.circlePolicestation + "");
            tvTitle.setText(application.applicationType + "");
            tvComments.setText(application.comment+"");
            if (application.status == 0) {
                tvStatus.setText(AppConstents.PENDING);
                tvStatus.setTextColor(getColor(R.color.red));
            } else if (application.status == 1) {
                tvStatus.setText(AppConstents.INPROGRESS);
                tvStatus.setTextColor(getColor(R.color.orange));
            } else if (application.status == 2) {
                tvStatus.setText(AppConstents.COMPLETED);
                tvStatus.setTextColor(getColor(R.color.green));
            }
            Bitmap bitmap = getUserImageBitMap(application.userImage);
            if (bitmap != null) {
                ivUserImage.setImageBitmap(bitmap);
            }

            if ((userType.equalsIgnoreCase(AppConstents.ADMIN))
                    ||(userType.equalsIgnoreCase(AppConstents.CUSTOMER))) {
                llComments.setVisibility(View.GONE);
                llUpdateStatus.setVisibility(View.GONE);
                llEndUserComments.setVisibility(View.GONE);
                if(userType.equalsIgnoreCase(AppConstents.CUSTOMER))
                    llEndUserComments.setVisibility(View.VISIBLE);
            } else if (userType.equalsIgnoreCase(AppConstents.XSERVICEMAN)) {
                llComments.setVisibility(View.VISIBLE);
                llUpdateStatus.setVisibility(View.VISIBLE);
                llEndUserComments.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(application.xserviceManEmail)
                    && userType.equalsIgnoreCase(AppConstents.ADMIN)) {
                llAssign.setVisibility(View.VISIBLE);
            } else {
                llAssign.setVisibility(View.GONE);
            }
        }

        llAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (application != null) {
//                    new GetAllServiceMansRelatedToDivision().execute(application.circlePolicestation);
                    getXServiceMansFromServer();
                }
            }
        });

        llUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = edtComments.getText().toString();
                updateApplicationStatus(comments);
            }
        });
    }


    class GetAllServiceMansRelatedToDivision extends AsyncTask<String, Void, List<XServiceManData.XServiceman>> {

        @Override
        protected List<XServiceManData.XServiceman> doInBackground(String... strings) {
            return XServiceManDataHelper.getAllXServiceMansBasedOnDivision(ApplicationDetailsActivity.this,
                    strings[0]);
        }

        @Override
        protected void onPostExecute(List<XServiceManData.XServiceman> exServiceMEN) {
            super.onPostExecute(exServiceMEN);
            if (null != exServiceMEN) {
                showAssignXserviceManDialog(exServiceMEN);
            }
        }
    }

    public void showAssignXserviceManDialog(List<XServiceManData.XServiceman> xServiceManList) {
        final Dialog dialog = new Dialog(ApplicationDetailsActivity.this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_assign_xserviceman);
        RecyclerView rvXserviceMans = dialog.findViewById(R.id.rvXserviceMans);
        rvXserviceMans.setLayoutManager(new LinearLayoutManager(ApplicationDetailsActivity.this));
        AssignXServiceManAdapter assignXServiceManAdapter = new AssignXServiceManAdapter(xServiceManList,
                new AssignXServiceManAdapter.OnXServiceManSelectListener() {
                    @Override
                    public void onXServiceManSelect(XServiceManData.XServiceman xServiceMan) {
//                new ServiceAssignAsyncTask().execute(xServiceMan.email);
                        assignApplicationToXServiceMan(xServiceMan.email);
                        dialog.dismiss();
                    }
                });
        rvXserviceMans.setAdapter(assignXServiceManAdapter);
        dialog.show();
    }

    class ServiceAssignAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return ApplicationDataHelper.assignApplicationToXServiceMan(
                    ApplicationDetailsActivity.this, strings[0], applicationNo);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean)
                DialogUtils.showDialog(ApplicationDetailsActivity.this, "Application Assigned Successfully",
                        AppConstents.FINISH, true);
        }
    }


    private void getXServiceMansFromServer() {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL
                    + ApiServiceConstants.GET_ASSIGNABLEEXSERVICEMEN
                    + "applicationType=" + applicationType + "&circlePolicestation=" + circlePolicestation);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.error_message));
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
                                XServiceManData xServiceManData = new Gson().fromJson(body, XServiceManData.class);
                                if (null != xServiceManData &&null != xServiceManData.getExServiceMen()
                                        && !xServiceManData.getExServiceMen().isEmpty()) {
                                        showAssignXserviceManDialog(xServiceManData.getExServiceMen());
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

    private void assignApplicationToXServiceMan(String xServiceManEmail) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL
                    + ApiServiceConstants.ASSIGN_XSERVICEMAN
                    + "applicationNumber=" + applicationNo + "&email=" + email + "&xServiceManEmail=" + xServiceManEmail);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.error_message));
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
                                    DialogUtils.showDialog(ApplicationDetailsActivity.this,
                                            "Application Assigned Successfully",
                                            AppConstents.FINISH, true);
                                } else {
                                    DialogUtils.showDialog(ApplicationDetailsActivity.this,
                                            getString(R.string.error_message),
                                            AppConstents.FINISH, true);
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

    private void updateApplicationStatus(String comments) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL
                    + ApiServiceConstants.UPDATE_APPLICATIONSTATUS
                    + "applicationNumber=" + applicationNo + "&comment=" + comments);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.error_message));
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
                                    DialogUtils.showDialog(ApplicationDetailsActivity.this,
                                            "Status Updated Successfully",
                                            AppConstents.FINISH, true);
                                } else {
                                    DialogUtils.showDialog(ApplicationDetailsActivity.this,
                                            getString(R.string.error_message),
                                            AppConstents.FINISH, true);
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
