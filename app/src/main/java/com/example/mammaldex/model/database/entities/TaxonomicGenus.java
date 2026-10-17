package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "taxonomic_genus",
        foreignKeys = {
                @ForeignKey(
                        entity = TaxonomicFamily.class,
                        parentColumns = "id",
                        childColumns = "family_id",
                        onDelete = ForeignKey.RESTRICT
                )
        },
        indices = {@Index(value = {"genus_name"}, unique = true)}
)
public class TaxonomicGenus {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "genus_name")
    public String genusName;
    @ColumnInfo(name = "family_id")
    public int familyId;

    public TaxonomicGenus(String genusName, int familyId) {
        this.genusName = genusName;
        this.familyId = familyId;
    }

    public int getId() {
        return id;
    }

    public String getGenusName() {
        return genusName;
    }

    public int getFamilyId() {
        return familyId;
    }
}


