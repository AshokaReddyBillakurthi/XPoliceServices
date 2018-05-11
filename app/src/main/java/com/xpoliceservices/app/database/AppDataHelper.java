package com.xpoliceservices.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.xpoliceservices.app.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class AppDataHelper {

    public static void insertUpdateStates(Context context,List<DataModel.State> stateList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblStates(stateCode,stateName)" +
                    " values(?,?)";
            String updateQuery = "Update tblStates set stateName = ?" +
                    " Where stateCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= stateList&&!stateList.isEmpty()){
                for(DataModel.State state: stateList){
                    updateStmt.bindString(1,state.getStateName());
                    updateStmt.bindString(2,state.getStateCode());
                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,state.getStateCode());
                        insertStmt.bindString(2,state.getStateName());
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

    public static void insertUpdateDistricts(Context context,List<DataModel.District> districtList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblDistrict(districtCode,districtName,stateCode) " +
                    " values(?,?,?)";
            String updateQuery = "Update tblDistrict set districtName = ?,stateCode = ?" +
                    " Where districtCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);
            if(null!= districtList&&!districtList.isEmpty()){
                for(DataModel.District district: districtList){
                    updateStmt.bindString(1,district.getDistrictName());
                    updateStmt.bindString(2,district.getStateCode());
                    updateStmt.bindString(3,district.getDistrictCode());

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,district.getDistrictCode());
                        insertStmt.bindString(2,district.getDistrictName());
                        insertStmt.bindString(3,district.getStateCode());
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

    public static void insertUpdateSubDivisions(Context context,List<DataModel.SubDivision> subDivisionList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblSubDivision " +
                    "(subDivisionCode,subDivisionName,districtCode) values(?,?,?)";
            String updateQuery = "Update tblSubDivision set subDivisionName = ?," +
                    "districtCode=? Where subDivisionCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= subDivisionList&&!subDivisionList.isEmpty()){
                for(DataModel.SubDivision subDivision: subDivisionList){
                    updateStmt.bindString(1,subDivision.getSubDivisionName());
                    updateStmt.bindString(2,subDivision.getDistrictCode());
                    updateStmt.bindString(3,subDivision.getSubDivisionCode());

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,subDivision.getSubDivisionCode());
                        insertStmt.bindString(2,subDivision.getSubDivisionName());
                        insertStmt.bindString(3,subDivision.getDistrictCode());
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
    public static void insertUpdateDivisionPoliceStations(Context context,List<DataModel.Division>
                                                                   divisionPoliceStationList) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String insertQuery = "Insert into tblDivisionPoliceStation " +
                    "(divisionPoliceStationCode,divisionPoliceStationName,subDivisionCode) values(?,?,?)";
            String updateQuery = "Update tblDivisionPoliceStation set divisionPoliceStationName = ?," +
                    " subDivisionCode=? Where divisionPoliceStationCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if (null != divisionPoliceStationList && !divisionPoliceStationList.isEmpty()) {
                for (DataModel.Division divisionPoliceStation : divisionPoliceStationList) {
                    updateStmt.bindString(1, divisionPoliceStation.getDivisionName());
                    updateStmt.bindString(2, divisionPoliceStation.getSubDivisionCode());
                    updateStmt.bindString(3, divisionPoliceStation.getDivisionCode());

                    if (updateStmt.executeUpdateDelete() <= 0) {
                        insertStmt.bindString(1, divisionPoliceStation.getDivisionCode());
                        insertStmt.bindString(2, divisionPoliceStation.getDivisionName());
                        insertStmt.bindString(3, divisionPoliceStation.getSubDivisionCode());
                        insertStmt.executeInsert();
                    }
                }
            }
            if (null != updateStmt)
                updateStmt.close();
            if (null != insertStmt)
                insertStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
    }

    public static List<DataModel.State> getAllStates(Context context){
        List<DataModel.State> statesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select stateCode,stateName from tblStates ";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DataModel.State state = null;
                do{
                    state = new DataModel.State();
                    state.setStateCode(cursor.getString(0));
                    state.setStateName(cursor.getString(1));
                    statesList.add(state);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return statesList;
    }
    public static List<DataModel.District> getAllDistrictByStateCode(Context context,String stateCode){
        List<DataModel.District> districtList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select districtCode,districtName from tblDistrict Where stateCode='"+stateCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DataModel.District district = null;
                do{
                    district = new DataModel.District();
                    district.setDistrictCode(cursor.getString(0));
                    district.setDistrictName(cursor.getString(1));
                    districtList.add(district);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return districtList;
    }

    public static List<DataModel.SubDivision> getAllSubDivisionsByDistrictCode(Context context,
                                                                                  String districtCode){
        List<DataModel.SubDivision> subDivisionList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select subDivisionCode,subDivisionName from tblSubDivision " +
                    "Where districtCode='"+districtCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DataModel.SubDivision subDivision = null;
                do{
                    subDivision = new DataModel.SubDivision();
                    subDivision.setSubDivisionCode(cursor.getString(0));
                    subDivision.setSubDivisionName(cursor.getString(1));
                    subDivisionList.add(subDivision);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return subDivisionList;
    }
    public static List<DataModel.Division> getAllDivisionPoliceStationByDistrictCode(Context context,
                                                                                                     String subDivisionCode){
        List<DataModel.Division> divisionPoliceStationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select divisionPoliceStationCode,divisionPoliceStationName " +
                    "from tblDivisionPoliceStation Where subDivisionCode = '"+subDivisionCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DataModel.Division divisionPoliceStation = null;
                do{
                    divisionPoliceStation = new DataModel.Division();
                    divisionPoliceStation.setDivisionCode(cursor.getString(0));
                    divisionPoliceStation.setDivisionName(cursor.getString(1));
                    divisionPoliceStationList.add(divisionPoliceStation);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return divisionPoliceStationList;
    }

    public static List<DataModel.Division> getAllDivisionPoliceStations(Context context){
        List<DataModel.Division> divisionPoliceStationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select divisionPoliceStationCode,divisionPoliceStationName " +
                    "from tblDivisionPoliceStation ";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DataModel.Division divisionPoliceStation = null;
                do{
                    divisionPoliceStation = new DataModel.Division();
                    divisionPoliceStation.setDivisionCode(cursor.getString(0));
                    divisionPoliceStation.setDivisionName(cursor.getString(1));
                    divisionPoliceStationList.add(divisionPoliceStation);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return divisionPoliceStationList;
    }
}
