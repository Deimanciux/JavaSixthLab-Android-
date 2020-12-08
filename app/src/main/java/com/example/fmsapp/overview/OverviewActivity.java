package com.example.fmsapp.overview;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataModels.Balance;
import com.example.fmsapp.dataModels.Overview;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.fmsapp.IPAddress.address;

public class OverviewActivity extends AppCompatActivity {
    private User user;
    private TextView fromDate;
    private TextView toDate;
    private TextView generatedBalance;
    private DatePickerDialog.OnDateSetListener fromListener;
    private DatePickerDialog.OnDateSetListener toListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setValues();
        retrieveExtras();
        GetAllOverviews getAllOverviews = new GetAllOverviews();
        getAllOverviews.execute();

        fromDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    OverviewActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    fromListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        fromListener = (datePicker, year, month, day) -> {
            month = month + 1;

            String date = year + "-" +
                    ((month < 10) ? "0" + month : month) + "-" +
                    ((day < 10) ? "0" + day : day);
            fromDate.setText(date);
        };

        toDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    OverviewActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    toListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        toListener = (datePicker, year, month, day) -> {
            month = month + 1;

            String date = year + "-" +
                    ((month < 10) ? "0" + month : month) + "-" +
                    ((day < 10) ? "0" + day : day);
            toDate.setText(date);
        };
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private void setValues() {
        fromDate = findViewById(R.id.fromDate);
        fromDate.setText("Click To Choose");

        toDate = findViewById(R.id.toDate);
        toDate.setText("Click To Choose");

        generatedBalance = findViewById(R.id.generatedBalance);
        generatedBalance.setText("0.00");
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

    private final class OverviewBalanceByDate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = address + "/balance/byDate";
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
            if (result.equals("")) {
                Toast.makeText(getBaseContext(), "Wrong data", Toast.LENGTH_LONG).show();
                return;
            }

            Type type = new TypeToken<Balance>() {
            }.getType();
            Balance balance = new Gson().fromJson(result, type);
            generatedBalance.setText(String.format("%.2f", balance.getBalance()));
        }
    }

    public void generateBalanceByDate(View view) {
        String data = "{\"fromDate\":\"" + fromDate.getText().toString() +
                "\", \"toDate\":\"" + toDate.getText().toString() +
                "\", \"userId\":" + user.getId() + "}";

        OverviewBalanceByDate overviewBalanceByDate = new OverviewBalanceByDate();
        overviewBalanceByDate.execute(data);
    }
}