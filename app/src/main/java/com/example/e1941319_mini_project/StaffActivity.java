package com.example.e1941319_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class StaffActivity extends AppCompatActivity {

    private static final String[] COUNTRIES = new String[]{"SRI LANKA", "USA", "UK", "INDIA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Button addNewPkg = findViewById(R.id.btn_add_pkg);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.txt_search_pkg);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StaffActivity.this, android.R.layout.simple_list_item_1, COUNTRIES);
        autoCompleteTextView.setAdapter(adapter);

        addNewPkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StaffActivity.this, "Open add new package page", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StaffActivity.this, AddPackageActivity.class);
                startActivity(intent);
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
            Toast.makeText(StaffActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StaffActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}