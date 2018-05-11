package com.xpoliceservices.app.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpoliceservices.app.R;
import com.xpoliceservices.app.adapters.DistrictAdapter;
import com.xpoliceservices.app.adapters.DivisionPoliceStationAdapter;
import com.xpoliceservices.app.adapters.StateAdapter;
import com.xpoliceservices.app.adapters.SubDivisionAdapter;
import com.xpoliceservices.app.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog {

    private EditText edtSearch;
    private ImageView ivCross;
    private Context mContext;
    private LinearLayout llSearch;
    private LinearLayout llBtns;
    private TextView tvTitle;
    private Button btnOk;
    private String title;

    private List<DataModel.State> stateList;
    private List<DataModel.District> districtList;
    private List<DataModel.SubDivision> subDivisionList;
    private List<DataModel.Division> divisionPoliceStationsList;
    private OnStateSelected onStateSelected;
    private OnDistrictSelected onDistrictSelected;
    private OnSubDivisionSelected onSubDivisionSelected;
    private OnDivisionPoliceStation onDivisionPoliceStation;
    private boolean isSearchReq;
    private boolean isCheckboxNeed = false;
    private boolean isState = false;
    private boolean isDistrict = false;
    private boolean isSubDivision = false;
    private boolean isDivisionPoliceStation = false;

    private DistrictAdapter districtAdapter;
    private StateAdapter stateAdapter;
    private SubDivisionAdapter subDivisionAdapter;
    private DivisionPoliceStationAdapter divisionPoliceStationAdapter;


    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, boolean isState, List<DataModel.State> stateList, String title,
                        boolean isSearchReq, boolean isCheckboxNeed, OnStateSelected onStateSelected) {
        super(context);
        this.mContext = context;
        this.stateList = stateList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onStateSelected = onStateSelected;
        this.isState = isState;
    }

    public CustomDialog(@NonNull Context context, List<DataModel.District> districtList, String title,
                        boolean isSearchReq, boolean isCheckboxNeed,
                        boolean isDistrict, OnDistrictSelected onDistrictSelected) {
        super(context);
        this.mContext = context;
        this.districtList = districtList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onDistrictSelected = onDistrictSelected;
        this.isDistrict = isDistrict;
    }

    public CustomDialog(@NonNull Context context, List<DataModel.SubDivision> subDivisionList,
                        boolean isSubDivision, String title, boolean isSearchReq, boolean isCheckboxNeed,
                        OnSubDivisionSelected onSubDivisionSelected) {
        super(context);
        this.mContext = context;
        this.subDivisionList = subDivisionList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onSubDivisionSelected = onSubDivisionSelected;
        this.isSubDivision = isSubDivision;
    }

    public CustomDialog(@NonNull Context context,
                        List<DataModel.Division> divisionPoliceStationsList,
                        String title, boolean isSearchReq, boolean isCheckboxNeed,
                        boolean isDivisionPoliceStation, OnDivisionPoliceStation onDivisionPoliceStation) {
        super(context);
        this.mContext = context;
        this.divisionPoliceStationsList = divisionPoliceStationsList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onDivisionPoliceStation = onDivisionPoliceStation;
        this.isDivisionPoliceStation = isDivisionPoliceStation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog);
        edtSearch = findViewById(R.id.edtSearch);
        ivCross = findViewById(R.id.ivCross);
        llSearch = findViewById(R.id.llSearch);
        llBtns = findViewById(R.id.llBtns);
        btnOk = findViewById(R.id.btnOk);
        tvTitle = findViewById(R.id.tvTitle);
        CustomRecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setHasFixedSize(true);

        if (isState) {
            stateAdapter = new StateAdapter(stateList, onStateSelected);
            rvList.setAdapter(stateAdapter);
        } else if (isDistrict) {
            districtAdapter = new DistrictAdapter(districtList, onDistrictSelected);
            rvList.setAdapter(districtAdapter);
        } else if (isSubDivision) {
            subDivisionAdapter = new SubDivisionAdapter(subDivisionList, onSubDivisionSelected);
            rvList.setAdapter(subDivisionAdapter);
        } else if (isDivisionPoliceStation) {
            divisionPoliceStationAdapter =
                    new DivisionPoliceStationAdapter(divisionPoliceStationsList,isCheckboxNeed, onDivisionPoliceStation);
            rvList.setAdapter(divisionPoliceStationAdapter);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                List<DataModel.Division>
                        divisionPoliceStationList = divisionPoliceStationAdapter.getSelectedDivisionPoliceStations();
                for (DataModel.Division divisionPoliceStation : divisionPoliceStationList) {
                    stringBuilder.append(divisionPoliceStation.getDivisionName()).append(",");
                }
                int index = -1;
                String divisionPoliceStationStr = "";
                index = stringBuilder.toString().lastIndexOf(",");
                if (index >= 0) {
                    divisionPoliceStationStr = stringBuilder.toString().substring(0, index);
                    onDivisionPoliceStation.onDivisionPoliceStation(divisionPoliceStationStr);
                }
            }
        });

        edtSearch.setHint("Search (Min. 3 Letters)");

        tvTitle.setText(title);

        if (isCheckboxNeed)
            llBtns.setVisibility(View.VISIBLE);
        else
            llBtns.setVisibility(View.GONE);

        if (isSearchReq)
            llSearch.setVisibility(View.VISIBLE);
        else
            llSearch.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*some text*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != s && s.length() >= 3) {
                    searchText(s.toString());
                } else if (s == null || s.length() == 0) {
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search (Min. 3 Letters)");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });

    }

    private void searchText(String searchText) {
        try {
            if (isState) {
                List<DataModel.State> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (DataModel.State state : stateList) {
                        if ((state.getStateName().toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(state);
                        }
                    }
                    stateAdapter.refresh(tempList);
                } else {
                    stateAdapter.refresh(stateList);
                }
            } else if (isDistrict) {
                List<DataModel.District> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (DataModel.District district : districtList) {
                        if ((district.getDistrictName().toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(district);
                        }
                    }
                    districtAdapter.refresh(tempList);
                } else {
                    districtAdapter.refresh(districtList);
                }
            } else if (isSubDivision) {
                List<DataModel.SubDivision> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (DataModel.SubDivision subDivision : subDivisionList) {
                        if ((subDivision.getSubDivisionName().toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(subDivision);
                        }
                    }
                    subDivisionAdapter.refresh(tempList);
                } else {
                    subDivisionAdapter.refresh(subDivisionList);
                }
            } else if (isDivisionPoliceStation) {
                List<DataModel.Division> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (DataModel.Division divisionPoliceStation : divisionPoliceStationsList) {
                        if ((divisionPoliceStation.getDivisionName().toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(divisionPoliceStation);
                        }
                    }
                    divisionPoliceStationAdapter.refresh(tempList);
                } else {
                    divisionPoliceStationAdapter.refresh(divisionPoliceStationsList);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public interface OnStateSelected {
        void onStateSelected(DataModel.State state);
    }

    public interface OnDistrictSelected {
        void onDistrictSelected(DataModel.District district);
    }

    public interface OnSubDivisionSelected {
        void OnSubDivisionSelected(DataModel.SubDivision subDivision);
    }

    public interface OnDivisionPoliceStation {
        void onDivisionPoliceStation(String divisionPoliceStations);
    }
}
