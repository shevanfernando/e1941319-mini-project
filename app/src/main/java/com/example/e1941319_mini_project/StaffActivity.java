package com.example.e1941319_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e1941319_mini_project.model.Package;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        DBAdapter db = new DBAdapter();

        Button addNewPkg = findViewById(R.id.btn_add_pkg);

        addNewPkg.setOnClickListener(view -> {
            Toast.makeText(StaffActivity.this, "Open add new package page", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(StaffActivity.this, AddPackageActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        DBAdapter db = new DBAdapter();

        db.getAllPackages("Staff Activity", null).observe(StaffActivity.this, res -> {
            if (res != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(StaffActivity.this, android.R.layout.simple_list_item_1, res.getPackageIdList());
                getSupportFragmentManager().beginTransaction().add(R.id.container, RecyclerViewFragment.newInstance(StaffActivity.this, res.getPackageData())).commit();
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