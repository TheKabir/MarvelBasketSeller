package com.example.marvelbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelbasket.bean.ProductCount;
import com.example.marvelbasket.bean.Seller;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText password;
    private EditText email;
    Integer flag = 0;
    public static final String mail = "com.example.mainactivity.mail", user = "com.example.mainactivity.user";
    public static final String MY_PREF_NAME = "marvelbasketseller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Marvel Basket");
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //If Return From Bank Register
        Intent intent = getIntent();
        String toastText = intent.getStringExtra("toastText");
        if (toastText != null) {
            Toast.makeText(getApplicationContext(), "Account Requested , Wait for Approval", Toast.LENGTH_LONG).show();
        }


        //hack basket
        Button hbasket = findViewById(R.id.tempbutton);
        hbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Afterlogin.class);
                startActivity(intent);
            }
        });

        //Password Eye Icon Functionality

        final ImageButton showHideBtn = findViewById(R.id.showHideBtn);
        showHideBtn.setBackgroundResource(R.drawable.ic_eye);
        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("")) {
                } else {
                    if (flag == 0) {
                        //Show Password
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        showHideBtn.setBackgroundResource(0);
                        showHideBtn.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
                        flag = 1;
                    } else {
                        //Hide Password
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showHideBtn.setBackgroundResource(0);
                        showHideBtn.setBackgroundResource(R.drawable.ic_eye);
                        flag = 0;
                    }
                }
            }
        });
    }

    public void authenticate(View view) {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        int flag1 = 0, flag2 = 0;
        if (!userEmail.equals("")) {
            flag1 = 1;
        }
        if (!userPassword.equals("")) {
            flag2 = 1;
        }
        if (flag1 == 1 && flag2 == 1) {
            if (!userEmail.contains("@") | !userEmail.contains(".com")) {
                email.setError("Enter valid email");
            } else {
                apiCallForAuthenticateSeller(view);
            }
        } else if (flag1 == 0 && flag2 == 1) {
            email.setError("Email is required!");
        } else if (flag1 == 1 && flag2 == 0) {
            password.setError("Password is required !");
        } else {
            email.setError("Email is required!");
            password.setError("Password is required !");
        }
    }

    public void apiCallForAuthenticateSeller(View view) {

        //step-1
        String URL = "http://192.168.43.248:9191/seller/authenticate";
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //step-2
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Seller seller = new Seller();
                        try {
                            seller.setSellerId(response.getInt("sellerId"));
                            seller.setSellerName(response.getString("sellerName"));
                            seller.setSellerEmail(response.getString("sellerEmail"));
                            seller.setSellerContact(response.getString("sellerContact"));
                            seller.setSellerPassword(response.getString("sellerPassword"));
                            seller.setShopName(response.getString("shopName"));
                            seller.setShopAddress(response.getString("shopAddress"));
                            seller.setSellerStatus(response.getString("sellerStatus"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        apiCallForProductCountCard(seller);
                        apiCallForApprovedProductCount(seller);
                        apiCallForStoppedProductCount(seller);
                        /*apiCallForRequestedProductCount(seller);*/


                        //putting seller in shared preferences
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                        Gson gson = new Gson();

                        String sellerJSON = gson.toJson(seller);
                        editor.putString("sellerJSON", sellerJSON);
                        editor.apply();

                        //sending seller via intent
                        Intent intent = new Intent(MainActivity.this, Afterlogin.class);
                        intent.putExtra("seller", seller);
                        startActivity(intent);
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 401) {
                            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        } else if (statusCode == 403) {
                            Toast.makeText(getApplicationContext(), "Seller Account Stopped", Toast.LENGTH_SHORT).show();
                        } else if (statusCode == 102) {
                            Toast.makeText(getApplicationContext(), "Seller Account Not Approved Yet", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        //step-3
        requestQueue.add(objectRequest);
    }

    public void forgotpass() {
        Intent intent = new Intent();
    }

    public void registerhere(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void apiCallForProductCountCard(Seller seller) {
        String URL = "http://192.168.43.248:9191/productcount/" + seller.getSellerId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ProductCount productCount = new ProductCount();
                            Gson gson = new Gson();

                            productCount.setElectronicTotalCount(response.getInt(0));
                            productCount.setBookTotalCount(response.getInt(1));
                            productCount.setGeneralTotalCount(response.getInt(2));
                            productCount.setFashionTotalCount(response.getInt(3));

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString("totalProductCountJSON",gson.toJson(productCount));
                            editor.apply();
                            Log.e("Total JSON",gson.toJson(productCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    public void apiCallForApprovedProductCount(Seller seller) {

        String URL = "http://192.168.43.248:9191/productcount/approved/" + seller.getSellerId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ProductCount productCount = new ProductCount();
                            Gson gson = new Gson();

                            productCount.setElectronicApprovedCount(response.getInt(0));
                            productCount.setBookApprovedCount(response.getInt(1));
                            productCount.setGeneralApprovedCount(response.getInt(2));
                            productCount.setFashionApprovedCount(response.getInt(3));

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString("approvedProductCountJSON",gson.toJson(productCount));
                            editor.apply();
                            Log.e("Approved JSON",gson.toJson(productCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    public void apiCallForStoppedProductCount(Seller seller) {
        String URL = "http://192.168.43.248:9191/productcount/stopped/" + seller.getSellerId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ProductCount productCount = new ProductCount();
                            Gson gson = new Gson();

                            productCount.setElectronicStoppedCount(response.getInt(0));
                            productCount.setBookStoppedCount(response.getInt(1));
                            productCount.setGeneralStoppedCount(response.getInt(2));
                            productCount.setFashionStoppedCount(response.getInt(3));

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString("stoppedProductCountJSON",gson.toJson(productCount));
                            editor.apply();
                            Log.e("Stopped JSON",gson.toJson(productCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    public void apiCallForRequestedProductCount(Seller seller) {
        String URL = "http://192.168.43.248:9191/productcount/requested/" + seller.getSellerId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ProductCount productCount = new ProductCount();
                            Gson gson = new Gson();

                            productCount.setElectronicRequestedCount(response.getInt(0));
                            productCount.setBookRequestedCount(response.getInt(1));
                            productCount.setGeneralRequestedCount(response.getInt(2));
                            productCount.setFashionRequestedCount(response.getInt(3));

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString("requestedProductCountJSON",gson.toJson(productCount));
                            editor.apply();
                            Log.e("Requested JSON",gson.toJson(productCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }
}