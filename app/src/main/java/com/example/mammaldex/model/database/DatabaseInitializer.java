package com.example.mammaldex.model.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.example.mammaldex.model.database.local.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
public class DatabaseInitializer {

    private final Context context;
    private final AppDatabase database;
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public DatabaseInitializer(
            Context context,
            AppDatabase database) {
        this.context = context.getApplicationContext();
        this.database = database;
    }

    public void initialize(Runnable onComplete,
                           Consumer<Exception> onError) {
        executor.execute(() -> {
            SharedPreferences prefs =
                    context.getSharedPreferences(
                            "database_setup",
                            Context.MODE_PRIVATE);

            boolean imported =
                    prefs.getBoolean("csv_import_complete", false);

            if (imported) {
                new Handler(Looper.getMainLooper())
                        .post(onComplete);
                return;
            }

            try {
                // Replace CsvImporter with your actual class.
                DatabaseCreator.readCsvToRoom(context, database);

                // Save the flag only after a successful import.
                prefs.edit()
                        .putBoolean("csv_import_complete", true)
                        .commit();

                new Handler(Looper.getMainLooper())
                        .post(onComplete);

            } catch (Exception e) {
                new Handler(Looper.getMainLooper())
                        .post(() -> onError.accept(e));
            }
        });
    }
}