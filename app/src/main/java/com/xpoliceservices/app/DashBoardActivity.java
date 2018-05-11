package com.xpoliceservices.app;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.xpoliceservices.app.adapters.MenuAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.utils.DataUtils;
import com.xpoliceservices.app.utils.DialogUtils;

import java.util.List;

public class DashBoardActivity extends BaseActivity {

    private DrawerLayout drawer;
    private LinearLayout llMenu;
    private RecyclerView rvMenuList;
    private MenuAdapter menuAdapter;
    private List<String> menuList;


    @Override
    public int getRootLayout() {
        return R.layout.activity_dash_board;
    }

    @Override
    public void initGUI() {

        if(null!= getIntent().getExtras()){
            userType = getIntent().getStringExtra(AppConstents.EXTRA_USER_TYPE);
        }
        drawer = findViewById(R.id.drawer);
        llMenu = findViewById(R.id.llMenu);
        rvMenuList = findViewById(R.id.rvMenuList);
        rvMenuList.setLayoutManager(new LinearLayoutManager(DashBoardActivity.this));
        menuList = DataUtils.getMenuList(userType);
        menuAdapter = new MenuAdapter(menuList);
        rvMenuList.setAdapter(menuAdapter);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                null, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    public void closeDrawer(){
        drawer.closeDrawer(Gravity.LEFT);
    }

    public void showLogoutPopup(){
        DialogUtils.showDialog(DashBoardActivity.this,"Do you want to Logout?",
                AppConstents.LOGOUT,true);
    }
}
