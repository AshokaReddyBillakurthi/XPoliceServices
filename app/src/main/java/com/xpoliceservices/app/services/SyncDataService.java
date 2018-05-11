package com.xpoliceservices.app.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.xpoliceservices.app.database.AppDataHelper;
import com.xpoliceservices.app.database.ServicesDataHelper;
import com.xpoliceservices.app.model.DataModel;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncDataService extends IntentService {

    public final static String MY_ACTION = "MY_ACTION";

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public SyncDataService() {
        super("SyncDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            getAllData(ApiServiceConstants.GEO_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllData(final String name) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL + name);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String body = response.body().string().toString();
                    parseAddressData(body);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAddressData(String body){
        try{
            DataModel dataModel = new Gson().fromJson(body,DataModel.class);
            if(null!=dataModel){
                if(null!=dataModel.getStates()&&!dataModel.getStates().isEmpty()){
                    AppDataHelper.insertUpdateStates(getApplicationContext(),dataModel.getStates());
                }
                if(null!=dataModel.getDistricts()&&!dataModel.getDistricts().isEmpty()){
                    AppDataHelper.insertUpdateDistricts(getApplicationContext(),dataModel.getDistricts());
                }
                if(null!=dataModel.getSubDivisions()&&!dataModel.getSubDivisions().isEmpty()){
                    AppDataHelper.insertUpdateSubDivisions(getApplicationContext(),dataModel.getSubDivisions());
                }
                if(null!=dataModel.getDivisions()&&!dataModel.getDivisions().isEmpty()){
                    AppDataHelper.insertUpdateDivisionPoliceStations(getApplicationContext(),
                            dataModel.getDivisions());
                }
                if(null!=dataModel.getServices()&&!dataModel.getServices().isEmpty()){
                    ServicesDataHelper.insertUpdateServices(getApplicationContext(),dataModel.getServices());
                }
                if(null!=dataModel.getSubServices()&&!dataModel.getSubServices().isEmpty()){
                    ServicesDataHelper.insertUpdateSubServices(getApplicationContext(),dataModel.getSubServices());
                    Intent data = new Intent();
                    data.setAction(MY_ACTION);
                    data.putExtra("DATAPASSED", "Done");
                    sendBroadcast(data);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
