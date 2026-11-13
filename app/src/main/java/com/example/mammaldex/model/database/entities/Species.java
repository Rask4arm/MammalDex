package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


/**
 * This is the table in the database for species
 */
@Entity(
        tableName = "species",
        indices = {@Index(value = {"id"}, unique = true)}
)
public class Species {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "sci_name")
    private String sciName;
    @ColumnInfo(name = "taxonomic_genus")
    private String taxonomicGenus;
    @ColumnInfo(name = "taxonomic_family")
    private String taxonomicFamily;
    @ColumnInfo(name = "taxonomic_order")
    private String taxonomicOrder;
    @ColumnInfo(name = "wiki_link")
    private String wikiLink;
    @ColumnInfo(name = "image_path")
    private String imagePath;
    @ColumnInfo(name = "seen")
    private int seen;
    @ColumnInfo(name = "updated_at")
    private Long updatedAt;
    @ColumnInfo(name = "sync_status")
    private String syncStatus;

    public Species(Long id, String name, String sciName, String taxonomicGenus, String taxonomicFamily, String taxonomicOrder, String imagePath, String wikiLink){
        this.id = id;
        this.name = name;
        this.sciName = sciName;
        this.taxonomicGenus = taxonomicGenus;
        this.taxonomicFamily = taxonomicFamily;
        this.taxonomicOrder = taxonomicOrder;
        this.imagePath = imagePath;
        this.wikiLink = wikiLink;
        this.seen = 0;
    }


    //Getter and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSciName() {
        return sciName;
    }

    public String getTaxonomicGenus() {
        return taxonomicGenus;
    }

    public String getTaxonomicFamily() {
        return taxonomicFamily;
    }

    public String getTaxonomicOrder() {
        return taxonomicOrder;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
}
