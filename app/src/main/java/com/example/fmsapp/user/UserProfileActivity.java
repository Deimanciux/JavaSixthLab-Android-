package com.example.fmsapp.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataStructures.User;

import java.io.Serializable;

import static com.example.fmsapp.IPAddress.address;

public class UserProfileActivity extends AppCompatActivity {

    private User user;
    private EditText name;
    private EditText loginName;
    private EditText email;
    private EditText surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        retrieveExtras();

        name = findViewById(R.id.name);
        name.setText(user.getName());

        loginName = findViewById(R.id.loginName);
        loginName.setText(user.getLoginName());

        email = findViewById(R.id.email);
        email.setText(user.getEmail());

        surname = findViewById(R.id.surname);
        surname.setText(user.getSurname());
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText().toString()) &&
                isNotEmpty(loginName.getText().toString()) &&
                isNotEmpty(email.getText().toString()) &&
                isNotEmpty(surname.getText().toString());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    public void editUser (View v) {
        if(!checkIfFieldsAreEmpty()) {
            Toast.makeText(UserProfileActivity.this, "Fields can not be empty", Toast.LENGTH_LONG);
            return;
        }

        String userData = "{\"name\":\"" + name.getText() +
                "\", \"loginName\":\"" + loginName.getText() +
                "\", \"email\":\"" + email.getText() +
                "\", \"surname\":\"" + surname.getText() + "\"}";

        EditUser editUser = new EditUser();
        editUser.execute(userData);
    }

    private final class EditUser extends AsyncTask<String, String, String> implements Serializable {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = address + "/user/" + user.getId();
            String postDataParameters = params[0];
            System.out.println("SENT: " + postDataParameters);

            try {
                return RESTcontrol.sendPut(url, postDataParameters);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("RECEIVED: " + result);
            System.out.println(result);

            Toast.makeText(UserProfileActivity.this, "User updated", Toast.LENGTH_LONG);
        }
    }
}