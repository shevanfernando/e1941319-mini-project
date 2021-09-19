package com.example.e1941319_mini_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e1941319_mini_project.dto.PackageDTO;

public class AddPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);

        DBAdapter db = new DBAdapter();

        Button btn_save_pkg = (Button) findViewById(R.id.btn_save_pkg);
        EditText txt_pkg_address = (EditText) findViewById(R.id.txt_pkg_address);
        EditText txt_pkg_description = (EditText) findViewById(R.id.txt_pkg_description);


        btn_save_pkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerId = "U_00103";
                String deliveryAddress = txt_pkg_address.getText().toString();
                String description = txt_pkg_description.getText().toString();
                db.addNewPackage(AddPackageActivity.this, new PackageDTO(customerId, deliveryAddress, description)).observe(AddPackageActivity.this, result -> {
                    if (result) {
                        Toast.makeText(AddPackageActivity.this, "New Package Add Successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPackageActivity.this, "New Package Add Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}