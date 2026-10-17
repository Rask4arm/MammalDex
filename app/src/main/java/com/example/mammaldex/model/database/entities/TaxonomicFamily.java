package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "taxonomic_family",
        foreignKeys = {
                @ForeignKey(
                        entity = TaxonomicOrder.class,
                        parentColumns = "id",
                        childColumns = "order_id",
                        onDelete = ForeignKey.RESTRICT
                )
        },
        indices = {@Index(value = {"family_name"}, unique = true)}
)
public class TaxonomicFamily {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "family_name")
    public String familyName;
    @ColumnInfo(name = "order_id")
    public int orderId;

    public TaxonomicFamily(String familyName, int orderId) {
        this.familyName = familyName;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getOrderId() {
        return orderId;
    }
}

