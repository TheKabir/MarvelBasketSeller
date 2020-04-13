package com.example.marvelbasket;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.MenuItem;

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
import com.example.marvelbasket.ui.orderManagement.Pendingorders;
import com.example.marvelbasket.ui.payandreturn.Payments;
import com.example.marvelbasket.ui.payandreturn.Support;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class Afterlogin extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{

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

        //View v=navigationView.getHeaderView(0);
        //tvName =(TextView) v.findViewById(R.id.username);


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
                setTitle("Current Orders");
                fragment = new Pendingorders();
                break;
            case R.id.nav_fullfilledorders:
                setTitle("Finished Orders");
                fragment = new Fullfilledorders();
                break;

            case R.id.nav_payments:
                setTitle("Check Payments");
                fragment = new Payments();
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

    public void onBackPressed()
    {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(Afterlogin.this);
            builder.setMessage("DO YOU WANT TO QUIT ?");
            builder.setTitle("Alert !");
            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            });
            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // If user click no
                                    // then dialog box is canceled.
                                    dialog.cancel();
                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        }
        else {
            super.onBackPressed();
        }


    }

}
