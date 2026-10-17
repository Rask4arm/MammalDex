package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "species",
        foreignKeys = {
                @ForeignKey(
                        entity = TaxonomicGenus.class,
                        parentColumns = "id",
                        childColumns = "genus_id",
                        onDelete = ForeignKey.RESTRICT
                )
        },
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
    @ColumnInfo(name = "genus_id")
    private Long genusId;
    @ColumnInfo(name = "wiki_link")
    private String wikiLink;
    @ColumnInfo(name = "image_path")
    private String imagePath;
    @ColumnInfo(name = "seen")
    private int seen;

    public Species(Long id, String name, String sciName, Long genusId, String wikiLink){
        this.id = id;
        this.name = name;
        this.sciName = sciName;
        this.genusId = genusId;
        this.wikiLink = wikiLink;
        this.seen = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSciName() {
        return sciName;
    }

    public Long getGenusId() {
        return genusId;
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setGenusId(Long genusId) {
        this.genusId = genusId;
    }
}
