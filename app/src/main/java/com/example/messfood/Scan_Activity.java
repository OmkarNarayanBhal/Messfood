package com.example.messfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Scan_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.scan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(),Order_Activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
}