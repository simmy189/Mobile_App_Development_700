package com.smartpantry.manager.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartpantry.manager.R;
import com.smartpantry.manager.database.AppDatabase;
import com.smartpantry.manager.database.entity.Ingredient;
import com.smartpantry.manager.database.entity.Recipe;
import com.smartpantry.manager.database.entity.RecipeIngredient;
import com.smartpantry.manager.model.RecipeWithIngredients;
import com.smartpantry.manager.util.IngredientMatcher;
import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesFragment extends Fragment implements RecipesAdapter.OnRecipeClickListener {

    private RecipesAdapter adapter;
    private AppDatabase db;
    private TextView tvEmptyState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggested_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDatabase.getInstance(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new RecipesAdapter(this);
        recyclerView.setAdapter(adapter);

        tvEmptyState = view.findViewById(R.id.tv_empty_state);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh matched recipes every time this screen becomes visible
        loadMatchingRecipes();
    }

    private void loadMatchingRecipes() {
        new Thread(() -> {
            List<Ingredient> pantryIngredients = db.ingredientDao().getAllIngredients();
            List<Recipe> allRecipes = db.recipeDao().getAllRecipes();
            List<RecipeWithIngredients> matchedRecipes = new ArrayList<>();

            for (Recipe recipe : allRecipes) {
                List<RecipeIngredient> recipeIngredients =
                        db.recipeIngredientDao().getIngredientsForRecipe(recipe.id);
                if (IngredientMatcher.recipeMatchesPantry(recipeIngredients, pantryIngredients)) {
                    matchedRecipes.add(new RecipeWithIngredients(recipe, recipeIngredients));
                }
            }

            requireActivity().runOnUiThread(() -> {
                adapter.setRecipes(matchedRecipes);
                if (matchedRecipes.isEmpty()) {
                    tvEmptyState.setVisibility(View.VISIBLE);
                } else {
                    tvEmptyState.setVisibility(View.GONE);
                }
            });
        }).start();
    }

    @Override
    public void onRecipeClick(RecipeWithIngredients recipeWithIngredients) {
        Intent intent = new Intent(requireContext(), RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipeWithIngredients.recipe.id);
        startActivity(intent);
    }
}
