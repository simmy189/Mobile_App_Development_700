package com.smartpantry.manager.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.smartpantry.manager.database.dao.IngredientDao;
import com.smartpantry.manager.database.dao.RecipeDao;
import com.smartpantry.manager.database.dao.RecipeIngredientDao;
import com.smartpantry.manager.database.entity.Ingredient;
import com.smartpantry.manager.database.entity.Recipe;
import com.smartpantry.manager.database.entity.RecipeIngredient;

@Database(entities = {Ingredient.class, Recipe.class, RecipeIngredient.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract IngredientDao ingredientDao();
    public abstract RecipeDao recipeDao();
    public abstract RecipeIngredientDao recipeIngredientDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "smart_pantry_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
