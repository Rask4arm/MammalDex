package com.example.mammaldex.model.database;

import android.content.Context;

public class ImagePathMatcher {
    public static void setImagePath(Context context, AppDatabase database) {
        database.speciesDao().updateImagePath("Ornithorhynchus_anatinus", "/Users/viggoringsby/AndroidStudioProjects/MammalDex/app/src/main/res/drawable/Ornithorhynchus_anatinus");
    }
}
