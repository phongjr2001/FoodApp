package com.example.noworderfoodapp.api;

import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.entity.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
    @GET("/user/list")
    Call<ResponseDTO<List<User>>> getListUser();

    @FormUrlEncoded
    @POST("/user/get")
    Call<ResponseDTO<User>> getUserNameAndPassword(@Field("username") String username,
                                        @Field("password") String password);

    @GET("/shop/list")
    Call<ResponseDTO<List<Shop>>> getListShop();

    @GET("/orders/list")
    Call<ResponseDTO<List<Orders>>> getListOrders();

    @GET("/orders/ship")
    Call<ResponseDTO<List<Orders>>> searchOrderByShipper(@Query("id") int id);

    @GET("/orders/search")
    Call<ResponseDTO<List<Orders>>> searchOrderByUser(@Query("id") int id);

    @GET("/{id}")
    Call<ResponseDTO<User>> getUserById(@Path("id") int id);
    @FormUrlEncoded
    @POST("/user/edit")
    Call<ResponseDTO<Void>> editUser(  @Field("id") int id,
                                       @Field("name") String name,
                                       @Field("phonenumber") String phonenumber,
                                       @Field("homeAddress") String homeAddress,
                                       @Field("age") int age,//homeAddress
                                       @Field("birthDate") Date birthDate);
    @POST("/orders/update")
    Call<ResponseDTO<Void>> orderUpdate(@Body Orders orders);

    @FormUrlEncoded
    @POST("/user/new")
    Call<ResponseDTO<Void>> createNewUser(//homeAddress
                                          @Field("name") String name,
                                          @Field("age") int age,
                                          @Field("username") String username,
                                          @Field("password") String password,
                                          @Field("phonenumber") String phonenumber,
                                          @Field("homeAddress") String homeAddress,
                                          @Field("roles") List<String> roles);

    @POST("/orders/customer")
    Call<ResponseDTO<Void>> postOrderData(@Body Orders orders);

    @POST("/orders/receive")
    Call<ResponseDTO<Void>> receiver(@Query("id") int id, @Query("shipper_id") int shipperId);

    @POST("/orders/delete")
    Call<ResponseDTO<Void>> deleteOrder(@Query("id") int id);
}