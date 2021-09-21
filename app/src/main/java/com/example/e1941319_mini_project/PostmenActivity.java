package com.example.e1941319_mini_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PostmenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postmen);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        DBAdapter db = new DBAdapter();

        db.getAllPackages("Postmen Activity", null).observe(PostmenActivity.this, res -> {
            if (res != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PostmenActivity.this, android.R.layout.simple_list_item_1, res.getPackageIdList());
                getSupportFragmentManager().beginTransaction().add(R.id.container, RecyclerViewFragment.newInstance(PostmenActivity.this, res.getPackageData(), UserType.POSTMEN, new StatusType[]{StatusType.IN_TRANSIT, StatusType.DELIVERED})).
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
            Toast.makeText(PostmenActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostmenActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}