package com.xpoliceservices.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xpoliceservices.app.DashBoardActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.XServiceManListAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.XServiceManData;
import com.xpoliceservices.app.utils.ApiServiceConstants;
import com.xpoliceservices.app.utils.OkHttpUtils;
import com.xpoliceservices.app.utils.PreferenceUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XServiceMansListFragment extends BaseFragment {

    private RecyclerView rvXServiceManList;
    private XServiceManListAdapter xServiceManListAdapter;
    private List<XServiceManData.XServiceman> xServiceManList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xserviceman_list, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashBoardActivity)getContext()).tvScreenTitle.setText("Service Man List");
        rvXServiceManList = view.findViewById(R.id.rvXServiceManList);
        rvXServiceManList.setLayoutManager(new LinearLayoutManager(getContext()));
        xServiceManListAdapter = new XServiceManListAdapter(getContext());
        rvXServiceManList.setAdapter(xServiceManListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getXServiceMansFromServer();
//        new GetExserviceManListTask().execute();
    }

    private class GetExserviceManListTask extends AsyncTask<Integer, Void, List<XServiceManData.XServiceman>> {

        @Override
        protected List<XServiceManData.XServiceman> doInBackground(Integer... integers) {
            xServiceManList = XServiceManDataHelper.getAllXServiceMans(getContext());
            return xServiceManList;
        }

        @Override
        protected void onPostExecute(List<XServiceManData.XServiceman> exServiceManList) {
            super.onPostExecute(exServiceManList);
            if (exServiceManList != null && exServiceManList.size() > 0) {
                xServiceManListAdapter.refresh(exServiceManList);
            }
        }
    }

    private void getXServiceMansFromServer(){
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            if(PreferenceUtils.getStringValue(AppConstents.USER_TYPE).equalsIgnoreCase(AppConstents.ADMIN)){
                builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.GET_XSERVICEMAN+"userType=ServiceMan");
            }
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ((DashBoardActivity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((DashBoardActivity)getContext()).showToast(getString( R.string.error_message));
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    ((DashBoardActivity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                XServiceManData xServiceManData = new Gson().fromJson(body,XServiceManData.class);
                                if(null!=xServiceManData&&null!=xServiceManData.getExServiceMen()
                                        &&!xServiceManData.getExServiceMen().isEmpty()){
                                        xServiceManListAdapter.refresh(xServiceManData.getExServiceMen());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
