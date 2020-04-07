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

    private List<Category> sellerCategories = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Integer> sellerCategoryIdList = new ArrayList<>();

    private boolean[] checkedCategories;
    private Integer[] categoryIdsArray;
    private String[] categoryNamesArray;

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

        apiCallForCategories();

        btnCategorySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize the arrays
                checkedCategories = new boolean[categories.size()];
                categoryIdsArray = new Integer[categories.size()];
                categoryNamesArray = new String[categories.size()];

                // Initialize the array values
                for (int i = 0; i < categories.size(); i++) {
                    categoryIdsArray[i] = categories.get(i).getcategoryId();
                    categoryNamesArray[i] = categories.get(i).getcategoryName();
                    checkedCategories[i] = false;
                }

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopRegister.this, R.style.MyDialogTheme);
                builder.setCancelable(false);
                builder.setTitle("Select Category For Shop");

                builder.setMultiChoiceItems(categoryNamesArray, checkedCategories, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean checked) {
                        // Update the current focused item's checked status
                        checkedCategories[position] = checked;
                    }
                });

                // Set the positive button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        // Do something when click positive button
                        for (int i = 0; i < checkedCategories.length; i++) {
                            boolean checked = checkedCategories[i];
                            if (checked) {
                                sellerCategoryIdList.add(categoryIdsArray[i]);
                                Category category = new Category();
                                category.setcategoryName(categoryNamesArray[i]);
                                category.setcategoryId(categoryIdsArray[i]);
                                sellerCategories.add(category);
                            }
                        }
                    }
                });

                // Set the neutral button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopName.getText().toString().equals("")) {
                    shopName.setError("Shop name is required !");
                } else if (sellerCategories.isEmpty()) {
                    Toast.makeText(view.getContext(), "Select atleast one shop category", Toast.LENGTH_SHORT).show();
                    /*btnCategorySelect.setError("Select atleast one type !");*/
                } else if (shopAddress.getText().toString().equals("")) {
                    shopAddress.setError("Address is required !");
                } else {
                    //setting sellerShop to previous sellerPersonal
                    Intent registerIntent = getIntent();
                    Seller seller = (Seller) registerIntent.getSerializableExtra("sellerPersonal");


                    Log.i("Shop Name : ", shopName.getText().toString());
                    Log.i("Shop Address : ", shopName.getText().toString());
                    Log.i("Seller Name : ", seller.getSellerName());
                    seller.setShopName(shopName.getText().toString());
                    seller.setShopAddress(shopAddress.getText().toString());
                    Log.i("Shop Address A : ", seller.getShopAddress());

                    SellerCategory sellerCategory = new SellerCategory();
                    sellerCategory.setSeller(seller);
                    sellerCategory.setCategory(sellerCategories);

                    Intent intent = new Intent(ShopRegister.this, BankRegister.class);
                    intent.putExtra("sellerCategory",sellerCategory);
                    /*for(int i=0;i<sellerCategories.size();i++) {
                        Category c = sellerCategories.get(i);
                        intent.putExtra("category"+i,c);
                    }*/

                    startActivity(intent);
                }
            }
        });
    }

    public void apiCallForCategories() {
        //step-1 for Getting Shop Categories from Admin(Server)
        String URL = "http://192.168.43.248:9191/category/find";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //step-2
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Category category = new Category();
                                category.setcategoryId(jsonObject.getInt("categoryId"));
                                category.setcategoryName(jsonObject.getString("categoryName"));
                                categories.add(category);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Error", error.toString());
                    }
                }
        );
        //step-3
        requestQueue.add(objectRequest);
    }
}
