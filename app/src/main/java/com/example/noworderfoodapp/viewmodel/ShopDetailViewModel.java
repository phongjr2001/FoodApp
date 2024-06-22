package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailViewModel extends ViewModel {
    public void deleteOrderData(int id){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                deleteOrder(id);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 200 || apiResponse.getStatus() ==201) {

                    }
                } else {
                    // Xử lý khi có lỗi từ API

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onResponse: fail");
            }
        });
    }
}
