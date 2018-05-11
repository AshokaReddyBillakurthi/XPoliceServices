package com.xpoliceservices.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.xpoliceservices.app.LoginActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.constents.AppConstents;

public class DialogUtils {

    public static void showDialog(final Context context, String message, final String actionType, boolean isCancelRequired){
        AlertDialog.Builder  dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        dialog.setTitle("Alert");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                performAction(context,actionType);
            }
        });

        if(isCancelRequired){
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private static void performAction(Context context,String actionType){
        switch (actionType){
            case AppConstents.LOGOUT: {
                PreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN, false);
                PreferenceUtils.putStaringValue(AppConstents.EMAIL_ID,"");
                PreferenceUtils.putStaringValue(AppConstents.PASSWORD,"");
                PreferenceUtils.putStaringValue(AppConstents.USER_TYPE,"");
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();
                break;
            }
            case AppConstents.FINISH:
                ((Activity)context).finish();
                break;
            default:
                break;
        }
    }
}
