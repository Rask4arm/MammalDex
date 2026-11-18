package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This is the table in the database for the alternate names for the different species
 */
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

    /**
     * TODO : Add Javadoc
     * @param name This should be a String of the alternate name for the species that is specified in the speciesId
     * @param speciesId Long speciesId
     */
    public AlternateSpeciesName(String name, Long speciesId) {
        this.name = name;
        this.speciesId = speciesId;
    }

    // Getter and setter methods

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Long getSpeciesId() {
        return speciesId;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
