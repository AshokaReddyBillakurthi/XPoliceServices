package com.xpoliceservices.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.xpoliceservices.app.model.ApplicationData;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDataHelper {

    public static void insertApplicationData(Context context, List<ApplicationData.Application> applicationList){
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();

            String insertQuery = "Insert into tblApplications(applicationNo,firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    "status,isAccepted,xServiceManEmail,payableAmount,date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);

            if(null!= applicationList&&!applicationList.isEmpty()){
                for(ApplicationData.Application application: applicationList){
                    insertStmt.bindString(1,application.applicationNumber);
                    insertStmt.bindString(2,application.firstName);
                    insertStmt.bindString(3,application.lastName);
                    insertStmt.bindString(4,application.email);
                    insertStmt.bindString(5,application.mobileNumber);
                    insertStmt.bindString(6,application.state);
                    insertStmt.bindString(7,application.city);
                    insertStmt.bindString(8,application.area);
                    insertStmt.bindString(9,application.district);
                    insertStmt.bindString(10,application.subDivision);
                    insertStmt.bindString(11,application.circlePolicestation);
                    insertStmt.bindString(12,application.userImage+"");
                    insertStmt.bindString(13,application.applicationType);
                    insertStmt.bindString(14,application.status+"");
                    insertStmt.bindString(15,application.accepted+"");
                    insertStmt.bindString(16,application.xserviceManEmail+"");
                    insertStmt.bindString(17,application.payableAmount+"");
                    insertStmt.bindString(18,application.data+"");
                    insertStmt.executeInsert();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<ApplicationData.Application> getAllApplications(Context context){
        List<ApplicationData.Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount,date,applicationNo from tblApplications ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    ApplicationData.Application application = new ApplicationData.Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNumber = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.city = cursor.getString(5);
                    application.area = cursor.getString(6);
                    application.district = cursor.getString(7);
                    application.subDivision = cursor.getString(8);
                    application.circlePolicestation = cursor.getString(9);
                    application.userImage = cursor.getString(10);
                    application.applicationType = cursor.getString(11);
                    application.status = Integer.parseInt(cursor.getString(12));
                    application.accepted =Boolean.getBoolean(cursor.getString(13));
                    application.xserviceManEmail = cursor.getString(14);
                    application.payableAmount = Double.parseDouble(cursor.getString(15));
                    application.data = cursor.getString(16);
                    application.applicationNumber = cursor.getString(17);
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }

    public static List<ApplicationData.Application> getAllApplicationsByArea(Context context,String circlePolicestation){
        List<ApplicationData.Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount from tblApplications " +
                    " Where circlePolicestation = '"+circlePolicestation+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    ApplicationData.Application application = new ApplicationData.Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNumber = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.city = cursor.getString(5);
                    application.area = cursor.getString(6);
                    application.district = cursor.getString(7);
                    application.subDivision = cursor.getString(8);
                    application.circlePolicestation = cursor.getString(9);
                    application.userImage = cursor.getString(10);
                    application.applicationType = cursor.getString(11);
                    application.status = Integer.parseInt(cursor.getString(12));
                    application.accepted =Boolean.parseBoolean(cursor.getString(13));
                    application.xserviceManEmail = cursor.getString(14);
                    application.payableAmount = Double.parseDouble(cursor.getString(15));
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }

    public static List<ApplicationData.Application> getAllApplicationsByApplicationType(Context context, String applicationType){
        List<ApplicationData.Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount from tblApplications " +
                    " Where applicationType = '"+applicationType+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    ApplicationData.Application application = new ApplicationData.Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNumber = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.city = cursor.getString(5);
                    application.area = cursor.getString(6);
                    application.district = cursor.getString(7);
                    application.subDivision = cursor.getString(8);
                    application.circlePolicestation = cursor.getString(9);
                    application.userImage = cursor.getString(10);
                    application.applicationType = cursor.getString(11);
                    application.status = Integer.parseInt(cursor.getString(12));
                    application.accepted =Boolean.parseBoolean(cursor.getString(13));
                    application.xserviceManEmail = cursor.getString(14);
                    application.payableAmount = Double.parseDouble(cursor.getString(15));
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }
    public static List<ApplicationData.Application> getAllApplicationsByEmailId(Context context,String email){
        List<ApplicationData.Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount,date,applicationNo from tblApplications " +
                    " Where email = '"+email+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    ApplicationData.Application application = new ApplicationData.Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNumber = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.city = cursor.getString(5);
                    application.area = cursor.getString(6);
                    application.district = cursor.getString(7);
                    application.subDivision = cursor.getString(8);
                    application.circlePolicestation = cursor.getString(9);
                    application.userImage = cursor.getString(10);
                    application.applicationType = cursor.getString(11);
                    application.status = Integer.parseInt(cursor.getString(12));
                    application.accepted =Boolean.parseBoolean(cursor.getString(13));
                    application.xserviceManEmail = cursor.getString(14);
                    application.payableAmount = Double.parseDouble(cursor.getString(15));
                    application.data = cursor.getString(16);
                    application.applicationNumber = cursor.getString(17);
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }

    public static boolean assignApplicationToXServiceMan(Context context,String emailid,String applicationNo){
        SQLiteDatabase sqLiteDatabase = null;
        boolean isUpdated = false;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String updateQuery = "Update tblApplications set xServiceManEmail='"+emailid+"'" +
                    " Where applicationNo ='"+applicationNo+"'";
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);
            int count = updateStmt.executeUpdateDelete();
            if(count>0)
                isUpdated = true;

            if(null != updateStmt)
                updateStmt.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return isUpdated;
    }
}
