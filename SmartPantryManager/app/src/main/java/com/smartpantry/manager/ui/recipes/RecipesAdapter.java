package com.smartpantry.manager.ui.recipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartpantry.manager.R;
import com.smartpantry.manager.model.RecipeWithIngredients;
import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(RecipeWithIngredients recipeWithIngredients);
    }

    private List<RecipeWithIngredients> recipes = new ArrayList<>();
    private OnRecipeClickListener listener;

    public RecipesAdapter(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<RecipeWithIngredients> recipes) {
        this.recipes = recipes != null ? recipes : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeWithIngredients item = recipes.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRecipeName;
        private final TextView tvIngredientCount;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvIngredientCount = itemView.findViewById(R.id.tv_ingredient_count);
        }

        void bind(RecipeWithIngredients item, OnRecipeClickListener listener) {
            tvRecipeName.setText(item.recipe.name);
            int count = item.ingredients != null ? item.ingredients.size() : 0;
            tvIngredientCount.setText(itemView.getContext().getResources()
                    .getQuantityString(R.plurals.ingredient_count, count, count));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRecipeClick(item);
                }
            });
        }
    }
}
