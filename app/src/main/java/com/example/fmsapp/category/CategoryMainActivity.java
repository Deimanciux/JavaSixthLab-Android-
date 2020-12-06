package com.example.fmsapp.category;

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
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.fmsapp.IPAddress.address;

public class CategoryMainActivity extends AppCompatActivity implements Serializable {
    private User user;
    private FinanceManagementSystem fms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);
        retrieveExtras();

        GetAllUserCategories getAllUserCategories = new GetAllUserCategories();
        getAllUserCategories.execute();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");
    }

    private final class GetAllUserCategories extends AsyncTask<String, String, String> implements Serializable{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/user/" + user.getId() + "/categories";
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
                    Type listType = new TypeToken<ArrayList<Category>>() {
                    }.getType();
                    final List<Category> categoryData = new Gson().fromJson(result, listType);
                    ListView listView = findViewById(R.id.categoriesListView);
                    ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<>(CategoryMainActivity.this, android.R.layout.simple_list_item_1, categoryData);
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        Toast info = Toast.makeText(CategoryMainActivity.this, "Selected category: " + categoryData.get(position).getName(), Toast.LENGTH_LONG);
                        info.show();
                        Intent singleCategoryWindow = new Intent(CategoryMainActivity.this, SingleCategoryActivity.class);
                        singleCategoryWindow.putExtra("category", categoryData.get(position));
                        singleCategoryWindow.putExtra("fms", fms);
                        startActivity(singleCategoryWindow);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CategoryMainActivity.this, "Wrong data", Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(CategoryMainActivity.this, "Wrong data", Toast.LENGTH_LONG);
            }
        }
    }
}