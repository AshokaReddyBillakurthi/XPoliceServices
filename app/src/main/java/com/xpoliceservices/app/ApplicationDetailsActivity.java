package com.xpoliceservices.app;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpoliceservices.app.adapters.AssignXServiceManAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.ApplicationDataHelper;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.ApplicationData;
import com.xpoliceservices.app.model.XServiceManData;
import com.xpoliceservices.app.utils.DialogUtils;

import java.util.List;

public class ApplicationDetailsActivity extends BaseActivity {


    private TextView tvTitle,tvFullName,tvEmail,tvMobileNo,tvApplicationType,
            tvArea,tvOccupation,tvStatus;
    private ImageView ivBack,ivUserImage;
    private ApplicationData.Application application;
    private LinearLayout llAssign;
    private String applicationNo="";

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_details;
    }

    @Override
    public void initGUI() {
        if(getIntent().getExtras()!=null){
            application = (ApplicationData.Application)getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_PERMISSION_APPLICATION);
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvApplicationType = findViewById(R.id.tvApplicationType);
        tvArea = findViewById(R.id.tvListName);
        tvStatus = findViewById(R.id.tvStatus);
        ivBack = findViewById(R.id.ivBack);
        ivUserImage = findViewById(R.id.ivUserImage);
        llAssign = findViewById(R.id.llAssign);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if(null!=application){
            applicationNo = application.applicationNumber;
            tvFullName.setText(application.firstName+"");
            tvOccupation.setText(application.lastName+"");
            tvEmail.setText(application.email+"");
            tvMobileNo.setText(application.mobileNumber+"");
            tvApplicationType.setText(application.applicationType+"");
            tvArea.setText(application.circlePolicestation+"");
            tvTitle.setText(application.applicationType+"");
            if(application.status == 0) {
                tvStatus.setText(AppConstents.PENDING);
                tvStatus.setTextColor(getColor(R.color.red));
            }
            else if(application.status == 1){
                tvStatus.setText(AppConstents.INPROGRESS);
                tvStatus.setTextColor(getColor(R.color.orange));
            }
            else if(application.status == 2){
                tvStatus.setText(AppConstents.COMPLETED);
                tvStatus.setTextColor(getColor(R.color.green));
            }
            Bitmap bitmap = getUserImageBitMap(application.userImage);
            if(bitmap!=null){
                ivUserImage.setImageBitmap(bitmap);
            }

            if(TextUtils.isEmpty(application.xserviceManEmail)){
                llAssign.setVisibility(View.VISIBLE);
            }
            else{
                llAssign.setVisibility(View.GONE);
            }
        }

        llAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application!=null){
                    new GetAllServiceMansRelatedToDivision().execute(application.circlePolicestation);
                }
            }
        });
    }


    class GetAllServiceMansRelatedToDivision extends AsyncTask<String,Void,List<XServiceManData.XServiceman>> {

        @Override
        protected List<XServiceManData.XServiceman> doInBackground(String... strings) {
            return XServiceManDataHelper.getAllXServiceMansBasedOnDivision(ApplicationDetailsActivity.this,
                    strings[0]);
        }

        @Override
        protected void onPostExecute(List<XServiceManData.XServiceman> exServiceMEN) {
            super.onPostExecute(exServiceMEN);
            if(null!=exServiceMEN){
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
                new ServiceAssignAsyncTask().execute(xServiceMan.email);
                dialog.dismiss();
            }
        });
        rvXserviceMans.setAdapter(assignXServiceManAdapter);
        dialog.show();
    }

    class ServiceAssignAsyncTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            return ApplicationDataHelper.assignApplicationToXServiceMan(
                    ApplicationDetailsActivity.this, strings[0],applicationNo);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
                 DialogUtils.showDialog(ApplicationDetailsActivity.this,"Application Assigned Successfully",
                    AppConstents.FINISH,true);
        }
    }
}
