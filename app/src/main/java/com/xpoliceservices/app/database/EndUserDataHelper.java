package com.xpoliceservices.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.xpoliceservices.app.model.EndUser;

import java.util.ArrayList;
import java.util.List;

public class EndUserDataHelper {

    public static void insertUserData(Context context, ArrayList<EndUser> userList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();

            String insertQuery = "Insert into tblUsers(firstName,lastName," +
                    "email,password,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);

            if(null!= userList&&!userList.isEmpty()){
                for(EndUser user: userList){
                    insertStmt.bindString(1,user.firstName);
                    insertStmt.bindString(2,user.lastName);
                    insertStmt.bindString(3,user.email);
                    insertStmt.bindString(4,user.password);
                    insertStmt.bindString(5,user.mobileNumber);
                    insertStmt.bindString(6,user.state);
                    insertStmt.bindString(7,user.city);
                    insertStmt.bindString(8,user.area);
                    insertStmt.bindString(9,user.district);
                    insertStmt.bindString(10,user.subDivision);
                    insertStmt.bindString(11,user.divisionPoliceStation);
                    insertStmt.bindString(12,user.image);
                    insertStmt.bindString(13,user.userType);
                    insertStmt.executeInsert();
                }
            }
            insertStmt.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
    }

    public static boolean isValidUser(Context context,String email, String password){
        boolean isValidUser = false;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select email from tblUsers Where email = '"+email+"' and password = '"+password+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                String emailId = cursor.getString(0);
                if(!TextUtils.isEmpty(emailId))
                    isValidUser = true;
            }
            if(null!=cursor&&!cursor.isClosed())
                cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return isValidUser;
    }

    public static List<EndUser> getAllUsers(Context context){
        List<EndUser> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType from tblUsers";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    EndUser user = new EndUser();
                    user.firstName  = cursor.getString(0);
                    user.lastName = cursor.getString(1);
                    user.email = cursor.getString(2);
                    user.mobileNumber = cursor.getString(3);
                    user.state = cursor.getString(4);
                    user.city = cursor.getString(5);
                    user.area = cursor.getString(6);
                    user.district = cursor.getString(7);
                    user.subDivision = cursor.getString(8);
                    user.divisionPoliceStation = cursor.getString(9);
                    user.image = cursor.getString(10);
                    user.userType = cursor.getString(11);
                    userList.add(user);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return userList;
    }

    public static EndUser getUserByEmailId(Context context,String email){
        EndUser user = null;
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType " +
                    " from tblUsers Where email = '"+email+"'";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    user = new EndUser();
                    user.firstName = cursor.getString(0);
                    user.lastName = cursor.getString(1);
                    user.email = cursor.getString(2);
                    user.mobileNumber = cursor.getString(3);
                    user.state = cursor.getString(4);
                    user.city = cursor.getString(5);
                    user.area = cursor.getString(6);
                    user.district = cursor.getString(7);
                    user.subDivision = cursor.getString(8);
                    user.divisionPoliceStation = cursor.getString(9);
                    user.image = cursor.getString(10);
                    user.userType = cursor.getString(11);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
