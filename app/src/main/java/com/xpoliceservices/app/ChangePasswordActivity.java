package com.xpoliceservices.app;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordActivity extends BaseActivity {

    private EditText edtCurrentPwd,edtNewPwd,edtConfirmPwd;
    private Button btnChangePassword;

    @Override
    public int getRootLayout() {
        return R.layout.activity_change_paddword;
    }

    @Override
    public void initGUI() {
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
            showToast("Please enter current password");
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
        return isValid;
    }


    private void changePassword(){
        try{
            String currentPwd = edtCurrentPwd.getText().toString();
            String newPwd = edtNewPwd.getText().toString();
            String confirmPwd = edtConfirmPwd.getText().toString();
            if(isValidData(currentPwd,newPwd,confirmPwd)){

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
