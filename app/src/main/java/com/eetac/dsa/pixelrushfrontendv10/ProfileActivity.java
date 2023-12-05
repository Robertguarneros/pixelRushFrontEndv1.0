package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        UserProfile(username);

    }

    public void UserProfile (String username){
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

            setContentView(R.layout.user_profile);
            TextView nameProfile = findViewById(R.id.editTextNameProfile);
            TextView surnameProfile = findViewById(R.id.editTextSurnameProfile);
            TextView mailProfile = findViewById(R.id.editTextMailProfile);
            TextView usernameProfile = findViewById(R.id.editTextUsernameProfile);
            TextView ageProfile = findViewById(R.id.editTextAgeProfile);

            Call<User> callGetUserProfile = pixelRushService.getUser(username);
            callGetUserProfile.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User userProfile = response.body();

                        // Actualiza los EditText con la información del perfil
                        nameProfile.setText(userProfile.getName());
                        surnameProfile.setText(userProfile.getSurname());
                        mailProfile.setText(userProfile.getMail());
                        usernameProfile.setText(userProfile.getUsername());
                        ageProfile.setText(String.valueOf(userProfile.getBirthDate()));

                        Log.i("FirstVersion_ObjectList", "Object list successful");
                        Toast.makeText(ProfileActivity.this, "Object list successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(ProfileActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                    Toast.makeText(ProfileActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void exitProfile(View view){
        finish();
    }
}
