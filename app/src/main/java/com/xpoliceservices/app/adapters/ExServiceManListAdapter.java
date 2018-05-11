package com.xpoliceservices.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpoliceservices.app.R;
import com.xpoliceservices.app.model.XServiceMan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExServiceManListAdapter  extends RecyclerView.Adapter<ExServiceManListAdapter.ExServiceManViewHolder> {

    private List<XServiceMan> listExServiceMan;
    private Context mContext;

    public ExServiceManListAdapter(Context mContext) {
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
        holder.tvDivisionPoliceStation.setText(listExServiceMan.get(position).circlePolicestation + "");

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

//        holder.tvEmail.setText(listExServiceMan.get(position).email + "");
//        holder.tvMobileNumber.setText(listExServiceMan.get(position).mobileNo + "");
//
//        holder.tvCity.setText(listExServiceMan.get(position).subDivision+"");
//        holder.tvState.setText(listExServiceMan.get(position).state+"");
        getImageOfServiceMan(listExServiceMan.get(position).userImg, holder.ivServiceManImg);
    }

    public void refresh(List<XServiceMan> listExServiceMan) {
        this.listExServiceMan = listExServiceMan;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listExServiceMan.size();
    }

    class ExServiceManViewHolder extends RecyclerView.ViewHolder {

        TextView tvFullName, tvDivisionPoliceStation, tvStatus;// tvEmail, tvMobileNumber,tvArea,tvCity,tvState;
        ImageView ivServiceManImg;

        public ExServiceManViewHolder(final View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvDivisionPoliceStation = itemView.findViewById(R.id.tvDivisionPoliceStation);
            tvStatus = itemView.findViewById(R.id.tvStatus);
//            tvEmail = itemView.findViewById(R.id.tvEmail);
//            tvMobileNumber = itemView.findViewById(R.id.tvMobileNumber);
//            ivServiceManImg = itemView.findViewById(R.id.ivServiceManImg);
//            tvArea = itemView.findViewById(R.id.tvArea);
//            tvCity = itemView.findViewById(R.id.tvCity);
//            tvState = itemView.findViewById(R.id.tvState);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, ExServiceManDetailsActivity.class);
//                    intent.putExtra(AppConstents.EXTRA_USER, listExServiceMan.get(getAdapterPosition()));
//                    intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, ((BaseActivity) (itemView.getContext())).loginType);
//                    mContext.startActivity(intent);
//                }
//            });
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