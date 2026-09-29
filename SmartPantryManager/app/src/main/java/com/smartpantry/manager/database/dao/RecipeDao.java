package com.smartpantry.manager.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.smartpantry.manager.database.entity.Recipe;
import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name ASC")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipeById(int id);

    @Insert
    long insert(Recipe recipe);

    @Query("SELECT COUNT(*) FROM recipes")
    int getRecipeCount();
}
