package com.example.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NormalServiceWithHandlerMessage extends Service {

    private Looper looper;
    private ServiceHandler serviceHandler;
    private Context context;
    private HandlerThread thread;
    private boolean isDestroyed;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public void onCreate() {
        thread = new HandlerThread("normal",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        looper = thread.getLooper();
        serviceHandler = new ServiceHandler(looper);
        context = this;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "Service started");
        Toast.makeText(this, "Starting service...", Toast.LENGTH_SHORT).show();

        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;
        serviceHandler.sendMessage(message);

        return START_STICKY;
    }

    @Override public void onDestroy() {
        isDestroyed = true;
        Log.d("TAG", "Service destroyed");
        Toast.makeText(this, "Service destroyed.", Toast.LENGTH_SHORT).show();
    }

    private class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override public void handleMessage(@NonNull Message msg) {
            try {
                //do something...
                for (int i = 0; i < 15; i++) {
                    if (isDestroyed) {
                        break;
                    }
                    Log.d("TAG_", i + " => " + String.valueOf(System.currentTimeMillis()));
                    showResult(String.valueOf(System.currentTimeMillis()));
                    Thread.sleep(5000);
                }

            } catch (Exception e) {
                Log.e("TAG_err", e.getMessage().toString());
                e.printStackTrace();
            }
            stopSelf(msg.arg1);
        }
    }

    private void showResult(String result) {
        Intent intent = new Intent("broadcast");
        intent.putExtra("msg", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}