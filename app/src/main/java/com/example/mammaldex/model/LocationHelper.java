package com.example.mammaldex.model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

/**
 * Handles retrieving the device's current location using Google's
 * Fused Location Provider.
 *
 * Location retrieval is performed only when requested by the user.
 * The class checks the user's location preference and Android
 * location permissions before attempting to retrieve coordinates.
 */
public class LocationHelper {

    private final Context context;
    private final FusedLocationProviderClient locationClient;

    /**
     * Provides callbacks for successful and unsuccessful location requests.
     */
    public interface LocationCallback {

        /**
         * Called when the device's current location is successfully retrieved.
         *
         * @param latitude the device's latitude coordinate
         * @param longitude the device's longitude coordinate
         */
        void onLocationReceived(double latitude, double longitude);

        /**
         * Called when the location request cannot be completed.
         *
         * @param error a message describing the location retrieval error
         */
        void onLocationError(String error);
    }

    /**
     * Checks whether the device's location settings are enabled.
     *
     * @return a Task containing the result of the settings check
     */
    public Task<LocationSettingsResponse> checkLocationSettings() {

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setMinUpdateIntervalMillis(2000) // Fastest allowed interval
                .build();


        LocationSettingsRequest settingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest)
                        .setAlwaysShow(true)
                        .build();

        SettingsClient settingsClient =
                LocationServices.getSettingsClient(context);

        return settingsClient.checkLocationSettings(settingsRequest);
    }
    /**
     * Creates a LocationHelper and initializes the Fused Location Provider.
     *
     * @param context the context used to initialize the location provider
     */
    public LocationHelper(Context context) {
        this.context = context.getApplicationContext();
        locationClient = LocationServices.getFusedLocationProviderClient(this.context);
    }

    /**
     * Requests the device's current location.
     *
     * Before retrieving the location, this method checks whether location
     * retrieval is enabled in the application settings and whether the
     * application has been granted fine or coarse location permission.
     *
     * The result is returned through the provided LocationCallback.
     *
     * @param callback the callback that receives the coordinates or an error
     */
    @SuppressLint("Missing Permission")
    public void getCurrentLocation(LocationCallback callback) {

        //Check whether location is enabled in app settings
        if (!LocationPreferences.isLocationEnabled(context)) {
            callback.onLocationError("Location preference is disabled");
            return;
        }

        //Check android permissions
        boolean finePermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        boolean coarsePermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        if (!finePermission && !coarsePermission) {
            callback.onLocationError("Location permission has not been granted");
            return;
        }

        //Retrieve current device location
        CancellationTokenSource cancellationTokenSource =
                new CancellationTokenSource();

        try {
            locationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.getToken()).addOnSuccessListener(location -> {

                if (location != null) {
                    callback.onLocationReceived(
                            location.getLatitude(),
                            location.getLongitude()
                    );
                } else {
                    callback.onLocationError("Unable to retrieve current lcoation");
                }

            }).addOnFailureListener(exception -> {
                callback.onLocationError(exception.getMessage());
            });

        } catch (SecurityException exception) {
            callback.onLocationError("Location permission was denied");
        }
    }
}
