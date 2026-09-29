package com.smartpantry.manager.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String preparationSteps;

    public Recipe() {}

    @Ignore
    public Recipe(String name, String preparationSteps) {
        this.name = name;
        this.preparationSteps = preparationSteps;
    }
}
