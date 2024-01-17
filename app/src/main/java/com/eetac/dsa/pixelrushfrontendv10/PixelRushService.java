package com.eetac.dsa.pixelrushfrontendv10;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Badge;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Message;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.User;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Question;
import com.google.gson.JsonObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;

public interface PixelRushService {
    //String URL = "http://147.83.7.203:80/dsaApp/pixelRush/";

    String URL = "http://147.83.7.203:80/dsaApp/pixelRush/";
    //String URL = "http://10.0.2.2:8080/dsaApp/pixelRush/";
    @POST("registerNewUser")
    Call<Void> register(@Body RegisterCredentials credentials);
    @POST("login")
    Call<Void> login(@Body LoginCredentials credentials);
    @GET("getObjectListFromStore")
    Call<List<StoreObject>> getAllObjectsFromStore();
    @GET("{username}")
    Call<User> getUser(@Path("username") String username);
    @PUT("addItemToUser/{username}/{objectID}")
    Call<Void> addItemToUser(@Path("username") String username, @Path("objectID") String objectID);
    @POST("question")
    Call<Void> askAQuestion(@Body Question question);
    @GET("getMatchPointsFromActiveMatch/{username}")
    Call<JsonObject> getMatchPointsFromActiveMatch(@Path("username")String username);
    @GET("user/{username}/badges")
    Call<List<Badge>> getListBadge (@Path("username")String username);
    @GET("posts")
    Call<List<Message>> getListMessages ();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
