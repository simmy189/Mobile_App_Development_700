package com.smartpantry.manager.ui.pantry;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartpantry.manager.R;
import com.smartpantry.manager.database.entity.Ingredient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.IngredientViewHolder> {

    public interface OnIngredientClickListener {
        void onIngredientClick(Ingredient ingredient);
        boolean onIngredientLongClick(Ingredient ingredient);
    }

    private List<Ingredient> ingredients = new ArrayList<>();
    private OnIngredientClickListener listener;

    public PantryAdapter(OnIngredientClickListener listener) {
        this.listener = listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient, listener);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvQuantityUnit;
        private final TextView tvExpiry;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_ingredient_name);
            tvQuantityUnit = itemView.findViewById(R.id.tv_ingredient_quantity_unit);
            tvExpiry = itemView.findViewById(R.id.tv_ingredient_expiry);
        }

        void bind(Ingredient ingredient, OnIngredientClickListener listener) {
            tvName.setText(ingredient.name);

            // Format quantity: show as integer if whole number, decimal otherwise
            String quantityStr;
            if (ingredient.quantity == Math.floor(ingredient.quantity)) {
                quantityStr = String.valueOf((int) ingredient.quantity);
            } else {
                quantityStr = String.valueOf(ingredient.quantity);
            }
            tvQuantityUnit.setText(quantityStr + " " + ingredient.unit);

            if (!TextUtils.isEmpty(ingredient.expiryDate)) {
                tvExpiry.setText(itemView.getContext().getString(R.string.expires_label, ingredient.expiryDate));
                tvExpiry.setVisibility(View.VISIBLE);
                // Highlight in red if expiring within 7 days
                if (isExpiringSoon(ingredient.expiryDate)) {
                    tvExpiry.setTextColor(Color.parseColor("#D32F2F"));
                } else {
                    tvExpiry.setTextColor(Color.parseColor("#757575"));
                }
            } else {
                tvExpiry.setText(R.string.no_expiry);
                tvExpiry.setTextColor(Color.parseColor("#757575"));
                tvExpiry.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onIngredientClick(ingredient);
            });

            itemView.setOnLongClickListener(v -> {
                if (listener != null) return listener.onIngredientLongClick(ingredient);
                return false;
            });
        }

        private boolean isExpiringSoon(String expiryDate) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date expiry = sdf.parse(expiryDate);
                if (expiry == null) return false;
                long diffMs = expiry.getTime() - new Date().getTime();
                return diffMs >= 0 && TimeUnit.MILLISECONDS.toDays(diffMs) <= 7;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}
