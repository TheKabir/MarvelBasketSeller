package com.example.marvelbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelbasket.bean.Category;
import com.example.marvelbasket.bean.Seller;
import com.example.marvelbasket.bean.SellerCategory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopRegister extends AppCompatActivity {

    private EditText shopName, shopContact, shopAddress;
    private Button btnCategorySelect;
    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Marvel Basket");
        setContentView(R.layout.activity_shop_register);

        //Getting Attributes From XML
        shopName = findViewById(R.id.shopname);
        shopAddress = findViewById(R.id.shopaddress);
        btnCategorySelect = (Button) findViewById(R.id.shoptype);
        btnNext = (Button) findViewById(R.id.nextBank);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopName.getText().toString().equals("")) {
                    shopName.setError("Shop name is required !");
                }
                else if (shopAddress.getText().toString().equals("")) {
                    shopAddress.setError("Address is required !");
                } else {


                    Intent intent = new Intent(ShopRegister.this, BankRegister.class);
                    startActivity(intent);
                }
            }
        });
    }

}
