package com.example.fmsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.fmsapp.IPAddress.address;

public class LoginActivity extends AppCompatActivity {
    private FinanceManagementSystem fms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrieveExtras();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");
    }

    public void connectToFms(View v) {
        EditText login = findViewById(R.id.loginField);
        EditText psw = findViewById(R.id.passwordField);
        String loginData = "{\"loginName\":\"" + login.getText().toString() +
                "\", \"password\":\"" + psw.getText().toString()+
                "\", \"fms_id\":" + fms.getId() + "}";

        UserLogin userLogin = new UserLogin();
        userLogin.execute(loginData);
    }


    private final class UserLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, "Validating login data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = address + "/user/login";
            String postDataParameters = params[0];
            System.out.println("SENT: " + postDataParameters);
            try {
                return RESTcontrol.sendPost(url, postDataParameters);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error while getting data from web";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("RECEIVED: " + result);
            if (result == null) {
                Toast.makeText(LoginActivity.this, "Wrong data", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                Type type = new TypeToken<User>() {}.getType();
                User user = new Gson().fromJson(result, type);

                Intent mainMenuWindow = new Intent(LoginActivity.this, MainMenuActivity.class);
                mainMenuWindow.putExtra("fms", fms);
                mainMenuWindow.putExtra("user", user);
                startActivity(mainMenuWindow);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Wrong data", Toast.LENGTH_LONG).show();
            }
        }
    }
}