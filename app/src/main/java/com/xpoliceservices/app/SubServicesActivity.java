package com.xpoliceservices.app;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpoliceservices.app.adapters.SubServicesAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class SubServicesActivity extends BaseActivity {

    private RecyclerView rvSubSevices;
    private SubServicesAdapter subServicesAdapter;
    private TextView tvTitle;
    private List<String> listSubServices;
    private List<String> tempSearchList;
    private String serviceType = "";
    private ImageView ivBack,ivCross;
    private EditText edtSearch;

    @Override
    public int getRootLayout() {
        return R.layout.activity_sub_services;
    }

    @Override
    public void initGUI() {
        if(null!=getIntent().getStringExtra(AppConstents.EXTRA_SEARVICE_TYPE)){
            serviceType = getIntent().getStringExtra(AppConstents.EXTRA_SEARVICE_TYPE);
        }
        rvSubSevices = findViewById(R.id.rvSubSevices);
        rvSubSevices.setLayoutManager(new LinearLayoutManager(SubServicesActivity.this));
        tvTitle = findViewById(R.id.tvTitle);
        ivCross = findViewById(R.id.ivCross);
        ivBack = findViewById(R.id.ivBack);
        edtSearch = findViewById(R.id.edtSearch);

    }

    @Override
    public void initData() {
        listSubServices = DataUtils.getSubServices(serviceType);
        subServicesAdapter = new SubServicesAdapter(listSubServices);
        rvSubSevices.setAdapter(subServicesAdapter);

        tvTitle.setText("Services");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&&s.length()>0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null&&s.length()>=3){
                    searchText(s.toString());
                }
                else if(s.length()==0){
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search here");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });
    }

    private void searchText(String searchText){
        try{
            if(!TextUtils.isEmpty(searchText)){
                tempSearchList = new ArrayList<>();
                for(String permission :listSubServices){
                    if(permission.toLowerCase().contains(searchText.toLowerCase())){
                        tempSearchList.add(permission);
                    }
                }
                if(tempSearchList!=null&&!tempSearchList.isEmpty())
                    subServicesAdapter.refresh(tempSearchList);
            }
            else {
                subServicesAdapter.refresh(listSubServices);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
