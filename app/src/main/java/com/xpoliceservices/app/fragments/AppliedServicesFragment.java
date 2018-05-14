package com.xpoliceservices.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.ApplicationsListAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.database.ApplicationDataHelper;
import com.xpoliceservices.app.model.Application;
import com.xpoliceservices.app.utils.PreferenceUtils;

import java.util.List;

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

        new GetApplicationsAsyncTask().execute();
    }

    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<Application>> {

        @Override
        protected List<Application> doInBackground(String... strings) {
            return ApplicationDataHelper.getAllApplicationsByEmailId(getContext(),
                    PreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
        }

        @Override
        protected void onPostExecute(List<Application> applications) {
            super.onPostExecute(applications);
            if(applications!=null&&!applications.isEmpty()){
                applicationsListAdapter.refresh(applications);
                rvApplications.setVisibility(View.VISIBLE);
            }
        }
    }

}
