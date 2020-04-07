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

    private EditText acname, acno, ifsc;
    private Button btnRequestAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Marvel Basket");
        setContentView(R.layout.activity_bankregister);

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
                    Intent intent = new Intent(BankRegister.this, Afterlogin.class);
                    startActivity(intent);
                }
            }
        });
    }

}
