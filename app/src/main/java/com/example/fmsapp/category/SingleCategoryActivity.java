package com.example.fmsapp.category;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.MoreOptionsActivity;
import com.example.fmsapp.R;
import com.example.fmsapp.REST.RESTcontrol;
import com.example.fmsapp.dataModels.Balance;
import com.example.fmsapp.dataStructures.Category;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

import static com.example.fmsapp.IPAddress.address;

public class SingleCategoryActivity extends AppCompatActivity implements Serializable {
    private Category category;
    private FinanceManagementSystem fms;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);
        retrieveExtras();
        printCategoryDetails();

        GetSystemBalance getSystemBalance = new GetSystemBalance();
        getSystemBalance.execute();

        GetCategoryBalance getCategoryBalance = new GetCategoryBalance();
        getCategoryBalance.execute();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");
        user = (User) intent.getSerializableExtra("user");
    }

    private void printCategoryDetails() {
        EditText editText = findViewById(R.id.singleCategoryText);
        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setText(category.toPrint());
        editText.setFocusable(false);
    }

    private final class GetSystemBalance extends AsyncTask<String, String, String> implements Serializable {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/balance/system/" + fms.getId();
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
                    Type type = new TypeToken<Balance>() {}.getType();
                    Balance balance = new Gson().fromJson(result, type);
                    fillBalanceTextViews(balance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("err");
            }
        }
    }

    private void fillBalanceTextViews(Balance balance) {
        TextView systemBalanceTextView = findViewById(R.id.systemBalance);
        systemBalanceTextView.setText(String.format("%.2f", balance.getBalance()));

        TextView incomeTextView = findViewById(R.id.systemIncomes);
        incomeTextView.setText(String.format("%.2f", balance.getIncome()));

        TextView expenseTextView = findViewById(R.id.systemExpenses);
        expenseTextView.setText(String.format("%.2f", balance.getExpense()));
    }

    private final class GetCategoryBalance extends AsyncTask<String, String, String> implements Serializable {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = address + "/balance/category/" + category.getId();
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
                    Type type = new TypeToken<Balance>() {}.getType();
                    Balance balance = new Gson().fromJson(result, type);
                    fillCategoryTextViews(balance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("err");
            }
        }
    }

    private void fillCategoryTextViews(Balance balance) {
        TextView categoryBalanceTextview = findViewById(R.id.categoryBalance);
        categoryBalanceTextview.setText(String.format("%.2f", balance.getBalance()));

        TextView incomeTextView = findViewById(R.id.categoryIncomes);
        incomeTextView.setText(String.format("%.2f", balance.getIncome()));

        TextView expenseTextView = findViewById(R.id.categoryExpenses);
        expenseTextView.setText(String.format("%.2f", balance.getExpense()));
    }

    public void goToMoreOptions (View view) {
        Intent moreOptionsWindow = new Intent(SingleCategoryActivity.this, MoreOptionsActivity.class);
        moreOptionsWindow.putExtra("category", category);
        moreOptionsWindow.putExtra("fms", fms);
        moreOptionsWindow.putExtra("user", user);
        startActivity(moreOptionsWindow);
    }
}