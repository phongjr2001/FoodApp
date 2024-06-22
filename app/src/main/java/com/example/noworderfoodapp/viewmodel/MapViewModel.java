package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewModel extends ViewModel {
    private MutableLiveData<Boolean> isUpdate = new MutableLiveData<>();
    public MapViewModel(){
    }

    public void updateOrder(Orders orders){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                orderUpdate(orders);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    //List<Orders> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 201 || apiResponse.getStatus() == 200) {
                       isUpdate.postValue(true);
                        Log.i("KMFG", "Done: ");
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    isUpdate.postValue(false);
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isUpdate.postValue(false);
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

}
