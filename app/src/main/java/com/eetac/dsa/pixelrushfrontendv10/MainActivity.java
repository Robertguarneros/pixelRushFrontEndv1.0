package com.eetac.dsa.pixelrushfrontendv10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //we have to search for the text boxes and buttons

    Button logIn;
    Button signIn;

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextMail;
    EditText editTextAge;

    String usernameLogin;
    TextView lineName;
    TextView lineID;
    TextView lineDescription;

    Button buttonRegister;
    Button buttonLogin;
    boolean logInCorrectly = false;



    View dialogView;

    private AlertDialog alertDialog;


    boolean popUpType;

    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        logIn = findViewById(R.id.logIn);
        signIn = findViewById(R.id.signIn);
    }


    public void loginPageOpen(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void registerPageOpen(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void getAllObjectsFromStore (View view) {
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        if (logInCorrectly == true) {
            setContentView(R.layout.recycle_view);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            Call<List<StoreObject>> callGetAllStoreObjects = pixelRushService.getAllObjectsFromStore();
            callGetAllStoreObjects.enqueue(new Callback<List<StoreObject>>() {
                @Override
                public void onResponse(Call<List<StoreObject>> call, Response<List<StoreObject>> response) {
                    if (response.isSuccessful()) {
                        List<StoreObject> objects = response.body();
                        System.out.println("--------------------------------------------------------------\n"+objects.get(0).objectID+"\n"+objects.get(0).articleName+"\n"+objects.get(0).description+"\n"+objects.get(0).price);

                        // Crear y establecer el adaptador
                        MyAdapter adapter = new MyAdapter(objects);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                            Log.i("FirstVersion_ObjectList", "Object list successful");
                            Toast.makeText(MainActivity.this, "Object list successful", Toast.LENGTH_SHORT).show();
                        }
                    else {
                        Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(MainActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<StoreObject>> call, Throwable t) {
                    Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "Log in first please", Toast.LENGTH_SHORT).show();
        }
    }
    public void UserProfile (View view, String username){
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface
        if (logInCorrectly == true) {
            setContentView(R.layout.user_profile);
            TextView nameProfile = findViewById(R.id.editTextNameProfile);
            TextView surnameProfile = findViewById(R.id.editTextSurnameProfile);
            TextView mailProfile = findViewById(R.id.editTextMailProfile);
            TextView usernameProfile = findViewById(R.id.editTextUsernameProfile);
            TextView ageProfile = findViewById(R.id.editTextAgeProfile);

           /* String nameP = nameProfile.toString();
            String surnameP = surnameProfile.toString();
            String mailP = mailProfile.toString();
            String usernameP = usernameProfile.toString();
            String agePS = ageProfile.toString();
            int ageP = Integer.parseInt(agePS);
            String passwordP = null;

            User userProfile = new User(usernameP,passwordP,mailP,nameP,surnameP,ageP);*/
            Call<User> callGetUserProfile = pixelRushService.getUser(usernameLogin);
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
                        Toast.makeText(MainActivity.this, "Object list successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("FirstVersion_ObjectList", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(MainActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("FirstVersion_ObjectList", "Error: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "Log in first please", Toast.LENGTH_SHORT).show();
        }
    }
    public void ClickExit (View view){

        setContentView(R.layout.main_page);
    }
}