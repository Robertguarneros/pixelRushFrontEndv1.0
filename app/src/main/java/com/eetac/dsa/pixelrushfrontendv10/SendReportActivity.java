package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Question;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Report;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendReportActivity extends AppCompatActivity {
    EditText editTextDate;
    EditText editTextTitle;
    EditText editTextMessage;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_a_report);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Initialize the text views and buttons here
        editTextDate = findViewById(R.id.editTextDate);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMessage = findViewById(R.id.editTextTextMessage);
    }

    public void sendReport(View view){

        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        String date = editTextDate.getText().toString();
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Report report = new Report(date,title,message,username);

        Call<Void> callSendReport = pixelRushService.sendReport(report);

        callSendReport.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Log.i("First Version","Question sent successfully");
                    Toast.makeText(SendReportActivity.this,"Issue sent successfully",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i("First Version","Error: " + response.code() + " " + response.message());
                    Toast.makeText(SendReportActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("First Version","Error: "+t.getMessage(),t);
                Toast.makeText(SendReportActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelReport(View view){
        finish();
    }
}
