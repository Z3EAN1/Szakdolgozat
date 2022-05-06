package com.example.szakdolgozat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StoreActivity extends AppCompatActivity {
    private static final String LOG_TAG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void open_aldi(View view) {
        Intent intent = new Intent(this, StoreAldiActivity.class);
        startActivity(intent);
    }

    public void open_lidl(View view) {
        Intent intent = new Intent(this, StoreLidlActivity.class);
        startActivity(intent);
    }

    public void open_tesco(View view) {
        Intent intent = new Intent(this, StoreTescoActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.store_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                Toast.makeText(this, "Stikeez list!", Toast.LENGTH_SHORT).show();
                Intent intents = new Intent(this, ShopActivity.class);
                startActivity(intents);
                return true;
            case R.id.profile:
                Toast.makeText(this, "Profile tab!", Toast.LENGTH_SHORT).show();
                Intent intentss = new Intent(this, UserProfileActivity.class);
                startActivity(intentss);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}