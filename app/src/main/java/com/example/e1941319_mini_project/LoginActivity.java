package com.example.e1941319_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e1941319_mini_project.dto.LoginAndRegisterDTO;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // references to controls on the layout
        Button btn_login = (Button) findViewById(R.id.btn_login);
        EditText txt_username = (EditText) findViewById(R.id.txt_username);
        EditText txt_password = (EditText) findViewById(R.id.txt_password);
        TextView btn_register = (TextView) findViewById(R.id.btn_register);

        DBAdapter db = new DBAdapter();

        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, UserRegistrationActivity.class);
            startActivity(intent);
        });

        btn_login.setOnClickListener(view -> {
            try {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // check username and password fields are empty or not
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Username and Password fields are required!", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "Username field is required!", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Password field is required!", Toast.LENGTH_LONG).show();
                } else {
                    db.login(new LoginAndRegisterDTO(username, password)).observe(LoginActivity.this, result -> {
                        if (result.getStatus()) {
                            Toast.makeText(LoginActivity.this, String.format("%s Login Successfully.", username), Toast.LENGTH_SHORT).show();
                            Intent intent;
                            switch (result.getUserType()) {
                                case STAFF:
                                    intent = new Intent(LoginActivity.this, StaffActivity.class);
                                    startActivity(intent);
                                    break;
                                case POSTMEN:
                                    intent = new Intent(LoginActivity.this, PostmenActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    Log.e("Login_Activity", result.getCustomerId());
                                    intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.putExtra("customerId", result.getCustomerId());
                                    startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed! Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                // log the error
                Log.e("LoginActivity", e.getMessage());
                Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_LONG).show();
            }
        });
    }
}