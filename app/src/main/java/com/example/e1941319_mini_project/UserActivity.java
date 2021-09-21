package com.example.e1941319_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    private String customerId = null;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DBAdapter();
        customerId = getIntent().getStringExtra("customerId");

        db.getAllPackages("User Activity", customerId).observe(UserActivity.this, res -> {
            if (res != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UserActivity.this, android.R.layout.simple_list_item_1, res.getPackageIdList());
                getSupportFragmentManager().beginTransaction().add(R.id.container, RecyclerViewFragment.newInstance(UserActivity.this, res.getPackageData(), UserType.USER, null)).
                        commit();
            }
        });
    }

    // add menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // handle the menu item actions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itm_logout) {
            Toast.makeText(UserActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}