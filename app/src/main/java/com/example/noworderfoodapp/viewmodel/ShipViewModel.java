package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipViewModel extends ViewModel {
    public ShipViewModel(){
        orderListMutableLiveData = new MutableLiveData<>();
    }


    private MutableLiveData<List<Orders>> orderListMutableLiveData;
    public MutableLiveData<List<Orders>> getOrderShippingMutableLiveData() {
        return orderListMutableLiveData;
    }

    public void getOrderList() {
        Call<ResponseDTO<List<Orders>>> call = ApiService.apiService.getListOrders();
        call.enqueue(new Callback<ResponseDTO<List<Orders>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Orders>>> call,
                                   Response<ResponseDTO<List<Orders>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Orders>> apiResponse = response.body();
                    List<Orders> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (orders != null) {
                        orderListMutableLiveData.postValue(filterOrderState(orders));
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Orders>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    private List<Orders> filterOrderState(List<Orders> orders) {
        List<Orders> listFilter = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getStates().equals("Delivery")) {
                listFilter.add(orders.get(i));
            }
        }
        return listFilter;
    }

    public void receiver(int id, int shipper_id) {
        Call<ResponseDTO<Void>> call = ApiService.apiService.receiver(id, shipper_id);
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call, Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    Log.i("KMFG", "receiver: ");
                    getOrderList();
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

}
