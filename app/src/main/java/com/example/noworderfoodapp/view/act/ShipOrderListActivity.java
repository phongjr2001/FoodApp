package com.example.noworderfoodapp.view.act;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.viewmodel.OrderViewModel;
import com.example.noworderfoodapp.viewmodel.ShipViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShipOrderListActivity extends AppCompatActivity {
    private OrderAdapter orderAdapter;
    private ShipViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_order_list);

        // Assuming you have a ViewModelProvider set up in your BaseActivity
        viewModel = new ViewModelProvider(this).get(ShipViewModel.class);

        // Initialize RecyclerView and Adapter
        RecyclerView rcvShipperOrderList = findViewById(R.id.rcv_shipper_order_list_1);
        orderAdapter = new OrderAdapter(new ArrayList<>(), this);
        // Set layout manager and adapter
        rcvShipperOrderList.setLayoutManager(new LinearLayoutManager(this));
        rcvShipperOrderList.setAdapter(orderAdapter);

        // Set item click listener
        orderAdapter.setOnItemClick(new OrderAdapter.OnItemClick() {
            @Override
            public void onItemClick(Orders orders) {
                // Handle item click event

                showCustomDialog(orders);

                Toast.makeText(
                        ShipOrderListActivity.this,
                        "Shop: " + orders.getShopName() + "Address:" + orders.getAddress(),
                        Toast.LENGTH_LONG).show();
            }
        });


        // Observe LiveData for changes in orders
        viewModel.getOrderShippingMutableLiveData().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                // Update the adapter with the new list of orders
                if (orderAdapter != null) {
                    orderAdapter.setOrdersList(orders);
                    orderAdapter.notifyDataSetChanged();

                }
            }
        });

        viewModel.getOrderList();
    }

    private void showCustomDialog(Orders orders) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đơn hàng!");
        builder.setMessage("Bạn có muốn nhận đơn này?");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // call api receiver
                viewModel.receiver(orders.getId(), App.getInstance().getUser().getId());
            }
        });

        builder.setNegativeButton("Không nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}