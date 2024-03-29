package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.OwnedObjects;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {
    String username;
    List<OwnedObjects> objects;
    MyAdapter adapter; // Declare MyAdapter instance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_layout);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        getAllObjectsFromStore();

        coinNumber();
    }

    public void getAllObjectsFromStore() {
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);

        setContentView(R.layout.recycle_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Call<List<StoreObject>> callGetAllStoreObjects = pixelRushService.getAllObjectsFromStore();
        callGetAllStoreObjects.enqueue(new Callback<List<StoreObject>>() {
            @Override
            public void onResponse(Call<List<StoreObject>> call, Response<List<StoreObject>> response) {
                if (response.isSuccessful()) {
                    List<StoreObject> storeObjects = response.body();
                    adapter = new MyAdapter(storeObjects, StoreActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));
                    getOwnedObjects();
                    Log.i("FirstVersion_ObjectList", "Showing Store");
                    Toast.makeText(StoreActivity.this, "Showing Store", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(StoreActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StoreObject>> call, Throwable t) {
                Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                Toast.makeText(StoreActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getOwnedObjects() {
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);
        Call<List<OwnedObjects>> callGetOwnedObjects = pixelRushService.getOwnedObjects(username);

        callGetOwnedObjects.enqueue(new Callback<List<OwnedObjects>>() {
            @Override
            public void onResponse(Call<List<OwnedObjects>> call, Response<List<OwnedObjects>> response) {
                if (response.isSuccessful()) {
                    objects = response.body();
                    // Set the list of owned objects in the adapter
                    if (adapter != null) {
                        adapter.setOwnedObjectsList(objects);
                        Log.i("Adapter set owned objects correctly ", "Adapter did not set owned objects");
                    }
                } else {
                    Log.i("Adapter did not set owned objects", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(StoreActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OwnedObjects>> call, Throwable t) {
                Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                Toast.makeText(StoreActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void coinNumber() {
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        TextView coinsTextView = findViewById(R.id.coinNumber);

        Call<User> callGetUserProfile = pixelRushService.getUser(username);
        callGetUserProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userProfile = response.body();

                    // Actualiza los EditText con la información del perfil
                    coinsTextView.setText(userProfile.getPointsEarned() + "");

                    Log.i("FirstVersion_ObjectList", "Getting User Profile");
                } else {
                    Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(StoreActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                Toast.makeText(StoreActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void exitStore(View view){
        finish();
    }
}
