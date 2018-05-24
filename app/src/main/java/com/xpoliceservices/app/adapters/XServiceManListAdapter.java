package com.xpoliceservices.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpoliceservices.app.BaseActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.XServiceManProfileActivity;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.model.XServiceManData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XServiceManListAdapter extends RecyclerView.Adapter<XServiceManListAdapter.ExServiceManViewHolder> {

    private List<XServiceManData.XServiceman> listExServiceMan;
    private Context mContext;

    public XServiceManListAdapter(Context mContext) {
        this.listExServiceMan = new ArrayList<>();
        this.mContext = mContext;
    }

    @Override
    public ExServiceManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xserviceman_list_item_cell, parent, false);
        return new ExServiceManViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExServiceManViewHolder holder, int position) {
        holder.tvFullName.setText(listExServiceMan.get(position).firstName + " " + listExServiceMan.get(position).lastName + "");
        holder.tvDivisionPoliceStation.setText(listExServiceMan.get(position).divisionPoliceStation + "");

        if (listExServiceMan.get(position).status == 0) {
            holder.tvStatus.setText("Pending");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else if (listExServiceMan.get(position).status == 1) {
            holder.tvStatus.setText("Approved");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (listExServiceMan.get(position).status == -1) {
            holder.tvStatus.setText("Rejected");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        getImageOfServiceMan(listExServiceMan.get(position).image, holder.ivServiceManImg);
    }

    public void refresh(List<XServiceManData.XServiceman> listExServiceMan) {
        this.listExServiceMan = listExServiceMan;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listExServiceMan.size();
    }

    class ExServiceManViewHolder extends RecyclerView.ViewHolder {

        TextView tvFullName, tvDivisionPoliceStation, tvStatus;
        ImageView ivServiceManImg;

        public ExServiceManViewHolder(final View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvDivisionPoliceStation = itemView.findViewById(R.id.tvDivisionPoliceStation);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, XServiceManProfileActivity.class);
                    intent.putExtra(AppConstents.EXTRA_USER, listExServiceMan.get(getAdapterPosition()));
                    intent.putExtra(AppConstents.EXTRA_USER_TYPE, ((BaseActivity) (itemView.getContext())).userType);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void getImageOfServiceMan(String serviceManImg, ImageView ivServiceManImg) {
        try {
            File mediaStorageDir = new File(
                    "/data/data/"
                            + mContext.getPackageName()
                            + "/Files");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return;
                }
            }

            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + serviceManImg);
            if (mediaFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
                ivServiceManImg.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}