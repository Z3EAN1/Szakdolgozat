package com.example.szakdolgozat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreTescoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StoreAdapter mAdapter;
    private WebView googlewebview;

    private DatabaseReference mDatabaseRef;
    private List<Product> mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_tesco);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeMaps();



        mProduct = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        Query query = FirebaseDatabase.getInstance().getReference("uploads")
                .orderByChild("store")
                .equalTo("Tesco");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    mProduct.add(product);
                }

                mAdapter = new StoreAdapter(StoreTescoActivity.this, mProduct);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StoreTescoActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeMaps() {

        String iframe = "<iframe src=https://maps.google.com/maps?q=tesco&t=&z=9&ie=UTF8&iwloc=&output=embed width=600 height=450 frameborder=0 {overflow:hidden;background:none!important;height:600px;width:450px;}style=border:0</iframe>";
        googlewebview = (WebView) findViewById(R.id.googlemaps);
        googlewebview.getSettings().setJavaScriptEnabled(true);
        googlewebview.loadData(iframe, "text/html", "utf-8");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.store_profile_menu, menu);
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
            case R.id.store:
                Toast.makeText(this, "Store list!", Toast.LENGTH_SHORT).show();
                Intent intent_2 = new Intent(this, StoreActivity.class);
                startActivity(intent_2);
                return true;
            case R.id.profile:
                Toast.makeText(this, "Back to Stikeez list!", Toast.LENGTH_SHORT).show();
                Intent intentss = new Intent(this, UserProfileActivity.class);
                startActivity(intentss);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}