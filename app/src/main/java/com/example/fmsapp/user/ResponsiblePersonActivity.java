package com.example.fmsapp.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataStructures.Category;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.fmsapp.IPAddress.address;

public class ResponsiblePersonActivity extends AppCompatActivity {
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_person);
        retrieveExtras();
        GetAlResponsibleUsers getAlResponsibleUsers = new GetAlResponsibleUsers();
        getAlResponsibleUsers.execute();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");
    }

    private final class GetAlResponsibleUsers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/category/" + category.getId() + "/users";
            try {
                return RESTcontrol.sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.println("RECEIVED: " + result);
            if (!result.equals("")) {
                try {
                    Type listType = new TypeToken<ArrayList<User>>() {
                    }.getType();
                    final List<User> userData = new Gson().fromJson(result, listType);
                    ListView listView = findViewById(R.id.responsibleUsersList);
                    ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(ResponsiblePersonActivity.this, android.R.layout.simple_list_item_1, userData);
                    listView.setAdapter(arrayAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ResponsiblePersonActivity.this, "Wrong data", Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(ResponsiblePersonActivity.this, "No responsible users", Toast.LENGTH_LONG);
            }
        }
    }
}