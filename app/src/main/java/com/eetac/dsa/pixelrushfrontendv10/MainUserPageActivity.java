package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kotlinx.coroutines.channels.Send;

public class MainUserPageActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        TextView welcomeUser = findViewById(R.id.WelcomeUser);
        welcomeUser.setText("Welcome " + username + "!");

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

    public void logout(View view){
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Get the SharedPreferences.Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Remove the stored values for "username" and "password"
        editor.remove("username");
        editor.remove("password");

        // Apply the changes
        editor.apply();

        Intent intent = new Intent(MainUserPageActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void play(View view){
        Intent intent = new Intent(MainUserPageActivity.this, StoreActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }

    //Minimo 2
    public void goToListBadges(View view){
        Intent intent = new Intent(MainUserPageActivity.this, BadgeActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }
    public void goToListMessages(View view){
        Intent intent = new Intent(MainUserPageActivity.this, MessageActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }
    public void goToAskAQuestion(View view){
        Intent intent = new Intent(MainUserPageActivity.this, AskAQuestionActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }
    public void goToSendReport(View view){
        Intent intent = new Intent(MainUserPageActivity.this, SendReportActivity.class);
        String message = username;
        intent.putExtra("username", message);
        startActivity(intent);
    }
}
