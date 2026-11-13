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

    /**
     * Constructs the databaseInitializer
     *
     * @param context context of App
     * @param database database that belongs the App
     */
    public DatabaseInitializer(
            Context context,
            AppDatabase database) {
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /**
     * Checks if the database has already been created and if not it
     * initializes it
     * @param onComplete log that runs if the database has already been created
     *                   or if the creation was successfull.
     * @param onError log that runs if something went wrong
     */
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
                DatabaseCreator.readCsvToRoom(context, database);

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