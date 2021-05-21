package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NormalService extends Service {

    private boolean isDestroyed;

    public NormalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting service...", Toast.LENGTH_SHORT).show();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override public void run() {
                //background task
                try{
                    for (int i = 0; i < 5; i++) {
                        if (isDestroyed) {
                            break;
                        }
                        Log.d("TAG_", i + " => " + String.valueOf(System.currentTimeMillis()));
                        showResult(String.valueOf(System.currentTimeMillis()));
                        Thread.sleep(5000);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override public void run() {
                        //update ui
                        showResult("All done");
                        stopSelf();
                    }
                });
            }
        });
        return START_STICKY;
    }

    private void showResult(String result) {
        Intent intent = new Intent("broadcast");
        intent.putExtra("msg", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        Toast.makeText(this, "Stopping service...", Toast.LENGTH_SHORT).show();
    }
}