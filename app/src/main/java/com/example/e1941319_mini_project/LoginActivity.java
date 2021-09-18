package com.example.e1941319_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e1941319_mini_project.dto.LoginDTO;
import com.example.e1941319_mini_project.dto.LoginResponseDTO;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    // references to controls on the layout
    Button btn_login;
    TextInputEditText txt_username, txt_password;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize controls
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        db = new DBAdapter(LoginActivity.this);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        LoginResponseDTO result = db.checkUsernameAndPassword(new LoginDTO(username, password));
                        System.out.println(result);
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
                                    intent = new Intent(LoginActivity.this, UserActivity.class);
                                    startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, String.format("%s Login Failed! Try Again.", username), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    // log the error
                    Log.e("LoginActivity", e.getMessage());
                    Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}