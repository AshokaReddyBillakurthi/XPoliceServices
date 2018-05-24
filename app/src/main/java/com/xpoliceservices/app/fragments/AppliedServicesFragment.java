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
import com.xpoliceservices.app.BaseActivity;
import com.xpoliceservices.app.DashBoardActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.ApplicationsListAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.ApplicationDataHelper;
import com.xpoliceservices.app.model.ApplicationData;
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

public class AppliedServicesFragment extends BaseFragment{

    private RecyclerView rvApplications;
    private ApplicationsListAdapter applicationsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_services,container,
                false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvApplications = view.findViewById(R.id.rvApplications);
        rvApplications.setLayoutManager(new LinearLayoutManager(getContext()));

        applicationsListAdapter = new ApplicationsListAdapter();
        rvApplications.setAdapter(applicationsListAdapter);

        ((DashBoardActivity)getContext()).tvScreenTitle.setText("Applied Services");

//        new GetApplicationsAsyncTask().execute();
        getApplicationsFromServer(PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
    }

    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<ApplicationData.Application>> {

        @Override
        protected List<ApplicationData.Application> doInBackground(String... strings) {
            if(((BaseActivity)getContext()).userType.equalsIgnoreCase(AppConstents.USER_TYPE_ADMIN)){
                return ApplicationDataHelper.getAllApplications(getContext());
            }
            else if(((BaseActivity)getContext()).userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)){
                return ApplicationDataHelper.getAllApplications(getContext());
            }
            else{
                return ApplicationDataHelper.getAllApplicationsByEmailId(getContext(),
                        PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
            }
        }

        @Override
        protected void onPostExecute(List<ApplicationData.Application> applications) {
            super.onPostExecute(applications);
            if(applications!=null&&!applications.isEmpty()){
                applicationsListAdapter.refresh(applications);
                rvApplications.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getApplicationsFromServer(String email) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            if(((BaseActivity)getContext()).userType.equalsIgnoreCase(AppConstents.CUSTOMER)){
                builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.GET_APPLICATIONS+"email="+email);
            }
            else if(((BaseActivity)getContext()).userType.equalsIgnoreCase(AppConstents.ADMIN)){
                builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.GET_APPLICATIONS);
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
                                ApplicationData applicationData = new Gson().fromJson(body,ApplicationData.class);
                                if(null!=applicationData){
                                    if(null!=applicationData.getApplicationDatas()
                                            &&!applicationData.getApplicationDatas().isEmpty()){
                                        applicationsListAdapter.refresh(applicationData.getApplicationDatas());
                                    }
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
