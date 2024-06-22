package com.example.elearning;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.IfOfflineActivityBinding;

public class IfOfflineActivity extends AppCompatActivity {
    IfOfflineActivityBinding binding ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = IfOfflineActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reloadClick();
    }
    private void reloadClick()
    {
        binding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(binding.getRoot());
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null || networkInfo.isConnectedOrConnecting()) {
                    startActivity(new Intent(IfOfflineActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(IfOfflineActivity.this, IfOfflineActivity.class));
                    finish();
                }
            }
        });
    }
}
