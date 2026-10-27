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
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "species_id")
    private Long speciesId;

    public AlternateSpeciesName(Long id, String name, Long speciesId) {
        this.id = id;
        this.name = name;
        this.speciesId = speciesId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getSpeciesId() {
        return speciesId;
    }
}
