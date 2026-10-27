package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "sighting",
        foreignKeys = {
                @ForeignKey(
                        entity = Species.class,
                        parentColumns = "id",
                        childColumns = "species_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("species_id")}
)
public class Sighting {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;
    @ColumnInfo(name = "species_id")
    public Long speciesId;
    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;

    public Sighting(Long speciesId, double latitude, double longitude) {
        this.speciesId = speciesId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public Long getSpeciesId() {
        return speciesId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}


