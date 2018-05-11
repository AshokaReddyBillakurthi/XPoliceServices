package com.xpoliceservices.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xpoliceservices.app.R;
import com.xpoliceservices.app.custom.CustomDialog;
import com.xpoliceservices.app.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DivisionPoliceStationAdapter extends RecyclerView.Adapter
        <DivisionPoliceStationAdapter.DivisionPoliceStationViewHolder>  {

    private List<DataModel.Division> divisionPoliceStationList;
    private List<DataModel.Division> tempSelect;
    private CustomDialog.OnDivisionPoliceStation onDivisionPoliceStation;
    private boolean isCheckBxNeed = false;

    public DivisionPoliceStationAdapter(List<DataModel.Division>
                                                divisionPoliceStationList, boolean isCheckBxNeed,
                                        CustomDialog.OnDivisionPoliceStation onDivisionPoliceStation){
        this.divisionPoliceStationList = divisionPoliceStationList;
        this.onDivisionPoliceStation = onDivisionPoliceStation;
        this.isCheckBxNeed = isCheckBxNeed;
        this.tempSelect = new ArrayList<>();
    }

    @Override
    public DivisionPoliceStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,
                false);
        return new DivisionPoliceStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DivisionPoliceStationViewHolder holder, final int position) {

        holder.tvArea.setText(divisionPoliceStationList.get(position).getDivisionName().toString());

        if(divisionPoliceStationList.get(position).isSelected){
            holder.cbxChecked.setChecked(true);
        }
        else{
            holder.cbxChecked.setChecked(false);
        }

        holder.cbxChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbxChecked.isChecked()) {
                    divisionPoliceStationList.get(position).isSelected = true;
                    tempSelect.add(divisionPoliceStationList.get(position));
                }
                else {
                    divisionPoliceStationList.get(position).isSelected = false;
                    tempSelect.remove(divisionPoliceStationList.get(position));
                }
            }
        });
    }

    public void refresh(List<DataModel.Division> divisionPoliceStationList){
        this.divisionPoliceStationList = divisionPoliceStationList;
        notifyDataSetChanged();
    }

    public List<DataModel.Division> getSelectedDivisionPoliceStations(){
        return tempSelect;
    }


    @Override
    public int getItemCount() {
        return divisionPoliceStationList.size();
    }

    class DivisionPoliceStationViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        private CheckBox cbxChecked;
        public DivisionPoliceStationViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            cbxChecked = itemView.findViewById(R.id.cbxChecked);

            if(isCheckBxNeed)
                cbxChecked.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isCheckBxNeed) {
                        onDivisionPoliceStation.onDivisionPoliceStation(divisionPoliceStationList
                                .get(getAdapterPosition()).getDivisionName());
                    }
                }
            });
        }
    }
}

