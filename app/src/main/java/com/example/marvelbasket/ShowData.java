package com.example.marvelbasket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvelbasket.ui.payandreturn.Returns;

public class ShowData extends Fragment {
private EditText editProductStock,editProductPrice;
private EditText t[];
private Button stopSales,restartSales;
private TextView editProductName,editProductBrand;
private ImageButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_showdata, container, false);

        //defining attributes

        button = v.findViewById(R.id.edititemdata);
        editProductName = v.findViewById(R.id.editProductName);
        editProductBrand = v.findViewById(R.id.editProductBrand);
        editProductStock = v.findViewById(R.id.editProductStock);
        editProductPrice = v.findViewById(R.id.editProductPrice);
        restartSales = v.findViewById(R.id.restartSales);
        stopSales = v.findViewById(R.id.stopSales);

        restartSales.setVisibility(View.GONE);

        // STOP SALES

        stopSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSales.setVisibility(View.GONE);
                restartSales.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Product Sales Temorarily Stopped",Toast.LENGTH_LONG).show();
            }
        });

        // RESTART SALES

        restartSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSales.setVisibility(View.VISIBLE);
                restartSales.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Product Sales Restarted ",Toast.LENGTH_LONG).show();
            }
        });

        t= new EditText[]{editProductStock,editProductPrice};

        //edit product button
        button.setBackgroundResource(R.drawable.edit_pencil);
        final int[] checkedit = {0};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stopSales.getVisibility()==View.GONE | restartSales.getVisibility()==View.VISIBLE)
                {
                    Toast.makeText(getContext(),"Required : Restart Product Sales!",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    if (checkedit[0] == 0) {
                        for (int i = 0; i < t.length; i++) {
                            t[i].setEnabled(true);
                        }
                        Toast.makeText(getContext(), "Enter New Details", Toast.LENGTH_SHORT).show();
                        button.setBackgroundResource(0);
                        button.setBackgroundResource(R.drawable.ic_check_black_24dp);
                        checkedit[0] = 1;
                    }
                    else {
                        button.setBackgroundResource(0);
                        button.setBackgroundResource(R.drawable.edit_pencil);
                        for (int i = 0; i < t.length; i++) {
                            t[i].setText(t[i].getText().toString());
                            t[i].setEnabled(false);
                        }
                        Toast.makeText(getContext(), "Changes Succesfully Saved", Toast.LENGTH_SHORT).show();
                        checkedit[0] = 0;
                    }
                }
            }
        });

       return  v;
    }
}
