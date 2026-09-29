package com.smartpantry.manager.ui.pantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartpantry.manager.R;
import com.smartpantry.manager.database.AppDatabase;
import com.smartpantry.manager.database.entity.Ingredient;

public class PantryFragment extends Fragment implements PantryAdapter.OnIngredientClickListener {

    private PantryAdapter adapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDatabase.getInstance(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_pantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new PantryAdapter(this);
        recyclerView.setAdapter(adapter);

        // Observe LiveData - Room handles background threading for LiveData queries
        db.ingredientDao().getAllIngredientsLive().observe(getViewLifecycleOwner(), ingredients -> {
            adapter.setIngredients(ingredients);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_ingredient);
        fab.setOnClickListener(v -> openAddIngredientActivity());
    }

    private void openAddIngredientActivity() {
        Intent intent = new Intent(requireContext(), AddEditIngredientActivity.class);
        startActivity(intent);
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        // Open edit activity with ingredient data
        Intent intent = new Intent(requireContext(), AddEditIngredientActivity.class);
        intent.putExtra(AddEditIngredientActivity.EXTRA_INGREDIENT_ID, ingredient.id);
        intent.putExtra(AddEditIngredientActivity.EXTRA_INGREDIENT_NAME, ingredient.name);
        intent.putExtra(AddEditIngredientActivity.EXTRA_INGREDIENT_QUANTITY, ingredient.quantity);
        intent.putExtra(AddEditIngredientActivity.EXTRA_INGREDIENT_UNIT, ingredient.unit);
        intent.putExtra(AddEditIngredientActivity.EXTRA_INGREDIENT_EXPIRY, ingredient.expiryDate);
        startActivity(intent);
    }

    @Override
    public boolean onIngredientLongClick(Ingredient ingredient) {
        showDeleteConfirmationDialog(ingredient);
        return true;
    }

    private void showDeleteConfirmationDialog(Ingredient ingredient) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_ingredient_title)
                .setMessage(getString(R.string.delete_ingredient_message, ingredient.name))
                .setPositiveButton(R.string.delete, (dialog, which) -> deleteIngredient(ingredient))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void deleteIngredient(Ingredient ingredient) {
        new Thread(() -> db.ingredientDao().delete(ingredient)).start();
    }
}
