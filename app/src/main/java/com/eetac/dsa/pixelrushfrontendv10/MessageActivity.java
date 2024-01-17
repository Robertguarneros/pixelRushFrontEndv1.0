package com.eetac.dsa.pixelrushfrontendv10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.MyAdapter;
import com.eetac.dsa.pixelrushfrontendv10.PixelRushService;
import com.eetac.dsa.pixelrushfrontendv10.R;
import com.eetac.dsa.pixelrushfrontendv10.StoreActivity;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Message;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_layout);
        Intent intent = getIntent();
        MessageList();
    }

    public void MessageList () {

        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
        setContentView(R.layout.recycle_view_messages);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Call<List<Message>> callGetAllStoreObjects = pixelRushService.getListMessages();
        callGetAllStoreObjects.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> objects = response.body();

                    // Crear y establecer el adaptador
                    MyAdapterMessages adapter = new MyAdapterMessages(objects, MessageActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                    Log.i("FirstVersion_ObjectList", "Showing Store");
                    Toast.makeText(MessageActivity.this, "Showing Store", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(MessageActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                Toast.makeText(MessageActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void exitStore(View view){
        finish();
    }
}
