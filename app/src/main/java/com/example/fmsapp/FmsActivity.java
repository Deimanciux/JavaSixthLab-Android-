package com.example.fmsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.fmsapp.IPAddress.address;

public class FmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fms);
        GetAllFms getAllFms = new GetAllFms();
        getAllFms.execute();
    }

    private final class GetAllFms extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/fms";
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
            if (result != null) {
                try {
                    Type listType = new TypeToken<ArrayList<FinanceManagementSystem>>() {
                    }.getType();
                    final List<FinanceManagementSystem> fmsData = new Gson().fromJson(result, listType);
                    ListView listView = findViewById(R.id.fmsListView);
                    ArrayAdapter<FinanceManagementSystem> arrayAdapter = new ArrayAdapter<>(FmsActivity.this, android.R.layout.simple_list_item_1, fmsData);
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        Toast info = Toast.makeText(FmsActivity.this, "Selected system: " + fmsData.get(position).toString(), Toast.LENGTH_LONG);
                        info.show();
                        Intent loginWindow = new Intent(FmsActivity.this, LoginActivity.class);
                        loginWindow.putExtra("fms", fmsData.get(position));
                        startActivity(loginWindow);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(FmsActivity.this, "Wrong data", Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(FmsActivity.this, "Wrong data", Toast.LENGTH_LONG);
            }
        }
    }
}