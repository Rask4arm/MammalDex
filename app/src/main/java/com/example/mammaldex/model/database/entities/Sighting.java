package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This is the table in the database for sightings
 */
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

    @ColumnInfo(name = "updated_at")
    private Long updatedAt;
    @ColumnInfo(name = "sync_status")
    private String syncStatus;

    @ColumnInfo(name = "deleted")
    private Boolean deleted;

    /**
     * Constructs the Sighting
     *
     * @param speciesId Long speciesId
     * @param latitude double latitude where sighting was
     * @param longitude double longitude where sighting was
     */
    public Sighting(Long speciesId, double latitude, double longitude) {
        this.speciesId = speciesId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deleted = false;
    }

    //Getters and Setters
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

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}


