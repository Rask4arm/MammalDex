package com.example.mammaldex.model.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "taxonomic_order",
        indices = {@Index(value = {"order_name"},
                unique = true)})
public class TaxonomicOrder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "order_name")
    public String orderName;

    public TaxonomicOrder(String orderName) {
        this.orderName = orderName;
    }

    public int getId() {
        return id;
    }

    public String getOrderName() {
        return orderName;
    }

    @Override
    public String toString() {
        return "TaxonomicOrder{id=" + id + ", name='" + orderName + "'}";
    }
}


