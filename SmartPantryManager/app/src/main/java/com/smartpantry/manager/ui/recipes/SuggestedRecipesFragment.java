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
    private RecipesAdapter almostThereAdapter;
    private AppDatabase db;
    private TextView tvEmptyState;
    private TextView tvSuggestedHeader;
    private TextView tvAlmostThereHeader;
    private RecyclerView recyclerAlmostThere;

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

        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        tvSuggestedHeader = view.findViewById(R.id.tv_suggested_header);
        tvAlmostThereHeader = view.findViewById(R.id.tv_almost_there_header);
        recyclerAlmostThere = view.findViewById(R.id.recycler_almost_there);

        RecyclerView recyclerRecipes = view.findViewById(R.id.recycler_recipes);
        recyclerRecipes.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecipesAdapter(this);
        recyclerRecipes.setAdapter(adapter);

        recyclerAlmostThere.setLayoutManager(new LinearLayoutManager(requireContext()));
        almostThereAdapter = new RecipesAdapter(this);
        recyclerAlmostThere.setAdapter(almostThereAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMatchingRecipes();
    }

    private void loadMatchingRecipes() {
        new Thread(() -> {
            List<Ingredient> pantryIngredients = db.ingredientDao().getAllIngredients();
            List<Recipe> allRecipes = db.recipeDao().getAllRecipes();
            List<RecipeWithIngredients> matchedRecipes = new ArrayList<>();
            List<RecipeWithIngredients> almostThereRecipes = new ArrayList<>();

            for (Recipe recipe : allRecipes) {
                List<RecipeIngredient> recipeIngredients =
                        db.recipeIngredientDao().getIngredientsForRecipe(recipe.id);
                if (IngredientMatcher.recipeMatchesPantry(recipeIngredients, pantryIngredients)) {
                    matchedRecipes.add(new RecipeWithIngredients(recipe, recipeIngredients));
                } else if (IngredientMatcher.countMissing(recipeIngredients, pantryIngredients) == 1) {
                    almostThereRecipes.add(new RecipeWithIngredients(recipe, recipeIngredients));
                }
            }

            requireActivity().runOnUiThread(() -> {
                adapter.setRecipes(matchedRecipes);
                almostThereAdapter.setRecipes(almostThereRecipes);

                if (matchedRecipes.isEmpty() && almostThereRecipes.isEmpty()) {
                    tvEmptyState.setVisibility(View.VISIBLE);
                    tvSuggestedHeader.setVisibility(View.GONE);
                    tvAlmostThereHeader.setVisibility(View.GONE);
                    recyclerAlmostThere.setVisibility(View.GONE);
                } else {
                    tvEmptyState.setVisibility(View.GONE);
                    tvSuggestedHeader.setVisibility(matchedRecipes.isEmpty() ? View.GONE : View.VISIBLE);
                    tvAlmostThereHeader.setVisibility(almostThereRecipes.isEmpty() ? View.GONE : View.VISIBLE);
                    recyclerAlmostThere.setVisibility(almostThereRecipes.isEmpty() ? View.GONE : View.VISIBLE);
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
