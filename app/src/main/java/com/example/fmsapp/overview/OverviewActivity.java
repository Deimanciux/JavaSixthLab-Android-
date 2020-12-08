package com.example.fmsapp.overview;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataModels.Overview;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.fmsapp.IPAddress.address;

public class OverviewActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        retrieveExtras();
        GetAllOverviews getAllOverviews = new GetAllOverviews();
        getAllOverviews.execute();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private final class GetAllOverviews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/overview/" + user.getId();
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
                    Type listType = new TypeToken<ArrayList<Overview>>() {
                    }.getType();
                    final List<Overview> overviewData = new Gson().fromJson(result, listType);
                    ListView listView = findViewById(R.id.overviewList);
                    ArrayAdapter<Overview> arrayAdapter = new ArrayAdapter<>(OverviewActivity.this, android.R.layout.simple_list_item_1, overviewData);
                    listView.setAdapter(arrayAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(OverviewActivity.this, "Wrong data", Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(OverviewActivity.this, "Wrong data", Toast.LENGTH_LONG);
            }
        }
    }
}