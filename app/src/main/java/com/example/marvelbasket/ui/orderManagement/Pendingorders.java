package com.example.marvelbasket.ui.orderManagement;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marvelbasket.EditData;
import com.example.marvelbasket.R;
import com.example.marvelbasket.adapter.OrdersAdapter;
import com.example.marvelbasket.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Pendingorders extends Fragment {

    private PendingordersViewModel mViewModel;
    //variables declaration
    private RecyclerView recyclerView;
    private OrdersAdapter recyclerViewAdapter;
    private  ArrayList<EditData> pendingOrdersArrayList;
    private ArrayAdapter<String> arrayAdapter;

    public static Pendingorders newInstance() {
        return new Pendingorders();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.pendingorders_fragment, container, false);


        recyclerView = v.findViewById(R.id.pendingOrdersrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView name= v.findViewById(R.id.orderdisplayName);
        TextView desc= v.findViewById(R.id.orderstatus);
        TextView price= v.findViewById(R.id.orderdisplayPrice);



//adding data
        pendingOrdersArrayList = new ArrayList<>();
        EditData contact = new EditData("abc","def","ghi");

        for(int i=0;i<10;i++)
        {
            pendingOrdersArrayList.add(contact);
        }

        recyclerViewAdapter = new OrdersAdapter(getContext(), pendingOrdersArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PendingordersViewModel.class);
        // TODO: Use the ViewModel
    }

}
