package com.xpoliceservices.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpoliceservices.app.DashBoardActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.XServiceManListAdapter;
import com.xpoliceservices.app.database.XServiceManDataHelper;
import com.xpoliceservices.app.model.XServiceMan;

import java.util.List;

public class XServiceMansListFragment extends BaseFragment {

    private RecyclerView rvXServiceManList;
    private XServiceManListAdapter xServiceManListAdapter;
    private List<XServiceMan> xServiceManList;

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
        new GetExserviceManListTask().execute();
    }

    private class GetExserviceManListTask extends AsyncTask<Integer, Void, List<XServiceMan>> {

        @Override
        protected List<XServiceMan> doInBackground(Integer... integers) {
            xServiceManList = XServiceManDataHelper.getAllXServiceMans(getContext());
            return xServiceManList;
        }

        @Override
        protected void onPostExecute(List<XServiceMan> exServiceManList) {
            super.onPostExecute(exServiceManList);
            if (exServiceManList != null && exServiceManList.size() > 0) {
                xServiceManListAdapter.refresh(exServiceManList);
            }
        }
    }
}
