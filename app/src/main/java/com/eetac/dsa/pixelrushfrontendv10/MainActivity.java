package com.eetac.dsa.pixelrushfrontendv10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.LoginCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.RegisterCredentials;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;

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


        /*we initialize the text views and buttons here
        editTextUsername = findViewById(R.id.editTextUsername);

        editTextPassword = findViewById(R.id.editTextPassword);

        editTextName = findViewById(R.id.editTextName);

        editTextMail = findViewById(R.id.editTextMail);
        editTextAge = findViewById(R.id.editTextAge);
        editTextSurname = findViewById(R.id.editTextSurname);


        buttonRegister= findViewById(R.id.registerBtn);
        buttonLogin = findViewById(R.id.loginBtn);*/
    }


    public void logingPageOpen(View view){
       // builder = new AlertDialog.Builder(this);
        //dialogView = getLayoutInflater().inflate(R.layout.custom_log_in_dialog, null);
        //builder.setView(dialogView);
        setContentView(R.layout.custom_log_in_dialog);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        //alertDialog = builder.create();
        //alertDialog.show();
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
                    logInCorrectly = true;
                    setContentView(R.layout.main_user_page);
                    //alertDialog.dismiss();
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

    public void registerPageOpen(View view){
        //builder = new AlertDialog.Builder(this);
       // dialogView = getLayoutInflater().inflate(R.layout.custom_sign_up_dialog, null);
        //builder.setView(dialogView);
        setContentView(R.layout.custom_sign_up_dialog);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextMail = findViewById(R.id.editTextMail);
        editTextAge = findViewById(R.id.editTextAge);

        //alertDialog = builder.create();
        //alertDialog.show();
    }
    public void register(View view){
        PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String mail = editTextMail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String getAge = editTextAge.getText().toString();
        int ageInt = Integer.parseInt(getAge);

        RegisterCredentials registerCredentials = new RegisterCredentials(username,password,name,surname,mail,ageInt);

        Call<Void> callSingIn = pixelRushService.register(registerCredentials);

        callSingIn.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("First Version","User register successful");
                    Toast.makeText(MainActivity.this,"User register successful",Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.custom_log_in_dialog);
                    //showPrincipalUserPage();
                } else {
                    Log.i("First Version","Error: " + response.code() + " " + response.message());
                    Toast.makeText(MainActivity.this,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("First Version","Error: "+t.getMessage(),t);
                Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
    public void UserProfile (View view){

    }
    public void ClickExit (View view){

        setContentView(R.layout.main_page);
    }
}