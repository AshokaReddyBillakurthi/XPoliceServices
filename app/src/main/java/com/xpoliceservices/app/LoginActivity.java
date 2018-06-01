package com.xpoliceservices.app;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.EndUserDataHelper;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.NetworkUtils;
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

public class LoginActivity extends BaseActivity {

    private TextView tvUserType;
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private TextView tvSkipLogin;
    private String userType = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initGUI() {
        tvUserType = findViewById(R.id.tvUserType);
        tvSkipLogin = findViewById(R.id.tvSkipLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    public void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (isValidData(email, password, userType)) {
                    moveToNext(email,password);
                }
            }
        });

        tvUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserTypeDialog();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userType)) {
                    showUserTypeDialog();
                } else {
                    if (!TextUtils.isEmpty(userType)
                            && !(userType.equals(AppConstents.USER_TYPE_ADMIN))) {
                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        intent.putExtra(AppConstents.EXTRA_USER_TYPE, userType);
                        startActivity(intent);

                    } else {
                        showToast("Please select login type as either service man or customer");
                    }
                }
            }
        });
    }


    private void moveToNext(String email, String password) {
        if (email.equalsIgnoreCase("admin@gmail.com") && password.equalsIgnoreCase("password")) {
            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
            PreferenceUtils.putStaringValue(AppConstents.EMAIL_ID, "admin@gmail.com");
            PreferenceUtils.putStaringValue(AppConstents.PASSWORD, "password");
            PreferenceUtils.putStaringValue(AppConstents.USER_TYPE, userType);
            PreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN, true);
            intent.putExtra(AppConstents.EXTRA_USER_TYPE, userType);
            startActivity(intent);
            finish();
        } else {
            String args[] = new String[3];
            args[0] = email;
            args[1] = password;
            args[2] = userType;
            PreferenceUtils.putStaringValue(AppConstents.EMAIL_ID, email);
            PreferenceUtils.putStaringValue(AppConstents.PASSWORD, password);
            PreferenceUtils.putStaringValue(AppConstents.USER_TYPE, userType);
//            new LoginAsyncTask().execute(args);
            if(NetworkUtils.isNetworkAvailable(LoginActivity.this)){
                checkLogin(email,password);
            }
            else{
                moveToNoNetWorkActivity();
            }
        }
    }


    public void showUserTypeDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_user_type);

        TextView tvAdmin = dialog.findViewById(R.id.tvAdmin);
        TextView tvXServiceMan = dialog.findViewById(R.id.tvXServiceMan);
        TextView tvCustomer = dialog.findViewById(R.id.tvCustomer);

        tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = AppConstents.USER_TYPE_ADMIN;
                tvUserType.setText(userType);
                btnRegister.setVisibility(View.GONE);
                edtEmail.setText("");
                edtPassword.setText("");
                dialog.dismiss();
            }
        });
        tvXServiceMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = AppConstents.USER_TYPE_SERVICEMAN;
                tvUserType.setText(userType);
                btnRegister.setVisibility(View.VISIBLE);
                edtEmail.setText("");
                edtPassword.setText("");
                dialog.dismiss();
            }
        });
        tvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = AppConstents.USER_TYPE_CUSTOMER;
                tvUserType.setText(userType);
                btnRegister.setVisibility(View.VISIBLE);
                edtEmail.setText("");
                edtPassword.setText("");
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private boolean isValidData(String email, String password, String userType) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email)) {
            showToast("Please enter emild id");
            isValid = false;
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            showToast("Please enter valid email id");
            isValid = false;
        } else if (TextUtils.isEmpty(password)) {
            showToast("Please enter password");
            isValid = false;
        } else if (TextUtils.isEmpty(userType)) {
            showToast("Please select user type");
            isValid = false;
        }
        return isValid;
    }

    class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean isValidUser = false;
            if (userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)) {
                isValidUser = XServiceManDataHelper.isValidXServiceMan(LoginActivity.this, strings[0], strings[1]);
            } else {
                isValidUser = EndUserDataHelper.isValidUser(LoginActivity.this, strings[0], strings[1]);
            }
            return isValidUser;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                intent.putExtra(AppConstents.EXTRA_USER_TYPE, PreferenceUtils.getStringValue(AppConstents.USER_TYPE));
                PreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN, true);
                startActivity(intent);
                finish();
            } else {
                showToast("Please enter proper details");
            }
        }
    }


    private void checkLogin(final String email, final String password){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("userType", userType);
            jsonObject.put("password", password);
//            Request.Builder builder = new Request.Builder();
//            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.USER_LOGIN+"email="+email+
//                    "&password="+password+"&userType="+userType);
//            builder.get();
            String body = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            Request.Builder builder = new Request.Builder();
            String method = "";
            if(userType.equalsIgnoreCase(AppConstents.CUSTOMER)){
                method = ApiServiceConstants.CUSTOMER_LOGIN;
            }
            else{
                method = ApiServiceConstants.SERVICEMAN_LOGIN;
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
                                if(body.equalsIgnoreCase("updatePassword")){
                                    Intent intent = new Intent(LoginActivity.this,ChangePasswordActivity.class);
                                    intent.putExtra(AppConstents.EXTRA_EMAIL_ID,email);
                                    intent.putExtra(AppConstents.EXTRA_USER_TYPE, userType);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(body.equalsIgnoreCase("success")){
                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
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
}
