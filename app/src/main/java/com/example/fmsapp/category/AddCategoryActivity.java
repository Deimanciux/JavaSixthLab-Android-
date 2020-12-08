package com.example.fmsapp.category;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;

import static com.example.fmsapp.IPAddress.address;

public class AddCategoryActivity extends AppCompatActivity {
    private FinanceManagementSystem fms;
    private User user;
    EditText name;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        retrieveExtras();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");

        name = findViewById(R.id.categoryName);
        description = findViewById(R.id.categoryDescription);
    }

    public void createCategory(View view) {
        if(!checkIfFieldsAreEmpty()) {
            Toast.makeText(AddCategoryActivity.this, "Fields can not be empty", Toast.LENGTH_LONG);
            return;
        }

        String categoryData = "{\"name\":\"" + name.getText() +
                "\", \"description\":\"" + description.getText() +
                "\", \"fmsId\":\"" + fms.getId() +
                "\", \"userId\":\"" + user.getId() +
                "\"}";

        CategoryCreator categoryCreator = new CategoryCreator();
        categoryCreator.execute(categoryData);
    }

    private boolean checkIfFieldsAreEmpty() {
        return isNotEmpty(name.getText().toString()) &&
                isNotEmpty(description.getText().toString());
    }

    private boolean isNotEmpty(String text) {
        return text != null && !text.equals("");
    }

    private final class CategoryCreator extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = address + "/category";
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
            if (result.equals("0")) {
                Toast.makeText(getBaseContext(), "Wrong data", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(AddCategoryActivity.this, "Category created", Toast.LENGTH_LONG);
        }
    }
}