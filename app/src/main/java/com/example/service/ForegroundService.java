package com.example.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForegroundService extends Service {

    private Context context;
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "100";
    private boolean isDestroyed = false;

    public ForegroundService() {
    }

    @Override public void onCreate() {
        super.onCreate();
        context = this;
        startForeground(NOTIFICATION_ID, showNotification("This is content."));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(context, "Starting service...", Toast.LENGTH_SHORT).show();
        doTask();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doTask() {
        final int[] data = new int[1];
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override public void run() {
                //task
                for (int i = 0; i < 100; i++) {
                    if (isDestroyed) {
                        break;
                    }
                    data[0] = i;
                    try {
                        handler.post(new Runnable() {
                            @Override public void run() {
                                //update ui
                                updateNotification(String.valueOf(data[0]));
                            }
                        });
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateNotification(String data) {
        Notification notification = showNotification(data);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private Notification showNotification(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, "Foreground Notification",
                            NotificationManager.IMPORTANCE_HIGH));
        }

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(content)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_android)
                .build();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        Toast.makeText(context, "Stopping service...", Toast.LENGTH_SHORT).show();
    }
}