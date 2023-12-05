package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainUserPageActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

    }

    public void goToProfile(View view){
        Intent intent = new Intent(MainUserPageActivity.this, ProfileActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }
    public void goToStore(View view){
        Intent intent = new Intent(MainUserPageActivity.this, StoreActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }

}
