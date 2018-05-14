package com.xpoliceservices.app;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.xpoliceservices.app.adapters.MenuAdapter;
import com.xpoliceservices.app.constents.AppConstents;
import com.xpoliceservices.app.fragments.AppliedServicesFragment;
import com.xpoliceservices.app.fragments.HomeFragment;
import com.xpoliceservices.app.fragments.ServicesFragment;
import com.xpoliceservices.app.fragments.XServiceMansListFragment;
import com.xpoliceservices.app.utils.DataUtils;
import com.xpoliceservices.app.utils.DialogUtils;

import java.util.List;

public class DashBoardActivity extends BaseActivity {

    private DrawerLayout drawer;
    private LinearLayout llMenu;
    private RecyclerView rvMenuList;
    private MenuAdapter menuAdapter;
    private List<String> menuList;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


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
        bottomNavigationView = findViewById(R.id.navigation);
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

        switch (userType){
            case AppConstents.USER_TYPE_ADMIN:
                bottomNavigationView.inflateMenu(R.menu.admin_bottom_navigation);
                break;
            case AppConstents.USER_TYPE_SERVICEMAN:
                bottomNavigationView.inflateMenu(R.menu.xserviceman_bottom_navigation);
                break;
            case AppConstents.USER_TYPE_CUSTOMER:
                bottomNavigationView.inflateMenu(R.menu.user_bottom_navigation);
                break;
        }

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_applied_services:
                        fragment = new AppliedServicesFragment();
                        break;
                    case R.id.navigation_services:
                        fragment = new ServicesFragment();
                        break;
                    case R.id.navigation_xserviceman:
                        fragment = new XServiceMansListFragment();
                        break;
                    case R.id.navigation_filter:
                        fragment = new ServicesFragment();
                        break;
                    default:
                        break;
                }
                return loadFragment(fragment);
            }
        });

        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    @Override
    public void initData() {

    }

    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_up_out)
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    public void closeDrawer(){
        drawer.closeDrawer(Gravity.LEFT);
    }

    public void showLogoutPopup(){
        DialogUtils.showDialog(DashBoardActivity.this,"Do you want to Logout?",
                AppConstents.LOGOUT,true);
    }
}
