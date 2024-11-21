package com.inhouse.food.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.inhouse.food.management.model.Grocery;
import com.inhouse.food.management.model.Recipe;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RecipeServiceTest {

    @Test
    public void testAddRecipe() {
        RecipeService recipeService = new RecipeService();

        Recipe recipe =
                new Recipe("Home Recipe", "Recipe", "mix the ingredients", Map.of("Milk", 1.0), 2);

        recipeService.addRecipe(recipe);

        List<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());
        assertEquals(recipe, recipes.get(0));
    }

    @Test
    public void testGetRecipes() {
        RecipeService recipeService = new RecipeService();

        Recipe recipe1 =
                new Recipe("Home Recipe", "Recipe", "mix the ingredients", Map.of("Milk", 1.0), 2);
        Recipe recipe2 = new Recipe("Home Recipe2", "Bread", "Crumbs", Map.of("Milk", 1.0), 2);

        recipeService.addRecipe(recipe1);
        recipeService.addRecipe(recipe2);

        List<Recipe> recipes = recipeService.getRecipes();

        assertEquals(2, recipes.size());
        assertEquals(recipe1, recipes.get(0));
        assertEquals(recipe2, recipes.get(1));
    }

    @Test
    public void testGetPossibleRecipes_IncludeExpiredGrocery_NoExpiredItems() {
        GroceryService mockGroceryService = Mockito.mock(GroceryService.class);
        RecipeService recipeService = new RecipeService(mockGroceryService);

        Grocery milk = new Grocery("Milk", 1, "Litre", 1.50, LocalDate.now().plusDays(2));
        Grocery bread = new Grocery("Bread", 2, "Pieces", 0.50, LocalDate.now().plusDays(1));

        List<Grocery> fridgeItems = Arrays.asList(milk, bread);

        Recipe recipe1 =
                new Recipe("Home Recipe", "Recipe", "mix the ingredients", Map.of("Milk", 1.0), 2);
        Recipe recipe2 =
                new Recipe("Home Recipe2", "Bread", "mix the crumbs", Map.of("Milk", 1.0), 2);
        recipeService.addRecipe(recipe1);
        recipeService.addRecipe(recipe2);

        List<Recipe> possibleRecipes = recipeService.getPossibleRecipes(fridgeItems, "y");
        assertEquals(Arrays.asList(recipe1, recipe2), possibleRecipes);
    }

    /*@Test
        public void testGetPossibleRecipes_ExcludeExpiredGrocery_WithExpiredItems() {
            GroceryService mockGroceryService = Mockito.mock(GroceryService.class);
            RecipeService recipeService = new RecipeService(mockGroceryService);

            Grocery milk = new Grocery("Milk", 1, "Litre", 1.50, LocalDate.now().plusDays(2));
            Grocery bread = new Grocery("Bread", 2, "Pieces", 0.50, LocalDate.now().minusDays(1));

            when(mockGroceryService.isExpired(bread)).thenReturn(true);
            when(mockGroceryService.isExpired(milk)).thenReturn(false);

            List<Grocery> fridgeItems = Arrays.asList(milk, bread);

            Recipe recipe1 = new Recipe("Home Recipe", "Recipe", "mix the ingredients",Map.of("Milk", 1.0),2);
            Recipe recipe2 = new Recipe("Home Recipe2", "Bread", "mix the crumbs",Map.of("Milk", 1.0),2);
            recipeService.addRecipe(recipe1);
            recipeService.addRecipe(recipe2);

            List<Recipe> possibleRecipes = recipeService.getPossibleRecipes(fridgeItems, "n");
            assertEquals(Collections.singletonList(recipe1), possibleRecipes);
        }
    */
    @Test
    public void testGetPossibleRecipes_IncludeExpiredGrocery_WithExpiredItems() {
        GroceryService mockGroceryService = Mockito.mock(GroceryService.class);
        RecipeService recipeService = new RecipeService(mockGroceryService);

        Grocery milk = new Grocery("Milk", 1, "Litre", 1.50, LocalDate.now().plusDays(2));
        Grocery bread = new Grocery("Bread", 2, "Pieces", 0.50, LocalDate.now().minusDays(1));

        List<Grocery> fridgeItems = Arrays.asList(milk, bread);

        Recipe recipe1 =
                new Recipe("Home Recipe", "Recipe", "mix the ingredients", Map.of("Milk", 1.0), 2);
        Recipe recipe2 =
                new Recipe("Home Recipe2", "Crumbs", "mix the ingredients", Map.of("Milk", 1.0), 2);
        recipeService.addRecipe(recipe1);
        recipeService.addRecipe(recipe2);

        List<Recipe> possibleRecipes = recipeService.getPossibleRecipes(fridgeItems, "y");
        assertEquals(Arrays.asList(recipe1, recipe2), possibleRecipes);
    }

    /*@Test
    public void testGetPossibleRecipes_ExcludeExpiredGrocery_AllExpiredItems() {
        GroceryService mockGroceryService = Mockito.mock(GroceryService.class);
        RecipeService recipeService = new RecipeService(mockGroceryService);

        Grocery milk = new Grocery("Milk", 1, "Litre", 1.50, LocalDate.now().minusDays(2));
        Grocery bread = new Grocery("Bread", 2, "Pieces", 0.50, LocalDate.now().minusDays(1));

        when(mockGroceryService.isExpired(bread)).thenReturn(true);
        when(mockGroceryService.isExpired(milk)).thenReturn(true);

        List<Grocery> fridgeItems = Arrays.asList(milk, bread);

        Recipe recipe1 = new Recipe("Home Recipe", "Recipe", "mix the ingredients",Map.of("Milk", 1.0),2);
        Recipe recipe2 = new Recipe("Home Recipe", "Recipe", "mix the ingredients",Map.of("Bread", 1.0),2);
        recipeService.addRecipe(recipe1);
        recipeService.addRecipe(recipe2);

        List<Recipe> possibleRecipes = recipeService.getPossibleRecipes(fridgeItems, "n");
        assertEquals(Collections.emptyList(), possibleRecipes);
    }*/
}
