package com.eetac.dsa.pixelrushfrontendv10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //we have to search for the text boxes and buttons
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextMail;
    EditText editTextAge;
    Button buttonRegister;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we initialize the text views and buttons here
        editTextUsername = findViewById(R.id.editTextUsername);

        editTextPassword = findViewById(R.id.editTextPassword);

        editTextName = findViewById(R.id.editTextName);

        editTextMail = findViewById(R.id.editTextmail);
        editTextAge = findViewById(R.id.editTextAge);
        editTextSurname = findViewById(R.id.editTextsurname);


        buttonRegister= findViewById(R.id.registerBtn);
        buttonLogin = findViewById(R.id.loginBtn);
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
                    Toast.makeText(MainActivity.this,"User login successful",Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Error: " + response.code() + " " + response.message());
                    Toast.makeText(MainActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error: "+t.getMessage());
                Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void register(View view){
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String mail = editTextMail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String age = editTextAge.getText().toString();
        int ageInt = Integer.parseInt(age);

        RegisterCredentials singinCredentials = new RegisterCredentials(username,password,name,surname,mail,ageInt);

        Call<Void> callsingin = pixelRushService.register(singinCredentials);

        callsingin.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("iteracion1","User register successful");
                    Toast.makeText(MainActivity.this,"User register successful",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("iteracion1","Error: " + response.code() + " " + response.message());
                    Toast.makeText(MainActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("iteracion1","Error: "+t.getMessage(),t);
                Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}