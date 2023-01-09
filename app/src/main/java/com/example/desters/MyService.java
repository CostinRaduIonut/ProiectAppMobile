package com.example.desters;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private volatile boolean stopThread = false;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopThread = false;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (!stopThread)
                    try {
                        i=i+1;
                        Thread.sleep(60000);
                        Log.d(TAG, i+ " min au trecut");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };

        Thread service_task = new Thread(runnable);
        service_task.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopThread = true;
//        Intent i = new Intent();
//        i.setClass(this, MainActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "onRemove");
        stopSelf();

        super.onTaskRemoved(rootIntent);
    }
}