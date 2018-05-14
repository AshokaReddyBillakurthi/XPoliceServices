package com.xpoliceservices.app.utils;

import android.util.Log;

import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.constents.PermissionInstruConstents;

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
//                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.MY_PROFILE);
//                    menuList.add(AppConstents.CHAT);
//                    menuList.add(AppConstents.MY_SERVICES);
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

    public static List<String> getServices(){
        List<String>  servicesList = new ArrayList<>();
        try{
            servicesList.add(AppConstents.POLICE_PERMISSIONS);
            servicesList.add(AppConstents.MATRIMONIAL_VERIFICATIONS);
            servicesList.add(AppConstents.DRAFTING_COMPLAINTS);
            servicesList.add(AppConstents.POLICE_IDENTITY_ADDRESS_TRACE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return servicesList;
    }

    public static List<String> getSubServices(String serviceType){
        List<String>  permissionList = new ArrayList<>();
        try{
            switch (serviceType){
                case AppConstents.POLICE_PERMISSIONS:
                    permissionList.add(AppConstents.GUN_LICENCES);
                    permissionList.add(AppConstents.INTERNET_CAFES);
                    permissionList.add(AppConstents.SNOOKERS_PARLOURS);
                    permissionList.add(AppConstents.PARKING_PLACES);
                    permissionList.add(AppConstents.EVENTS_FUNCTIONS_MIKES);
                    permissionList.add(AppConstents.LODGES_HOTELS);
                    permissionList.add(AppConstents.FILM_TV_SHOOTINGS);
                    permissionList.add(AppConstents.POLICE_BB_FOR_PVT_FUNCTIONS);
                    break;
                case AppConstents.MATRIMONIAL_VERIFICATIONS:
                    permissionList.add(AppConstents.MARTIMONIAL_VERIFICATION);
                    break;
                case AppConstents.DRAFTING_COMPLAINTS:
                    permissionList.add(AppConstents.CRIME_REPORT);
                    permissionList.add(AppConstents.NOCS);
                    permissionList.add(AppConstents.LICENCES_RENEWALS);
                    permissionList.add(AppConstents.CERTIFIED_COPIES);
                    permissionList.add(AppConstents.RTI_AND_APPEALS_TO_HIGHER_UPS);
                    break;
                case AppConstents.POLICE_IDENTITY_ADDRESS_TRACE:
                    permissionList.add(AppConstents.PHONE_ADDRESSES);
                    permissionList.add(AppConstents.ADDHAR_ID_PROOFS);
                    permissionList.add(AppConstents.DECLARED_AND_STATED_ADDRESS);
                    break;
                default:
                    Log.d("Services", "No Services available for this type");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return permissionList;
    }

    public static List<String> getApplicationInstructions(String applicetionType){
        List<String> listInstructions = new ArrayList<>();
        try{
            switch (applicetionType){
                case AppConstents.GUN_LICENCES:{
                    listInstructions = getInstructionForArmsLicense();
                    break;
                }
                case AppConstents.INTERNET_CAFES:{
                    listInstructions = getInstructionsForCyberCafesPermission();
                    break;
                }
                default:
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listInstructions;
    }

    private static List<String> getInstructionsForCyberCafesPermission(){
        List<String> listPermissionInstructions = new ArrayList<>();
        try{
            listPermissionInstructions.add(PermissionInstruConstents.APPLICATION_ESEVE);
            listPermissionInstructions.add(PermissionInstruConstents.DETAILS);
            listPermissionInstructions.add(PermissionInstruConstents.DOCS);
            listPermissionInstructions.add(PermissionInstruConstents.MUNICIPAL_PERMISSION);
            listPermissionInstructions.add(PermissionInstruConstents.LEASE_AGR);
            listPermissionInstructions.add(PermissionInstruConstents.SITE_PLAN);
            listPermissionInstructions.add(PermissionInstruConstents.RENT_RECEIPTS);
            listPermissionInstructions.add(PermissionInstruConstents.TAX_RECEIPTS);
            listPermissionInstructions.add(PermissionInstruConstents.NOC_FIRE_DEPT);
            listPermissionInstructions.add(PermissionInstruConstents.CER_BSNL_TEL);
            listPermissionInstructions.add(PermissionInstruConstents.NOC_NEIG);
            listPermissionInstructions.add(PermissionInstruConstents.GAMBLING_MACH);
            listPermissionInstructions.add(PermissionInstruConstents.ID_RESIDENTIAL);
            listPermissionInstructions.add(PermissionInstruConstents.SERVER_CLIENT);
            listPermissionInstructions.add(PermissionInstruConstents.PARTNERSHIP_DEED);
            listPermissionInstructions.add(PermissionInstruConstents.MEM_ASSOCI);
            listPermissionInstructions.add(PermissionInstruConstents.ARTICLE_ASSOCI);
            listPermissionInstructions.add(PermissionInstruConstents.BOND);
            listPermissionInstructions.add(PermissionInstruConstents.IT_RETURNS);
            listPermissionInstructions.add(PermissionInstruConstents.FEE_PAYABLE);
            listPermissionInstructions.add(PermissionInstruConstents.SERVICE_CHARGES);
            listPermissionInstructions.add(PermissionInstruConstents.FRESH_LICENCE_CHARGE);
            listPermissionInstructions.add(PermissionInstruConstents.ANNUAL_RENEWAL_CHARGE);
            listPermissionInstructions.add(PermissionInstruConstents.SUBMIT_ESEVA);
            listPermissionInstructions.add(PermissionInstruConstents.LICENCE_ISSUED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listPermissionInstructions;
    }

    private static List<String> getInstructionForArmsLicense(){
        List<String> listInstruArmsLicense = new ArrayList<>();
        try{
            listInstruArmsLicense.add(PermissionInstruConstents.APPLICATION_ESEVE);
            listInstruArmsLicense.add(PermissionInstruConstents.FORM_A);
            listInstruArmsLicense.add(PermissionInstruConstents.DOCS);
            listInstruArmsLicense.add(PermissionInstruConstents.RESIDENTIAL_PROOF);
            listInstruArmsLicense.add(PermissionInstruConstents.ORIGINAL_ARMS_LICENCE);
            listInstruArmsLicense.add(PermissionInstruConstents.ORIGINAL_CHALLAN);
            listInstruArmsLicense.add(PermissionInstruConstents.NO_OBJECTIONS);
            listInstruArmsLicense.add(PermissionInstruConstents.NOC_FAMILY_MEMBERS);
            listInstruArmsLicense.add(PermissionInstruConstents.PASSPORT_PHOTOS);
            listInstruArmsLicense.add(PermissionInstruConstents.IT_RETURNS);
            listInstruArmsLicense.add(PermissionInstruConstents.FEE_PAYABLE);
            listInstruArmsLicense.add(PermissionInstruConstents.SERVICE_CHARGES);
            listInstruArmsLicense.add(PermissionInstruConstents.FRESH_LICENCE_CHARGE);
            listInstruArmsLicense.add(PermissionInstruConstents.ANNUAL_RENEWAL_CHARGE);
            listInstruArmsLicense.add(PermissionInstruConstents.SUBMIT_ESEVA);
            listInstruArmsLicense.add(PermissionInstruConstents.LICENCE_ISSUED);
            listInstruArmsLicense.add(PermissionInstruConstents.NOTE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listInstruArmsLicense;
    }

}
