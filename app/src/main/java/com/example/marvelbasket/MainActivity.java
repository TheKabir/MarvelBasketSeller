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
                Intent intent = new Intent(MainActivity.this, Afterlogin.class);
                startActivity(intent);
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

    public void forgotpass(View view) {
        Intent intent = new Intent(this,forgotpassword.class);
        startActivity(intent);
    }

    public void registerhere(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}