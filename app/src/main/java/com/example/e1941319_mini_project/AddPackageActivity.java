package com.example.e1941319_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.e1941319_mini_project.dto.PackageDTO;

import java.util.List;
import java.util.Objects;

public class AddPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        DBAdapter db = new DBAdapter();

        Button btn_save_pkg = (Button) findViewById(R.id.btn_save_pkg);
        EditText txt_pkg_address = (EditText) findViewById(R.id.txt_pkg_address);
        EditText txt_pkg_description = (EditText) findViewById(R.id.txt_pkg_description);

        AutoCompleteTextView txt_search_customer = (AutoCompleteTextView) findViewById(R.id.txt_search_customer);

        db.getAllCustomers().observe(AddPackageActivity.this, res -> {
            if (!res.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddPackageActivity.this, android.R.layout.simple_list_item_1, res);
                txt_search_customer.setAdapter(adapter);
            }
        });


        btn_save_pkg.setOnClickListener(view -> {
            String customerId = txt_search_customer.getText().toString();
            String deliveryAddress = txt_pkg_address.getText().toString();
            String description = txt_pkg_description.getText().toString();
            db.addNewPackage(AddPackageActivity.this, new PackageDTO(customerId, deliveryAddress, description)).observe(AddPackageActivity.this, result -> {
                if (result) {
                    Toast.makeText(AddPackageActivity.this, "New Package Add Successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPackageActivity.this, "New Package Add Failed.", Toast.LENGTH_SHORT).show();
                }
            });
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itm_logout: {
                Toast.makeText(AddPackageActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddPackageActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case android.R.id.home: {
                Toast.makeText(AddPackageActivity.this, "Redirect to Staff Dashboard.", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}