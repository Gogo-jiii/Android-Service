package com.example.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartNormalService, btnStopNormalService, btnStartBoundService, btnStopBoundService
            , btnStartForegroundService, btnStopForegroundService;
    TextView txtResultValue;
    Intent normalServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartNormalService = findViewById(R.id.btnStartNormalService);
        btnStopNormalService = findViewById(R.id.btnStopNormalService);
        btnStartBoundService = findViewById(R.id.btnStartBoundService);
        btnStopBoundService = findViewById(R.id.btnStopBoundService);
        btnStartForegroundService = findViewById(R.id.btnStartForegroundService);
        btnStopForegroundService = findViewById(R.id.btnStopForegroundService);
        txtResultValue = findViewById(R.id.txtResultValue);

        btnStartNormalService.setOnClickListener(this);
        btnStopNormalService.setOnClickListener(this);
        btnStartBoundService.setOnClickListener(this);
        btnStopBoundService.setOnClickListener(this);
        btnStartForegroundService.setOnClickListener(this);
        btnStopForegroundService.setOnClickListener(this);

        normalServiceIntent = new Intent(this, NormalService2.class);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartNormalService:
                startService(normalServiceIntent);
                break;

            case R.id.btnStopNormalService:
                stopService(normalServiceIntent);
                break;

            case R.id.btnStartBoundService:
                break;

            case R.id.btnStopBoundService:
                break;

            case R.id.btnStartForegroundService:
                break;

            case R.id.btnStopForegroundService:
                break;
        }
    }

    @Override protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("broadcast"));
    }

    @Override protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            txtResultValue.setText(msg);
        }
    };
}