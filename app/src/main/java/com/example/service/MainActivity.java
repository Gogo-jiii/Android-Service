package com.example.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartNormalService, btnStopNormalService, btnStartBoundService, btnStopBoundService
            , btnStartForegroundService, btnStopForegroundService;
    TextView txtResultValue;
    Intent normalServiceIntent;
    Intent boundedServiceIntent;
    BoundedService boundedService;
    private boolean isServiceBounded = false;

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

        normalServiceIntent = new Intent(this, NormalService.class);
        boundedServiceIntent = new Intent(this, BoundedService.class);

        bindService(boundedServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
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
                if (isServiceBounded) {
                    int num = boundedService.data;
                    Toast.makeText(boundedService, "Num: " + num, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnStopBoundService:
                if(isServiceBounded){
                    unbindService(serviceConnection);
                    isServiceBounded = false;
                }
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


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            BoundedService.LocalBinder binder = (BoundedService.LocalBinder) service;
            boundedService = binder.getService();
            isServiceBounded = true;
        }

        @Override public void onServiceDisconnected(ComponentName name) {
            isServiceBounded = false;
        }
    };
}