package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipDoneViewModel extends ViewModel {
    public ShipDoneViewModel(){
        orderShippingMutableLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<List<Orders>> orderShippingMutableLiveData;

    public MutableLiveData<List<Orders>> getOrderShippingMutableLiveData() {
        return orderShippingMutableLiveData;
    }

    public void getOrderByShipper(int id) {
        Call<ResponseDTO<List<Orders>>> call = ApiService.apiService.
                searchOrderByShipper(id);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Orders>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Orders>>> call,
                                   Response<ResponseDTO<List<Orders>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Orders>> apiResponse = response.body();
                    List<Orders> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (orders != null) {
                        orderShippingMutableLiveData.postValue(filterOrderState(orders));
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
            if (orders.get(i).getStates().equals("Done")) {
                listFilter.add(orders.get(i));
            }
        }
        return listFilter;
    }

}
