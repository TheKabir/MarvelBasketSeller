package com.example.marvelbasket.ui.editp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
import com.example.marvelbasket.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class Editproduct extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private  ArrayList<EditData> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
    private EditproductViewModel mViewModel;

    public static Editproduct newInstance() {
        return new Editproduct();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.editproduct_fragment, container, false);
       //Recycler view
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//adding data
        contactArrayList = new ArrayList<>();
        EditData contact = new EditData("Apple MacBook Pro 2019","in Electronics","185000");
        contactArrayList.add(new EditData("Canon 1300D DSLR","in Electronics","38000"));

        for(int i=0;i<9;i++)
        {
            contactArrayList.add(contact);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditproductViewModel.class);
        // TODO: Use the ViewModel
    }



}
