package com.example.e1941319_mini_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e1941319_mini_project.dto.LoginAndRegisterDTO;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class UserRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView txt_username = findViewById(R.id.txt_username);
        TextView txt_password = findViewById(R.id.txt_password);
        TextView txt_cn_password = findViewById(R.id.txt_cn_password);
        Button btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(view -> {
            try {

                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String reenterPassword = txt_cn_password.getText().toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(reenterPassword)) {
                    Toast.makeText(UserRegistrationActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(UserRegistrationActivity.this, "Username field is required!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(UserRegistrationActivity.this, "Password field is required!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(reenterPassword)) {
                    Toast.makeText(UserRegistrationActivity.this, "Re-enter password field is required!", Toast.LENGTH_SHORT).show();
                } else if (password.equals(reenterPassword)) {
                    DBAdapter db = new DBAdapter();

                    db.register(UserRegistrationActivity.this, new LoginAndRegisterDTO(username, password)).observe(UserRegistrationActivity.this, res -> {
                        if (res != null) {
                            if (res.getStatus()) {
                                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(UserRegistrationActivity.this);
                                dialogBuilder.setTitle("User Registration Successfully");
                                dialogBuilder.setMessage(String.format("Your userId: %s", res.getCustomerId()));
                                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                                dialogBuilder.show();
                            } else {
                                Toast.makeText(UserRegistrationActivity.this, "Username is exist! Try different username...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(UserRegistrationActivity.this, "Password and re-enter password do not matched!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                // log the error
                Log.e("User_Registration_Activity", e.getMessage());
                Toast.makeText(UserRegistrationActivity.this, "Something Wrong...", Toast.LENGTH_LONG).show();
            }
        });
    }

    // handle the back button actions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(UserRegistrationActivity.this, "Redirect to Login page.", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}