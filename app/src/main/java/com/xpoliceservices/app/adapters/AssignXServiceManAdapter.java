package com.xpoliceservices.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xpoliceservices.app.R;
import com.xpoliceservices.app.model.XServiceManData;


import java.util.List;

public class AssignXServiceManAdapter extends RecyclerView.Adapter<AssignXServiceManAdapter.AssignXServiceManViewHolder>{

    private List<XServiceManData.XServiceman> xServiceManList;
    private OnXServiceManSelectListener onXServiceManSelectListener;

    public AssignXServiceManAdapter(List<XServiceManData.XServiceman> xServiceManList,
                                    OnXServiceManSelectListener onXServiceManSelectListener){
        this.xServiceManList = xServiceManList;
        this.onXServiceManSelectListener = onXServiceManSelectListener;
    }

    @Override
    public AssignXServiceManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new AssignXServiceManViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignXServiceManViewHolder holder, int position) {
        holder.tvListName.setText(xServiceManList.get(position).firstName+" "+xServiceManList.get(position).lastName);
    }

    @Override
    public int getItemCount() {
        return xServiceManList.size();
    }

    class AssignXServiceManViewHolder extends RecyclerView.ViewHolder{

        TextView tvListName;
        public AssignXServiceManViewHolder(View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onXServiceManSelectListener.onXServiceManSelect(xServiceManList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnXServiceManSelectListener{
        void onXServiceManSelect(XServiceManData.XServiceman xServiceMan);
    }
}
