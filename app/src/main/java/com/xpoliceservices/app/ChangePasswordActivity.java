package com.xpoliceservices.app;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.OkHttpUtils;
import com.xpoliceservices.app.utils.PreferenceUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePasswordActivity extends BaseActivity {

    private EditText edtCurrentPwd,edtNewPwd,edtConfirmPwd;
    private Button btnChangePassword;
    private String email, userType;

    @Override
    public int getRootLayout() {
        return R.layout.activity_change_paddword;
    }

    @Override
    public void initGUI() {

        if(null != getIntent().getExtras()){
            email = getIntent().getStringExtra(AppConstents.EXTRA_EMAIL_ID);
            userType = getIntent().getStringExtra(AppConstents.EXTRA_USER_TYPE);
        }
        edtCurrentPwd = findViewById(R.id.edtCurrentPwd);
        edtNewPwd = findViewById(R.id.edtNewPwd);
        edtConfirmPwd = findViewById(R.id.edtConfirmPwd);
        btnChangePassword = findViewById(R.id.btnChangePassword);
    }

    @Override
    public void initData() {

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private boolean isValidData(String currnetPwd,String newPassword,String confirmPwd){
        boolean isValid = true;
        if(TextUtils.isEmpty(currnetPwd)){
            showToast("Please enter Current Password");
            isValid = false;
        }
        else if(TextUtils.isEmpty(newPassword)){
            showToast("Please enter New Password");
            isValid = false;
        }
        else if(TextUtils.isEmpty(confirmPwd)){
            showToast("Please enter Confirm Password");
            isValid = false;
        }
        else if(!newPassword.equalsIgnoreCase(confirmPwd)){
            showToast("New and confirm password should match");
            isValid = false;
        }
        return isValid;
    }


    private void changePassword(){
        try{
            String currentPwd = edtCurrentPwd.getText().toString();
            String newPwd = edtNewPwd.getText().toString();
            String confirmPwd = edtConfirmPwd.getText().toString();
            if(isValidData(currentPwd,newPwd,confirmPwd)){
                updatePassword(currentPwd,newPwd);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updatePassword(String currentPassword,String newPassword){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("userType", userType);
            jsonObject.put("currentPassword", currentPassword);
            jsonObject.put("newPassword", newPassword);
            String body = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            Request.Builder builder = new Request.Builder();
            String method = "";
            if(userType.equalsIgnoreCase(AppConstents.CUSTOMER)){
                method =  ApiServiceConstants.UPDATE_PASSWORD;
            }
            else{
                method =  ApiServiceConstants.UPDATE_XSERVICEMAN_PASSWORD;
            }
            builder.url(ApiServiceConstants.MAIN_URL + method)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .post(requestBody);
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
                                if(body.equalsIgnoreCase("success")){
                                    Intent intent = new Intent(ChangePasswordActivity.this, DashBoardActivity.class);
                                    intent.putExtra(AppConstents.EXTRA_USER_TYPE, userType);
                                    PreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN, true);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    showToast(getString(R.string.error_message));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
