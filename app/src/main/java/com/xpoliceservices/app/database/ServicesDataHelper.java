package com.xpoliceservices.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.xpoliceservices.app.model.DataModel;

import java.util.List;

public class ServicesDataHelper {

    public static void insertUpdateServices(Context context, List<DataModel.Service> serviceList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblServices(serviceCode,serviceName)" +
                    " values(?,?)";
            String updateQuery = "Update tblServices set serviceName = ?" +
                    " Where serviceCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= serviceList&&!serviceList.isEmpty()){
                for(DataModel.Service service: serviceList){
                    updateStmt.bindString(1,service.getServiceName());
                    updateStmt.bindString(2,service.getServiceCode());
                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,service.getServiceCode());
                        insertStmt.bindString(2,service.getServiceName());
                        insertStmt.executeInsert();
                    }
                }
            }
            if(null!=updateStmt)
                updateStmt.close();
            if(null!=insertStmt)
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

    public static void insertUpdateSubServices(Context context, List<DataModel.SubService> subServiceList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblSubServices(subServiceCode,subServiceName,serviceCode)" +
                    " values(?,?,?)";
            String updateQuery = "Update tblSubServices set subServiceName = ?,serviceCode=?" +
                    " Where subServiceCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= subServiceList&&!subServiceList.isEmpty()){
                for(DataModel.SubService subService: subServiceList){
                    updateStmt.bindString(1,subService.getSubServiceName());
                    updateStmt.bindString(2,subService.getServiceCode());
                    updateStmt.bindString(3,subService.getSubServiceCode());
                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,subService.getSubServiceCode());
                        insertStmt.bindString(2,subService.getSubServiceName());
                        insertStmt.bindString(3,subService.getServiceCode());
                        insertStmt.executeInsert();
                    }
                }
            }
            if(null!=updateStmt)
                updateStmt.close();
            if(null!=insertStmt)
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

}
