package com.example.fmsapp;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fmsapp.category.CategoryMainActivity;
import com.example.fmsapp.dataStructures.FinanceManagementSystem;
import com.example.fmsapp.dataStructures.User;
import com.example.fmsapp.overview.OverviewActivity;
import com.example.fmsapp.user.UserProfileActivity;

public class MainMenuActivity extends AppCompatActivity {

    private FinanceManagementSystem fms;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        retrieveExtras();
    }

    private void retrieveExtras() {
        Intent intent = getIntent();
        fms = (FinanceManagementSystem) intent.getSerializableExtra("fms");
        user = (User) intent.getSerializableExtra("user");
    }

    public void manageCategories(View view) {
        Intent manageCategoriesWindow = new Intent(MainMenuActivity.this, CategoryMainActivity.class);
        manageCategoriesWindow.putExtra("fms", fms);
        manageCategoriesWindow.putExtra("user", user);
        startActivity(manageCategoriesWindow);
    }

    public void manageProfile(View view) {
        Intent profileWindow = new Intent(MainMenuActivity.this, UserProfileActivity.class);
        profileWindow.putExtra("user", user);
        startActivity(profileWindow);
    }

    public void logOutUser(View view) {
        Intent logOutUserWindow = new Intent(MainMenuActivity.this, FmsActivity.class);
        startActivity(logOutUserWindow);
    }

    public void goToOverview(View view) {
        Intent overviewWindow = new Intent(MainMenuActivity.this, OverviewActivity.class);
        overviewWindow.putExtra("user", user);
        startActivity(overviewWindow);
    }
}