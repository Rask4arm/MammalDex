package com.example.mammaldex.model.database.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncWorker extends Worker {

    public SyncWorker(@NonNull Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        // TODO : Call Sync Class here

        return Result.success();
    }
}
