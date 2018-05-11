package com.xpoliceservices.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpoliceservices.app.DashBoardActivity;
import com.xpoliceservices.app.R;
import com.xpoliceservices.app.constents.AppConstents;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private List<String> menuList;

    public MenuAdapter(List<String> menuList) {
        this.menuList = menuList;
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.tvItemName.setText(menuList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        LinearLayout llChildItems;

        public MenuViewHolder(final View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            llChildItems = itemView.findViewById(R.id.llChildItems);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = tvItemName.getText().toString();
                    switch (title){
                        case AppConstents.CREATE_SERVICE:{
                            break;
                        }
                        case AppConstents.SERVICES:{
                            break;
                        }
                        case AppConstents.MY_PROFILE:{
                            break;
                        }
                        case AppConstents.MY_SERVICES:{
                            break;
                        }
                        case AppConstents.SERVICE_MAN_LIST:{
                            break;
                        }
                        case AppConstents.CUSTOMER_LIST:{
                            break;
                        }
                        case AppConstents.APPLICATION_LIST:{
                            break;
                        }
                        case AppConstents.LOGOUT:{
                            ((DashBoardActivity)itemView.getContext()).closeDrawer();
                            ((DashBoardActivity) itemView.getContext()).showLogoutPopup();
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            });
        }
    }
}
