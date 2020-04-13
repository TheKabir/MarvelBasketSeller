package com.example.marvelbasket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelbasket.EditData;
import com.example.marvelbasket.R;
import com.example.marvelbasket.ShowData;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{
    private Context context;
    private List<EditData> contactList;

    public OrdersAdapter(Context context, List<EditData> orderList) {
        this.context = context;
        this.contactList = orderList;
    }
    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercardviewrow, parent, false);
        return new OrdersAdapter.ViewHolder(view);
    }

    // What will happen after we create the viewholder object

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        holder.Name.setText(" Asus ROG Strix Scar III");
        holder.desc.setText("Status : Finished");
        holder.price.setText("120000");

    }

    // How many items?
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Name;
        public TextView desc;
        public TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Name = itemView.findViewById(R.id.orderdisplayName);
            desc = itemView.findViewById(R.id.orderstatus);
            price = itemView.findViewById(R.id.orderdisplayPrice);

        }

        @Override
        public void onClick(View v) {
         /*   AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment fragment = new ShowData();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/
        }
    }

}
