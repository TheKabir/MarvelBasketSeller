package com.example.marvelbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class forgotpassword extends AppCompatActivity {
    Button reqpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        reqpass = findViewById(R.id.reqnewpass);
        reqpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Reset Password Link Sent Successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
