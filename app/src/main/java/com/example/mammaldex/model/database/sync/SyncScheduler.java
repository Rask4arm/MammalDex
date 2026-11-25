package com.example.mammaldex.model.database.sync;


import android.content.Context;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SyncScheduler {
    public static void scheduleSync(Context context) {

        //Constraint that sets it so code can only run when connected to network.
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //One non-repeating request to run SyncWorker when constraint is met.
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints)
                .build();

        //Puts the request in the que with the name "FirebaseSync".
        //If there is already something in que with that name it keeps the original.
        WorkManager.getInstance(context)
                .enqueueUniqueWork("FirebaseSync", ExistingWorkPolicy.KEEP, request);
    }

    public static void schedulePeriodicSync(Context context) {

        //Constraint that sets it so code can only run when connected to network.
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //Periodic request to run SyncWorker when constraint is met.
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        //Puts the request in the que with the name "FirebaseSync".
        //If there is already something in que with that name it keeps the original.
        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("PeriodicFirebaseSync", ExistingPeriodicWorkPolicy.KEEP, request);
    }
}
