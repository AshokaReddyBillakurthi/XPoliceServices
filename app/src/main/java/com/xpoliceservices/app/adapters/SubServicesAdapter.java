package com.xpoliceservices.app.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xpoliceservices.app.ApplyServiceActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.constents.AppConstents;

import java.util.List;

public class SubServicesAdapter extends RecyclerView.Adapter<SubServicesAdapter.SubServicesViewHolder>{

    private List<String> listSubServices;

    public SubServicesAdapter(List<String> listSubServices){
        this.listSubServices = listSubServices;
    }

    @Override
    public SubServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subservice_list_item_cell,parent,
                false);
        return new SubServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubServicesViewHolder holder, int position) {
        holder.tvSubService.setText(listSubServices.get(position));
    }

    @Override
    public int getItemCount() {
        return listSubServices.size();
    }

    public void refresh(List<String> listSubServices){
        this.listSubServices = listSubServices;
        notifyDataSetChanged();
    }

    class SubServicesViewHolder extends RecyclerView.ViewHolder{
        TextView tvSubService;
        public SubServicesViewHolder(final View itemView) {
            super(itemView);
            tvSubService = itemView.findViewById(R.id.tvSubService);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String permissionName = tvSubService.getText().toString();
                    Intent intent = new Intent(itemView.getContext(), ApplyServiceActivity.class);
                    if(permissionName.equalsIgnoreCase(AppConstents.INTERNET_CAFES)){
                        intent.putExtra(AppConstents.EXTRA_SERVICE_TYPE,AppConstents.INTERNET_CAFES);
                    }
                    else if (permissionName.equalsIgnoreCase(AppConstents.GUN_LICENCES)) {
                        intent.putExtra(AppConstents.EXTRA_SERVICE_TYPE,AppConstents.GUN_LICENCES);
                    }
                    (itemView.getContext()).startActivity(intent);
                }
            });


        }
    }
}
