package com.example.marvelbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.marvelbasket.bean.Category;
import com.example.marvelbasket.bean.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    private EditText regpassword, regconfirmpssword, regname, regemail, regcontact;
    private Integer flag = 0, flag2 = 0;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Marvel Basket");
        setContentView(R.layout.activity_register);

        //Getting attributes from XMl and passing to afterlogin activity
        regpassword = findViewById(R.id.regpassword);
        regconfirmpssword = findViewById(R.id.regconfirmpassword);
        regname = findViewById(R.id.regname);
        regemail = findViewById(R.id.regemail);
        regcontact = findViewById(R.id.regcontact);
        btnNext = findViewById(R.id.next);

        //Password and confirm password eye icon
        final ImageButton showHideBtn = findViewById(R.id.showHideBtn1);
        showHideBtn.setBackgroundResource(R.drawable.ic_eye);
        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regpassword.getText().toString().equals("")) {
                } else {
                    if (flag == 0) {
                        //Show Password
                        regpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        showHideBtn.setBackgroundResource(0);
                        showHideBtn.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
                        flag = 1;
                    } else {
                        //Hide Password
                        regpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showHideBtn.setBackgroundResource(0);
                        showHideBtn.setBackgroundResource(R.drawable.ic_eye);
                        flag = 0;
                    }
                }
            }
        });

        //Confirm password eye
        final ImageButton showHideBtn2 = findViewById(R.id.showHideBtn2);
        showHideBtn2.setBackgroundResource(R.drawable.ic_eye);
        showHideBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regconfirmpssword.getText().toString().equals("")) {
                } else {
                    if (flag2 == 0) {
                        //Show Password
                        regconfirmpssword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        showHideBtn2.setBackgroundResource(0);
                        showHideBtn2.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
                        flag2 = 1;
                    } else {
                        //Hide Password
                        regconfirmpssword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showHideBtn2.setBackgroundResource(0);
                        showHideBtn2.setBackgroundResource(R.drawable.ic_eye);
                        flag2 = 0;
                    }
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Field Validation
                if (regname.getText().toString().equals("")) {
                    regname.setError("Full Name is required !");
                } else if (regemail.getText().toString().equals("")) {
                    regemail.setError("Email is required !");
                }else if (!regemail.getText().toString().contains("@") | !regemail.getText().toString().contains(".com")) {
                    regemail.setError("Invalid email !");
                } else if (regpassword.getText().toString().equals("")) {
                    regpassword.setError("Password is required !");
                } else if (regconfirmpssword.getText().toString().equals("")) {
                    regconfirmpssword.setError("Password confirmation required !");
                } else if (regcontact.getText().toString().equals("")) {
                    regcontact.setError("Contact is required !");
                } else {
                    if (!regconfirmpssword.getText().toString().equals(regpassword.getText().toString())) {
                        regconfirmpssword.setError("It must match password !");
                    } else {
                        //passing seller data
                        Seller seller = new Seller();

                        seller.setSellerName(regname.getText().toString());
                        seller.setSellerEmail(regemail.getText().toString());
                        seller.setSellerContact(regcontact.getText().toString());
                        seller.setSellerPassword(regpassword.getText().toString());

                        Intent intent = new Intent(Register.this, ShopRegister.class);
                        intent.putExtra("sellerPersonal", seller);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
