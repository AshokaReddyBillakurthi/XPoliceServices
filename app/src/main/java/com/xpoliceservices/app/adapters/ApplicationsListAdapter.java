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

import com.xpoliceservices.app.ApplicationDetailsActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.model.ApplicationData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsListAdapter extends RecyclerView.Adapter<ApplicationsListAdapter.ApplicationViewHolder> {

    private List<ApplicationData.Application> applicationList;
    private Context mContext;

    public ApplicationsListAdapter() {
        this.applicationList = new ArrayList<>();
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list_item_cell, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        holder.tvFullName.setText(applicationList.get(position).getFirstName() + " " + applicationList.get(position).getLastName());
        holder.tvApplicationName.setText(applicationList.get(position).getApplicationType() + "");
        holder.tvDivisionPoliceStation.setText(applicationList.get(position).getCirclePolicestation() + "");
        holder.tvDate.setText(applicationList.get(position).getData()+"");

        if (applicationList.get(position).getStatus() == 0) {
            holder.tvStatus.setText(AppConstents.PENDING);
//            holder.tvStatus.setTextColor(mContext.getColor(R.color.error_strip));
        } else if (applicationList.get(position).getStatus() == 1) {
            holder.tvStatus.setText(AppConstents.INPROGRESS);
//            holder.tvStatus.setTextColor(mContext.getColor(R.color.orange));
        } else if (applicationList.get(position).getStatus() == 2) {
            holder.tvStatus.setText(AppConstents.COMPLETED);
//            holder.tvStatus.setTextColor(mContext.getColor(R.color.button_green));
        }

//        holder.ivMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ChatActivity.class);
//                intent.putExtra(AppConstents.EXTRA_EMAIL_ID,"ashok.billakurthi@gmail.com");
//                mContext.startActivity(intent);
//            }
//        });

//        getImageOfServiceMan(applicationList.get(position).userImg,holder.ivApplicantImg);
    }

    public void refresh(List<ApplicationData.Application> applicationList) {
        this.applicationList = applicationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder {

        // ImageView ivApplicantImg,ivMessage;
        TextView tvFullName, tvApplicationName,
                tvStatus, tvDivisionPoliceStation,tvDate;//tvEmail,tvMobileNo,tvApplicationType,
        // tvArea;

        public ApplicationViewHolder(View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvApplicationName = itemView.findViewById(R.id.tvApplicationName);
            tvDivisionPoliceStation = itemView.findViewById(R.id.tvDivisionPoliceStation);
            tvDate = itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApplicationDetailsActivity.class);
                    intent.putExtra(AppConstents.EXTRA_PERMISSION_APPLICATION,
                            applicationList.get(getAdapterPosition()));
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
