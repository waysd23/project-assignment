package com.inhouse.food.management.service;

import com.inhouse.food.management.model.Cookbook;
import com.inhouse.food.management.model.Grocery;
import com.inhouse.food.management.model.Recipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

/**
 * The RecipeService class provides methods to manage and interact with recipes, including adding
 * recipes, retrieving all recipes, and filtering recipes based on available grocery items.
 */
@NoArgsConstructor
public class RecipeService {
  private Cookbook cookbookForRecipes = new Cookbook();
  private GroceryService groceryService = new GroceryService();

  public RecipeService(GroceryService mockGroceryService) {
    groceryService = mockGroceryService;
  }

  /**
   * Adds a new recipe to the list of recipes.
   *
   * @param recipe the recipe to be added
   *     <p>Example usage:
   *     <pre>
   *     Recipe newRecipe = new Recipe("Pasta", ingredientsMap);
   *     recipeService.addRecipe(newRecipe);
   * </pre>
   */
  public void addRecipe(Recipe recipe) {
    cookbookForRecipes.getRecipes().add(recipe);
  }

  /**
   * Returns the list of all recipes.
   *
   * @return the list of recipes
   *     <p>Example usage:
   *     <pre>
   *     List&lt;Recipe&gt; allRecipes = recipeService.getRecipes();
   * </pre>
   */
  public List<Recipe> getRecipes() {
    return cookbookForRecipes.getRecipes();
  }

  /**
   * Returns a list of possible recipes that can be made with the given fridge items. This method
   * filters the recipes based on whether expired groceries should be included.
   *
   * @param fridgeItems the list of groceries available in the fridge
   * @param includeExpiredGrocery whether to include expired groceries ("y" for yes, otherwise no)
   * @return the list of possible recipes that can be made
   *     <p>Example usage:
   *     <pre>
   *     List&lt;Grocery&gt; fridgeItems = Arrays.asList(new Grocery("Tomato", 2, false));
   *     List&lt;Recipe&gt; possibleRecipes = recipeService.getPossibleRecipes(fridgeItems, "n");
   * </pre>
   */
  public List<Recipe> getPossibleRecipes(List<Grocery> fridgeItems, String includeExpiredGrocery) {

    Map<String, List<Grocery>> expiredItemsIncludedBasedOnFlag =
        fridgeItems.stream()
            .filter(
                grocery -> {
                  if (includeExpiredGrocery.equalsIgnoreCase("y")) {
                    return true;
                  } else {
                    return !groceryService.isExpired(grocery);
                  }
                })
            .collect(Collectors.groupingBy(Grocery::getName));

    return cookbookForRecipes.getRecipes().stream()
        .filter(
            recipe ->
                recipe.getIngredients().entrySet().stream()
                    .allMatch(
                        entry -> {
                          List<Grocery> grocery =
                              expiredItemsIncludedBasedOnFlag.get(entry.getKey());
                          double availableQuantity =
                              Optional.ofNullable(grocery).orElse(new ArrayList<>()).stream()
                                  .mapToDouble(Grocery::getQuantity)
                                  .sum();
                          return grocery != null && availableQuantity >= entry.getValue();
                        }))
        .toList();
  }
}
