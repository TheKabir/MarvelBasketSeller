package com.example.marvelbasket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelbasket.bean.ProductCount;
import com.example.marvelbasket.bean.Seller;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.marvelbasket.ui.Addproduct;
import com.example.marvelbasket.ui.editp.Editproduct;
import com.example.marvelbasket.ui.home.Home;
import com.example.marvelbasket.ui.orderManagement.Fullfilledorders;
import com.example.marvelbasket.ui.orderManagement.Onwayorders;
import com.example.marvelbasket.ui.orderManagement.Pendingorders;
import com.example.marvelbasket.ui.orderManagement.Replaceorders;
import com.example.marvelbasket.ui.payandreturn.Payments;
import com.example.marvelbasket.ui.payandreturn.Returns;
import com.example.marvelbasket.ui.payandreturn.Support;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Afterlogin extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{

    public static final String MY_PREF_NAME = "marvelbasketseller";
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvName;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Provided Code For Construction Of Drawer
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Displaying Entered Data After Login
      // SharedPreferences preferences = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
       // String sellerJSON = preferences.getString("sellerJSON", "");
        //Gson gson = new Gson();
        //Seller seller = gson.fromJson(sellerJSON, Seller.class);

        //View v=navigationView.getHeaderView(0);
        //tvName =(TextView) v.findViewById(R.id.username);
        //tvName.setText(seller.getSellerName());


        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_app_bar_open_drawer_description,R.string.nav_app_bar_open_drawer_description);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Setting Home as default

        setTitle("Dashboard");
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new Home();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();


    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.afterlogin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(MenuItem m){
        //removing data when log out
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE).edit();
        editor.remove("sellerJSON");
        editor.remove("stoppedProductCountJSON");
        editor.remove("approvedProductCountJSON");
        editor.remove("totalProductCountJSON");
        editor.apply();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch (menuItem.getItemId()){

            case R.id.nav_home:
                setTitle("Dashboard");
                fragment = new Home();
                break;
            case R.id.nav_profile:
                setTitle("My Profile");
                fragment = new Profile();
                break;
            case R.id.nav_addproduct:
                setTitle("Add Products");
                fragment = new Addproduct();
                break;
            case R.id.nav_pendingorders:
                setTitle("Approve Orders");
                fragment = new Pendingorders();
                break;
            case R.id.nav_fullfilledorders:
                setTitle("Finished Orders");
                fragment = new Fullfilledorders();
                break;
            case R.id.nav_replacementorders:
                setTitle("Replacement");
                fragment = new Replaceorders();
                break;
            case R.id.nav_onwayorders:
                setTitle("Orders On-Way");
                fragment = new Onwayorders();
                break;
            case R.id.nav_payments:
                setTitle("Check Payments");
                fragment = new Payments();
                break;
            case R.id.nav_returns:
                setTitle("Track Returns");
                fragment = new Returns();
                break;
            case R.id.nav_support:
                setTitle("Support Dashboard");
                fragment = new Support();
                break;

            case R.id.nav_editproduct:
                setTitle("View/Update Products");
                fragment = new Editproduct();
                break;
        }


        if (fragment != null){

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
