package com.smartpantry.manager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.smartpantry.manager.database.entity.Ingredient;
import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    LiveData<List<Ingredient>> getAllIngredientsLive();

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);
}
