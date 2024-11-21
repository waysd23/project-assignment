package com.inhouse.food.management;

import static com.inhouse.food.management.service.UserInterfaceService.init;
import static com.inhouse.food.management.service.UserInterfaceService.start;

import com.inhouse.food.management.service.FridgeService;
import com.inhouse.food.management.service.GroceryService;
import com.inhouse.food.management.service.RecipeService;
import com.inhouse.food.management.service.UserInterfaceService;

/**
 * Application for managing food waste by tracking groceries and recipes.
 *
 * <p>This application allows users to add, remove, and view groceries, as well as add and view
 * recipes. It also provides an option to view possible recipes that can be made with the current
 * groceries.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * FoodWasteApp.main(new String[]{});
 * }</pre>
 */
public class FoodWasteApp {

  public static final FridgeService fridgeService = new FridgeService();
  public static final RecipeService recipeService = new RecipeService();
  public static final GroceryService groceryService = new GroceryService();

  public static void main(String[] args) {
    UserInterfaceService userInterfaceService = new UserInterfaceService();
    init(); // Initialize sample data
    start(); // Start the console menu
  }
}
