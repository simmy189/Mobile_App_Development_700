package com.smartpantry.manager.database;

import android.content.Context;

import com.smartpantry.manager.database.dao.RecipeDao;
import com.smartpantry.manager.database.dao.RecipeIngredientDao;
import com.smartpantry.manager.database.entity.Recipe;
import com.smartpantry.manager.database.entity.RecipeIngredient;

public class DatabaseSeeder {

    public static void seedIfEmpty(Context context) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            RecipeDao recipeDao = db.recipeDao();
            RecipeIngredientDao recipeIngredientDao = db.recipeIngredientDao();

            if (recipeDao.getRecipeCount() > 0) {
                return; // Already seeded
            }

            // 1. Scrambled Eggs
            long id1 = recipeDao.insert(new Recipe("Scrambled Eggs",
                    "1. Crack eggs into a bowl and add milk. Whisk well.\n" +
                    "2. Melt butter in a non-stick pan over medium-low heat.\n" +
                    "3. Pour in the egg mixture and stir gently with a spatula.\n" +
                    "4. Cook until eggs are just set but still slightly glossy.\n" +
                    "5. Season with salt and serve immediately."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id1, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id1, "butter", 1, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id1, "milk", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id1, "salt", 1, "pinch"));

            // 2. Boiled Eggs
            long id2 = recipeDao.insert(new Recipe("Boiled Eggs",
                    "1. Place eggs in a saucepan and cover with cold water.\n" +
                    "2. Bring water to a boil over medium-high heat.\n" +
                    "3. For soft-boiled, cook 6 minutes; for hard-boiled, cook 10 minutes.\n" +
                    "4. Transfer eggs to an ice bath to stop cooking.\n" +
                    "5. Peel, season with salt, and serve."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id2, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id2, "salt", 1, "pinch"));

            // 3. Omelette
            long id3 = recipeDao.insert(new Recipe("Omelette",
                    "1. Whisk eggs in a bowl until well combined.\n" +
                    "2. Melt butter in a pan over medium heat.\n" +
                    "3. Pour in eggs and let them set at the edges.\n" +
                    "4. Sprinkle cheese over one half and fold the omelette.\n" +
                    "5. Slide onto a plate, season with salt, and serve hot."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id3, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id3, "butter", 1, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id3, "cheese", 30, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id3, "salt", 1, "pinch"));

            // 4. French Toast
            long id4 = recipeDao.insert(new Recipe("French Toast",
                    "1. Whisk eggs, milk, and sugar together in a shallow bowl.\n" +
                    "2. Dip each bread slice into the egg mixture, coating both sides.\n" +
                    "3. Melt butter in a pan over medium heat.\n" +
                    "4. Cook bread slices for 2-3 minutes per side until golden brown.\n" +
                    "5. Serve warm with your choice of toppings."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id4, "bread", 2, "slice"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id4, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id4, "milk", 60, "ml"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id4, "butter", 1, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id4, "sugar", 1, "tsp"));

            // 5. Toast with Butter
            long id5 = recipeDao.insert(new Recipe("Toast with Butter",
                    "1. Place bread slices in a toaster or under a grill.\n" +
                    "2. Toast until golden and crispy to your liking.\n" +
                    "3. Spread butter generously over the hot toast.\n" +
                    "4. Serve immediately while warm."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id5, "bread", 2, "slice"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id5, "butter", 1, "tbsp"));

            // 6. Cheese Sandwich
            long id6 = recipeDao.insert(new Recipe("Cheese Sandwich",
                    "1. Butter one side of each bread slice.\n" +
                    "2. Place cheese evenly on the unbuttered side of one slice.\n" +
                    "3. Top with the second slice, buttered side out.\n" +
                    "4. Cook in a pan over medium heat until golden on both sides.\n" +
                    "5. Cut diagonally and serve hot."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id6, "bread", 2, "slice"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id6, "cheese", 60, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id6, "butter", 1, "tbsp"));

            // 7. Garlic Bread
            long id7 = recipeDao.insert(new Recipe("Garlic Bread",
                    "1. Preheat oven to 180°C (350°F).\n" +
                    "2. Mix softened butter with minced garlic.\n" +
                    "3. Spread the garlic butter mixture on each bread slice.\n" +
                    "4. Place on a baking tray and bake for 10-12 minutes.\n" +
                    "5. Serve hot as a side dish or snack."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id7, "bread", 4, "slice"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id7, "butter", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id7, "garlic", 2, "clove"));

            // 8. Pancakes
            long id8 = recipeDao.insert(new Recipe("Pancakes",
                    "1. Mix flour, sugar, and baking powder in a bowl.\n" +
                    "2. Whisk eggs, milk, and melted butter in a separate bowl.\n" +
                    "3. Combine wet and dry ingredients, mixing until just smooth.\n" +
                    "4. Heat a pan over medium heat and pour small ladlefuls of batter.\n" +
                    "5. Cook until bubbles form, then flip and cook the other side until golden."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "flour", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "milk", 250, "ml"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "butter", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "sugar", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id8, "baking powder", 1, "tsp"));

            // 9. Banana Smoothie
            long id9 = recipeDao.insert(new Recipe("Banana Smoothie",
                    "1. Peel bananas and break into chunks.\n" +
                    "2. Place banana pieces in a blender.\n" +
                    "3. Add milk and sugar to the blender.\n" +
                    "4. Blend on high speed until smooth and creamy.\n" +
                    "5. Pour into glasses and serve immediately."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id9, "banana", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id9, "milk", 250, "ml"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id9, "sugar", 1, "tbsp"));

            // 10. Apple Cinnamon Oatmeal
            long id10 = recipeDao.insert(new Recipe("Apple Cinnamon Oatmeal",
                    "1. Bring milk to a gentle simmer in a saucepan.\n" +
                    "2. Stir in oats and cook for 3-5 minutes, stirring occasionally.\n" +
                    "3. Peel and dice the apple into small cubes.\n" +
                    "4. Stir in sugar, cinnamon, and diced apple.\n" +
                    "5. Cook for another 2 minutes and serve warm."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id10, "oat", 100, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id10, "apple", 1, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id10, "cinnamon", 1, "tsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id10, "milk", 250, "ml"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id10, "sugar", 1, "tbsp"));

            // 11. Pasta with Tomato Sauce
            long id11 = recipeDao.insert(new Recipe("Pasta with Tomato Sauce",
                    "1. Cook pasta in salted boiling water until al dente, then drain.\n" +
                    "2. Heat olive oil in a pan and sauté minced garlic for 1 minute.\n" +
                    "3. Add chopped tomatoes and cook for 10-15 minutes until sauce thickens.\n" +
                    "4. Season with salt and stir in cooked pasta.\n" +
                    "5. Toss to combine and serve immediately."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id11, "pasta", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id11, "tomato", 3, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id11, "garlic", 2, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id11, "olive oil", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id11, "salt", 1, "pinch"));

            // 12. Pasta with Butter
            long id12 = recipeDao.insert(new Recipe("Pasta with Butter",
                    "1. Cook pasta in well-salted boiling water until al dente.\n" +
                    "2. Reserve a cup of pasta water before draining.\n" +
                    "3. Return hot pasta to the pot and add butter.\n" +
                    "4. Toss until butter melts and coats the pasta, adding pasta water if needed.\n" +
                    "5. Season with salt and top with grated cheese before serving."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id12, "pasta", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id12, "butter", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id12, "salt", 1, "pinch"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id12, "cheese", 30, "g"));

            // 13. Fried Rice
            long id13 = recipeDao.insert(new Recipe("Fried Rice",
                    "1. Cook rice until fluffy and allow to cool completely (preferably overnight).\n" +
                    "2. Heat oil in a wok or large pan over high heat.\n" +
                    "3. Fry minced garlic for 30 seconds, then add cold rice and stir-fry.\n" +
                    "4. Push rice to one side, scramble eggs on the other side, then mix together.\n" +
                    "5. Add soy sauce, toss everything together, and serve hot."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id13, "rice", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id13, "egg", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id13, "soy sauce", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id13, "garlic", 2, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id13, "oil", 2, "tbsp"));

            // 14. Rice and Beans
            long id14 = recipeDao.insert(new Recipe("Rice and Beans",
                    "1. Heat oil in a pot and sauté minced garlic until fragrant.\n" +
                    "2. Add beans and stir to coat with the garlic oil.\n" +
                    "3. Add rice and enough water to cover by 2 cm, season with salt.\n" +
                    "4. Bring to a boil, then reduce heat and simmer covered for 18-20 minutes.\n" +
                    "5. Fluff with a fork and serve as a complete meal."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id14, "rice", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id14, "bean", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id14, "salt", 1, "pinch"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id14, "oil", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id14, "garlic", 2, "clove"));

            // 15. Tomato Soup
            long id15 = recipeDao.insert(new Recipe("Tomato Soup",
                    "1. Melt butter in a pot over medium heat and sauté diced onion until soft.\n" +
                    "2. Add minced garlic and cook for another minute.\n" +
                    "3. Add chopped tomatoes, stir, and season with salt.\n" +
                    "4. Simmer for 20 minutes until tomatoes break down completely.\n" +
                    "5. Use an immersion blender to puree until smooth, adjust seasoning, and serve."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id15, "tomato", 4, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id15, "onion", 1, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id15, "garlic", 2, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id15, "butter", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id15, "salt", 1, "pinch"));

            // 16. Vegetable Stir Fry
            long id16 = recipeDao.insert(new Recipe("Vegetable Stir Fry",
                    "1. Peel and slice carrots diagonally and cut onion into wedges.\n" +
                    "2. Heat oil in a wok or large pan over high heat.\n" +
                    "3. Add minced garlic and stir-fry for 30 seconds until fragrant.\n" +
                    "4. Add carrots first (they take longer), then onion, stir-frying constantly.\n" +
                    "5. Drizzle with soy sauce, toss to combine, and serve immediately over rice."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id16, "carrot", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id16, "onion", 1, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id16, "garlic", 2, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id16, "soy sauce", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id16, "oil", 2, "tbsp"));

            // 17. Mashed Potatoes
            long id17 = recipeDao.insert(new Recipe("Mashed Potatoes",
                    "1. Peel and quarter potatoes, then boil in salted water until very tender.\n" +
                    "2. Drain the potatoes and return them to the pot over low heat.\n" +
                    "3. Add butter and let it melt into the hot potatoes.\n" +
                    "4. Pour in warm milk and mash until smooth and creamy.\n" +
                    "5. Season with salt, taste, and serve immediately."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id17, "potato", 4, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id17, "butter", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id17, "milk", 125, "ml"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id17, "salt", 1, "pinch"));

            // 18. Egg Salad
            long id18 = recipeDao.insert(new Recipe("Egg Salad",
                    "1. Hard-boil eggs for 10 minutes, then cool in an ice bath.\n" +
                    "2. Peel eggs and chop into small pieces.\n" +
                    "3. Place chopped eggs in a bowl and add mayonnaise.\n" +
                    "4. Season generously with salt and pepper, then mix gently.\n" +
                    "5. Serve on toast, in a sandwich, or over salad greens."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id18, "egg", 3, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id18, "mayonnaise", 2, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id18, "salt", 1, "pinch"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id18, "pepper", 1, "pinch"));

            // 19. Garlic Pasta
            long id19 = recipeDao.insert(new Recipe("Garlic Pasta",
                    "1. Cook pasta in well-salted boiling water until al dente, then drain.\n" +
                    "2. While pasta cooks, heat olive oil in a pan over medium-low heat.\n" +
                    "3. Add thinly sliced garlic and cook gently until golden and fragrant.\n" +
                    "4. Add drained pasta to the pan and toss to coat in the garlic oil.\n" +
                    "5. Season with salt and pepper, toss once more, and serve immediately."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id19, "pasta", 200, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id19, "garlic", 3, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id19, "olive oil", 3, "tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id19, "salt", 1, "pinch"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id19, "pepper", 1, "pinch"));

            // 20. Chicken Soup
            long id20 = recipeDao.insert(new Recipe("Chicken Soup",
                    "1. Place chicken pieces in a large pot and cover with water, bring to a boil.\n" +
                    "2. Skim off any foam, then add peeled garlic cloves and diced onion.\n" +
                    "3. Peel and slice carrots, add to the pot with salt.\n" +
                    "4. Reduce heat and simmer for 45 minutes until chicken is fully cooked.\n" +
                    "5. Remove chicken, shred the meat, return to pot, adjust seasoning, and serve."));
            recipeIngredientDao.insert(new RecipeIngredient((int) id20, "chicken", 300, "g"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id20, "carrot", 2, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id20, "onion", 1, "unit"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id20, "garlic", 2, "clove"));
            recipeIngredientDao.insert(new RecipeIngredient((int) id20, "salt", 1, "pinch"));

        }).start();
    }
}
