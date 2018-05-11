package com.xpoliceservices.app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.services.SyncDataService;
import com.xpoliceservices.app.utils.PreferenceUtils;

public class SplashActivity extends BaseActivity {

    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private MyReceiver myReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SyncDataService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public int getRootLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initGUI() {

    }

    @Override
    public void initData() {
        Intent intent = new Intent(SplashActivity.this,SyncDataService.class);
        startService(intent);
    }

    private void checkPermissions(){
        if(ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }/* else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        showToast("Go to Permissions to Grant  Camera and Location");
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }*/  else {
                //just request the permission
                ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    proceedAfterPermission();
                }
            },500);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,permissionsRequired[2])){
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                showToast("Unable to get Permission");
            }
        }
    }


    private void proceedAfterPermission(){
        try{
            if(PreferenceUtils.getBoolValue(AppConstents.IS_LOGGEDIN)){
                Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                intent.putExtra(AppConstents.EXTRA_USER_TYPE, PreferenceUtils.getStringValue(AppConstents.USER_TYPE));
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            String  datapassed = arg1.getStringExtra("DATAPASSED");

            if(datapassed.equalsIgnoreCase("Done")){
                checkPermissions();
            }

//            Toast.makeText(SplashActivity.this,
//                    "Triggered by Service!\n"
//                            + "Data passed: " + String.valueOf(datapassed),
//                    Toast.LENGTH_LONG).show();

        }

    }
}
