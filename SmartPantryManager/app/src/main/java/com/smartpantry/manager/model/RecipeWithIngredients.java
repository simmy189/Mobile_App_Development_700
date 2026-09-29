package com.smartpantry.manager.model;

import com.smartpantry.manager.database.entity.Recipe;
import com.smartpantry.manager.database.entity.RecipeIngredient;
import java.util.List;

public class RecipeWithIngredients {
    public Recipe recipe;
    public List<RecipeIngredient> ingredients;

    public RecipeWithIngredients(Recipe recipe, List<RecipeIngredient> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }
}
