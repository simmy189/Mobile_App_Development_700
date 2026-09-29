package com.smartpantry.manager.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.smartpantry.manager.database.entity.RecipeIngredient;
import java.util.List;

@Dao
public interface RecipeIngredientDao {
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);

    @Insert
    void insert(RecipeIngredient recipeIngredient);
}
