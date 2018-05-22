package com.xpoliceservices.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpoliceservices.app.DashBoardActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.ServicesAdapter;
import com.xpoliceservices.app.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class ServicesFragment extends BaseFragment {

    private RecyclerView rvServices;
    private ServicesAdapter servicesAdapter;
    private List<String> listServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DashBoardActivity)getContext()).tvScreenTitle.setText("Services");

        rvServices = view.findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new GridLayoutManager(getContext(),2));
        listServices = new ArrayList<>();
        listServices = DataUtils.getServices();
        servicesAdapter = new ServicesAdapter(listServices);
        rvServices.setAdapter(servicesAdapter);
    }
}
