package com.example.fmsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.category.CategoryMainActivity;
import com.example.fmsapp.dataStructures.Category;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;
import com.example.fmsapp.user.ResponsiblePersonActivity;

import static com.example.fmsapp.IPAddress.address;

public class MoreOptionsActivity extends AppCompatActivity {
    private Category category;
    private FinanceManagementSystem fms;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);
        retrieveExtras();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");
        user = (User) intent.getSerializableExtra("user");
    }

    public void goToShowResponsiblePersons(View view) {
        Intent intent = new Intent(MoreOptionsActivity.this, ResponsiblePersonActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void goToDeleteCategory(View view) {
        DeleteCategory deleteCategory = new DeleteCategory();
        deleteCategory.execute();
    }

    private final class DeleteCategory extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/category/" + category.getId();
            try {
                return RESTcontrol.sendDelete(url);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("cia BUVOOOOOO");
            Intent intent = new Intent(MoreOptionsActivity.this, CategoryMainActivity.class);
            intent.putExtra("fms", fms);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}