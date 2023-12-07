package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity {
    String username;
    ProgressBar progressBarStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_layout);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        getAllObjectsFromStore();
        progressBarStore = findViewById(R.id.progressBarProfile);

    }

    public void getAllObjectsFromStore () {
        progressBarStore.setVisibility(View.VISIBLE);
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
            setContentView(R.layout.recycle_view);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            Call<List<StoreObject>> callGetAllStoreObjects = pixelRushService.getAllObjectsFromStore();
            callGetAllStoreObjects.enqueue(new Callback<List<StoreObject>>() {
                @Override
                public void onResponse(Call<List<StoreObject>> call, Response<List<StoreObject>> response) {
                    progressBarStore.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        List<StoreObject> objects = response.body();

                        // Crear y establecer el adaptador
                        MyAdapter adapter = new MyAdapter(objects,StoreActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));


                        Log.i("FirstVersion_ObjectList", "Object list successful");
                        Toast.makeText(StoreActivity.this, "Object list successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(StoreActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<StoreObject>> call, Throwable t) {
                    progressBarStore.setVisibility(View.GONE);

                    Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                    Toast.makeText(StoreActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void exitStore(View view){
        finish();
    }
}
