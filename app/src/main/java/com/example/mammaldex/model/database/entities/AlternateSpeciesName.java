package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "alternate_species_name",
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
public class AlternateSpeciesName {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "species_id")
    private int speciesId;

    public AlternateSpeciesName(int id, String name, int speciesId) {
        this.id = id;
        this.name = name;
        this.speciesId = speciesId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpeciesId() {
        return speciesId;
    }
}
