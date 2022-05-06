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
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {
    private FirebaseUser user;
    private static final String LOG_TAG = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);

        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void add(View view) {
        Intent intent = new Intent(this, ProductAddActivity.class);
        startActivity(intent);
    }

public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.profile_menu, menu);
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
            case R.id.logout:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intentss = new Intent(this, MainActivity.class);
                startActivity(intentss);
                return true;
            case R.id.store:
                Toast.makeText(this, "Store list!", Toast.LENGTH_SHORT).show();
                Intent intent_3 = new Intent(this, StoreActivity.class);
                startActivity(intent_3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}