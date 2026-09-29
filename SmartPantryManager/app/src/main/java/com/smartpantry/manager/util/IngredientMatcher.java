package com.smartpantry.manager.util;

import com.smartpantry.manager.database.entity.Ingredient;
import com.smartpantry.manager.database.entity.RecipeIngredient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientMatcher {

    public static String normalize(String name) {
        if (name == null) return "";
        String n = name.trim().toLowerCase();
        // Handle plural forms
        if (n.endsWith("ies") && n.length() > 3) {
            n = n.substring(0, n.length() - 3) + "y"; // berries->berry
        } else if (n.endsWith("ves") && n.length() > 3) {
            n = n.substring(0, n.length() - 3) + "f"; // loaves->loaf
        } else if (n.endsWith("es") && n.length() > 3) {
            n = n.substring(0, n.length() - 1); // tomatoes->tomato, potatoes->potato
        } else if (n.endsWith("s") && n.length() > 2 && !n.endsWith("ss")) {
            n = n.substring(0, n.length() - 1); // eggs->egg, carrots->carrot
        }
        return n;
    }

    public static boolean recipeMatchesPantry(List<RecipeIngredient> required, List<Ingredient> pantry) {
        Map<String, Double> pantryMap = new HashMap<>();
        for (Ingredient item : pantry) {
            String key = normalize(item.name);
            double existing = pantryMap.containsKey(key) ? pantryMap.get(key) : 0.0;
            pantryMap.put(key, existing + item.quantity);
        }
        for (RecipeIngredient req : required) {
            String reqKey = normalize(req.ingredientName);
            if (!pantryMap.containsKey(reqKey)) return false;
            if (pantryMap.get(reqKey) < req.requiredQuantity) return false;
        }
        return true;
    }

    public static int countMissing(List<RecipeIngredient> required, List<Ingredient> pantry) {
        Map<String, Double> pantryMap = new HashMap<>();
        for (Ingredient item : pantry) {
            String key = normalize(item.name);
            double existing = pantryMap.containsKey(key) ? pantryMap.get(key) : 0.0;
            pantryMap.put(key, existing + item.quantity);
        }
        int missing = 0;
        for (RecipeIngredient req : required) {
            String reqKey = normalize(req.ingredientName);
            if (!pantryMap.containsKey(reqKey) || pantryMap.get(reqKey) < req.requiredQuantity) {
                missing++;
            }
        }
        return missing;
    }
}
