package com.example.marvelbasket;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvelbasket.bean.Seller;
import com.google.gson.Gson;

public class Profile extends Fragment {

    private EditText t[];
    private TextView textView;
    private ImageButton btn;
    private ProfileViewModel mViewModel;
    public static final String MY_PREF_NAME = "marvelbasketseller";

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        String sellerJSON = preferences.getString("sellerJSON", "");
        Gson gson = new Gson();
        Seller seller = gson.fromJson(sellerJSON, Seller.class);

        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        t = new EditText[]{v.findViewById(R.id.name), v.findViewById(R.id.mobile), v.findViewById(R.id.email), v.findViewById(R.id.shopAddress),
                v.findViewById(R.id.shopName)};

        t[0].setText(seller.getSellerName());
        t[1].setText(seller.getSellerContact());
        t[2].setText(seller.getSellerEmail());
        t[3].setText(seller.getShopAddress());
        t[4].setText(seller.getShopName());

        textView = v.findViewById(R.id.did);
        textView.setText(seller.getShopName());
        final ImageButton button = v.findViewById(R.id.editacc);
        button.setBackgroundResource(R.drawable.edit_pencil);
        final int[] checkedit = {0};

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedit[0] == 0) {
                    for (int i = 0; i < t.length; i++) {
                        t[i].setEnabled(true);
                    }
                    Toast.makeText(getContext(), "Enter New Details", Toast.LENGTH_SHORT).show();
                    button.setBackgroundResource(0);
                    button.setBackgroundResource(R.drawable.ic_check_black_24dp);
                    checkedit[0] = 1;
                } else {
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
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}