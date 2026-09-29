package com.smartpantry.manager.util;

import com.smartpantry.manager.database.entity.Ingredient;
import com.smartpantry.manager.database.entity.RecipeIngredient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientMatcher {

    // Collapse whitespace and apply plural→singular rules so "Tomatoes" == "tomato"
    public static String normalize(String name) {
        if (name == null) return "";
        // Collapse internal whitespace (e.g. "olive  oil" -> "olive oil")
        String n = name.trim().toLowerCase().replaceAll("\\s+", " ");
        // Apply plural→singular rules on the last word only for multi-word names
        int lastSpace = n.lastIndexOf(' ');
        String prefix = lastSpace >= 0 ? n.substring(0, lastSpace + 1) : "";
        String word = lastSpace >= 0 ? n.substring(lastSpace + 1) : n;

        if (word.endsWith("ies") && word.length() > 3) {
            word = word.substring(0, word.length() - 3) + "y"; // berries->berry
        } else if (word.endsWith("ves") && word.length() > 3) {
            word = word.substring(0, word.length() - 3) + "f"; // loaves->loaf
        } else if (word.endsWith("es") && word.length() > 3) {
            word = word.substring(0, word.length() - 1); // tomatoes->tomato
        } else if (word.endsWith("s") && word.length() > 2 && !word.endsWith("ss")) {
            word = word.substring(0, word.length() - 1); // eggs->egg, carrots->carrot
        }
        return prefix + word;
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
