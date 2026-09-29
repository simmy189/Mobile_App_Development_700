package com.smartpantry.manager.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double quantity;
    public String unit;
    public String expiryDate; // nullable, format: yyyy-MM-dd

    public Ingredient() {}

    @Ignore
    public Ingredient(String name, double quantity, String unit, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }
}
