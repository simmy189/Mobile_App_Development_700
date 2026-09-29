package com.smartpantry.manager.ui.recipes;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.smartpantry.manager.R;
import com.smartpantry.manager.database.AppDatabase;
import com.smartpantry.manager.database.entity.Recipe;
import com.smartpantry.manager.database.entity.RecipeIngredient;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_recipe_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.recipe_detail_title);
        }

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
        if (recipeId == -1) {
            finish();
            return;
        }

        loadRecipeDetails(recipeId);
    }

    private void loadRecipeDetails(int recipeId) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Recipe recipe = db.recipeDao().getRecipeById(recipeId);
            List<RecipeIngredient> ingredients =
                    db.recipeIngredientDao().getIngredientsForRecipe(recipeId);

            runOnUiThread(() -> {
                if (recipe == null) {
                    finish();
                    return;
                }
                populateUI(recipe, ingredients);
            });
        }).start();
    }

    private void populateUI(Recipe recipe, List<RecipeIngredient> ingredients) {
        // Recipe name
        TextView tvRecipeName = findViewById(R.id.tv_detail_recipe_name);
        tvRecipeName.setText(recipe.name);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipe.name);
        }

        // Ingredients list
        LinearLayout ingredientsContainer = findViewById(R.id.container_ingredients);
        ingredientsContainer.removeAllViews();

        if (ingredients != null) {
            for (RecipeIngredient ingredient : ingredients) {
                TextView tvIngredient = new TextView(this);
                tvIngredient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvIngredient.setTextColor(getResources().getColor(R.color.colorOnSurface, getTheme()));

                String quantityStr;
                if (ingredient.requiredQuantity == Math.floor(ingredient.requiredQuantity)) {
                    quantityStr = String.valueOf((int) ingredient.requiredQuantity);
                } else {
                    quantityStr = String.valueOf(ingredient.requiredQuantity);
                }
                tvIngredient.setText("• " + ingredient.ingredientName + " — " +
                        quantityStr + " " + ingredient.unit);

                int paddingPx = (int) (4 * getResources().getDisplayMetrics().density);
                tvIngredient.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
                ingredientsContainer.addView(tvIngredient);
            }
        }

        // Preparation steps
        TextView tvPreparation = findViewById(R.id.tv_preparation_steps);
        tvPreparation.setText(recipe.preparationSteps);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
