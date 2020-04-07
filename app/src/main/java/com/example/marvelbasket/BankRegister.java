package com.example.marvelbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelbasket.bean.Category;
import com.example.marvelbasket.bean.Seller;
import com.example.marvelbasket.bean.SellerCategory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankRegister extends AppCompatActivity {

    public static final String MY_PREF_NAME = "marvelbasketseller";
    private SellerCategory sellerCategory;
    private EditText acname, acno, ifsc;
    private Button btnRequestAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Marvel Basket");
        setContentView(R.layout.activity_bankregister);

        Intent intent = getIntent();
        sellerCategory = (SellerCategory) intent.getSerializableExtra("sellerCategory");

        /*Log.i("In ", "Bank Register");
        Log.i("sellerName-->", sellerCategory.getSeller().getSellerName());
        Log.i("sellerPassword-->", sellerCategory.getSeller().getSellerPassword());
        Log.i("sellerEmail-->", sellerCategory.getSeller().getSellerEmail());
        Log.i("sellerContact-->", sellerCategory.getSeller().getSellerContact());
        Log.i("shopName-->", sellerCategory.getSeller().getShopName());
        Log.i("shopAddress-->", sellerCategory.getSeller().getShopAddress());
        for (Category c : sellerCategory.getCategory()) {
            Log.i("Category<-->",c.getcategoryName()+"---"+c.getcategoryId());
        }*/

        btnRequestAccount = findViewById(R.id.reqacc);

        btnRequestAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acname = findViewById(R.id.acno);
                acno = findViewById(R.id.acno);
                ifsc = findViewById(R.id.ifsc);
                //validation
                if (acname.getText().toString().equals("")) {
                    acname.setError("Account holder's name required !");
                } else if (acno.getText().toString().equals("")) {
                    acno.setError("Account number required !");
                } else if (ifsc.getText().toString().equals("")) {
                    ifsc.setError("IFSC is required !");
                } else {
                    apiCallForRegisteringSellerAccount();
                }
            }
        });
    }

    public void apiCallForRegisteringSellerAccount() {
        //step-1
        String URL = "http://192.168.43.248:9191/seller/register";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject sellerJsonObject = new JSONObject();
            sellerJsonObject.put("sellerName", sellerCategory.getSeller().getSellerName());
            sellerJsonObject.put("sellerPassword", sellerCategory.getSeller().getSellerPassword());
            sellerJsonObject.put("sellerEmail", sellerCategory.getSeller().getSellerEmail());
            sellerJsonObject.put("sellerContact", sellerCategory.getSeller().getSellerContact());
            sellerJsonObject.put("shopName", sellerCategory.getSeller().getShopName());
            sellerJsonObject.put("shopAddress", sellerCategory.getSeller().getShopAddress());

            /*Log.i("Seller Json : ",sellerJsonObject.toString(3));*/

            JSONArray categoryJsonArray = new JSONArray();
            for (int i = 0; i < sellerCategory.getCategory().size(); i++) {
                JSONObject categoryJsonObject = new JSONObject();
                categoryJsonObject.put("categoryId", sellerCategory.getCategory().get(i).getcategoryId());
                categoryJsonArray.put(categoryJsonObject);
            }
            /*Log.i("Category Json : ",categoryJsonArray.toString(3));*/

            jsonObject.put("category", categoryJsonArray);
            jsonObject.put("seller", sellerJsonObject);

            /*Log.i("Buited Json : ",jsonObject.toString(3));*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //step-2
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Intent intent = new Intent(BankRegister.this,MainActivity.class);
                        intent.putExtra("toastText","Account Requested , Wait for Approval");
                        startActivity(intent);

                        /*SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                        Gson gson = new Gson();
                        String sellerJSON = gson.toJson(sellerCategory.getSeller());
                        editor.putString("sellerJSON", sellerJSON);
                        editor.apply();

                        Intent intent = new Intent(BankRegister.this, Afterlogin.class);
                        intent.putExtra("seller", sellerCategory.getSeller());
                        startActivity(intent);*/

                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Integer statusCode = error.networkResponse.statusCode;
                        if (statusCode == 409) {
                            Toast.makeText(getApplicationContext(), "Email already taken", Toast.LENGTH_SHORT).show();
                        } else if (statusCode == 406) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        //step-3
        requestQueue.add(objectRequest);
    }
}
