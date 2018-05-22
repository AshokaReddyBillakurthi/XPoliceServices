package com.xpoliceservices.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xpoliceservices.app.R;

import java.util.List;

public class ServiceInstructionsAdapter extends
        RecyclerView.Adapter<ServiceInstructionsAdapter.ServiceIntructionViewHolder>{
    private List<String> serviceInstuctionsList;

    public ServiceInstructionsAdapter(List<String> serviceInstuctionsList){
        this.serviceInstuctionsList = serviceInstuctionsList;
    }

    @Override
    public ServiceIntructionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_instruction_item_cell,
                parent,false);
        return new ServiceIntructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceIntructionViewHolder holder, int position) {
        holder.tvServiceInstruction.setText(serviceInstuctionsList.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return serviceInstuctionsList.size();
    }

    class ServiceIntructionViewHolder extends RecyclerView.ViewHolder{
        TextView tvServiceInstruction;
        public ServiceIntructionViewHolder(View itemView) {
            super(itemView);
            tvServiceInstruction = itemView.findViewById(R.id.tvServiceInstruction);
        }
    }
}
