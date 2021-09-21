package com.example.e1941319_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e1941319_mini_project.model.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PackageSingleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_single_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView packageId = findViewById(R.id.packageId);
        TextView customerId = findViewById(R.id.customerId);
        TextView deliveryAddress = findViewById(R.id.deliveryAddress);
        TextView description = findViewById(R.id.description);
        TextView currentStatusLabel = findViewById(R.id.current_states_label);
        TextView currentStatus = findViewById(R.id.status_label);
        AutoCompleteTextView statusSelect = findViewById(R.id.status);
        LinearLayout status_view = findViewById(R.id.status_view);
        Button btn_update = findViewById(R.id.btn_update);

        // auto complete text view editable disable
        statusSelect.setKeyListener(null);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            packageId.setText(extras.getString("packageId"));
            customerId.setText(extras.getString("customerId"));
            deliveryAddress.setText(extras.getString("deliveryAddress"));
            description.setText(extras.getString("description"));
            StatusType crsts = (StatusType) extras.get("currentStatus");
            UserType loginUserType = (UserType) extras.get("loginUserType");
            StatusType[] statusArray = (StatusType[]) extras.get("statusArray");
            List<Status> statusList = (List<Status>) intent.getSerializableExtra("statusList");

            // sort status list
            Collections.sort(statusList, (status, status2) -> {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return Objects.requireNonNull(dateFormat.parse(status.getDate())).compareTo(dateFormat.parse(status2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            });

            // add status to linear layout
            for (Status status : statusList) {
                View view2 = LayoutInflater.from(PackageSingleViewActivity.this).inflate(R.layout.package_status, null, false);
                TextView pkg_status_date = view2.findViewById(R.id.pkg_status_date);
                TextView pkg_status_label = view2.findViewById(R.id.pkg_status_label);

                pkg_status_date.setText(status.getDate());
                pkg_status_label.setText(status.getStatus().toString());

                status_view.addView(view2);
            }

            // check the logged user type
            if (loginUserType == UserType.USER) {
                btn_update.setVisibility(View.GONE);
                currentStatus.setVisibility(View.VISIBLE);
                statusSelect.setVisibility(View.GONE);

                currentStatus.setText(crsts.toString());
            } else {
                btn_update.setVisibility(View.VISIBLE);

                if (crsts.equals(StatusType.DELIVERED)) {
                    currentStatusLabel.setVisibility(View.GONE);
                    currentStatus.setVisibility(View.GONE);
                    statusSelect.setVisibility(View.GONE);
                } else {
                    currentStatusLabel.setVisibility(View.VISIBLE);
                    currentStatus.setVisibility(View.GONE);
                    statusSelect.setVisibility(View.VISIBLE);
                    ArrayAdapter<StatusType> adapter = new ArrayAdapter<>(PackageSingleViewActivity.this, android.R.layout.simple_list_item_1, statusArray);

                    statusSelect.setAdapter(adapter);
                    statusSelect.setText(crsts.toString(), false);

                    btn_update.setOnClickListener(view -> {

                    });
                }
            }
        }
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
                Toast.makeText(PackageSingleViewActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PackageSingleViewActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case android.R.id.home: {
                Toast.makeText(PackageSingleViewActivity.this, "Redirect to dashboard...", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}