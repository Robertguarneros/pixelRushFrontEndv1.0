package com.eetac.dsa.pixelrushfrontendv10;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PixelRushService {
    String URL = "http://10.0.2.2:8080/dsaApp/pixelRush/";
    @POST("registerNewUser")
    Call<Void> register(@Body RegisterCredentials credentials);
    @POST("login")
    Call<Void> login(@Body LoginCredentials credentials);
    @GET("getObjectListFromStore")
    Call<StoreObject> objectsList ();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
