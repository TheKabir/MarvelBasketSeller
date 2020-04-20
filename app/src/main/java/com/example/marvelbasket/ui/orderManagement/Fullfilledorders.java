package com.example.marvelbasket.ui.orderManagement;

import androidx.lifecycle.ViewModelProviders;

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

import java.util.ArrayList;

public class Fullfilledorders extends Fragment {

    private FullfilledordersViewModel mViewModel;

    //declaring attributes
    private RecyclerView recyclerView;
    private OrdersAdapter recyclerViewAdapter;
    private ArrayList<EditData> finishedOrdersArrayList;
    private ArrayAdapter<String> arrayAdapter;

    public static Fullfilledorders newInstance() {
        return new Fullfilledorders();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fullfilledorders_fragment, container, false);
// setting views
        recyclerView = v.findViewById(R.id.finishedOrdersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//adding data
        finishedOrdersArrayList = new ArrayList<>();

        //data setting hint
        finishedOrdersArrayList.add(new EditData("Canon 1300D DSLR","finished","38000"));
        EditData contact = new EditData("Iphone 11","finished","90000");
        for(int i=0;i<10;i++)
        {
            finishedOrdersArrayList.add(contact);
        }

        recyclerViewAdapter = new OrdersAdapter(getContext(), finishedOrdersArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);




        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FullfilledordersViewModel.class);
        // TODO: Use the ViewModel
    }

}
