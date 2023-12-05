package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;

    boolean logInCorrectly;
    String usernameLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_log_in_dialog);

        // Initialize the text views and buttons here
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void login(View view) {
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        LoginCredentials loginCredentials = new LoginCredentials(username,password);

        Call<Void> callLogin = pixelRushService.login(loginCredentials);

        callLogin.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("User login successful");
                    Toast.makeText(LoginActivity.this,"User login successful",Toast.LENGTH_SHORT).show();
                    usernameLogin=username;
                    Intent intent = new Intent(LoginActivity.this, MainUserPageActivity.class);
                    String message = usernameLogin;
                    intent.putExtra("username", message);
                    startActivity(intent);
                } else {
                    System.out.println("Error: " + response.code() + " " + response.message());
                    Toast.makeText(LoginActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error: "+t.getMessage());
                Toast.makeText(LoginActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void cancelLoginUp(View view){
        finish();
    }
}
