package com.example.fmsapp.user;

import android.content.Intent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.dataStructures.User;

public class UserProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        retrieveExtras();

        EditText name = findViewById(R.id.name);
        name.setText(user.getName());

        EditText loginName = findViewById(R.id.loginName);
        loginName.setText(user.getLoginName());

        EditText email = findViewById(R.id.email);
        email.setText(user.getEmail());

        EditText surname = findViewById(R.id.surname);
        surname.setText(user.getSurname());
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }
}