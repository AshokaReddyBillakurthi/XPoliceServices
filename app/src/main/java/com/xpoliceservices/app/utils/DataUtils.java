package com.xpoliceservices.app.utils;

import android.util.Log;

import com.xpoliceservices.app.constents.AppConstents;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static List<String> getMenuList(String loginType){
        List<String> menuList = new ArrayList<>();
        try{
            switch (loginType){
                case AppConstents.LOGIN_ADMIN:
                    menuList.add(AppConstents.CREATE_SERVICE);
                    menuList.add(AppConstents.SERVICE_MAN_LIST);
                    menuList.add(AppConstents.CUSTOMER_LIST);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_SERVICE_MAN:
                    menuList.add(AppConstents.APPLICATION_LIST);
                    menuList.add(AppConstents.MY_PROFILE);
                    menuList.add(AppConstents.CHAT);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_CUSTOMER:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.MY_PROFILE);
                    menuList.add(AppConstents.CHAT);
                    menuList.add(AppConstents.MY_SERVICES);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_TYPE_NONE:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.SERVICE_MAN_LIST);
                    menuList.add(AppConstents.LOGIN);
                    break;
                default:
                    Log.i("Data Manager","No List");
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return menuList;
    }

}
