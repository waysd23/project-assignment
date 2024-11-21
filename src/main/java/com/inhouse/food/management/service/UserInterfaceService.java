package com.inhouse.food.management.service;

import static com.inhouse.food.management.FoodWasteApp.*;

import com.inhouse.food.management.model.Grocery;
import com.inhouse.food.management.model.Recipe;
import java.time.LocalDate;
import java.util.*;

public class UserInterfaceService {

  /**
   * Initialize the application with sample groceries and recipes.
   *
   * <p>This method adds sample grocery items to the fridge and sample recipes to the recipe book.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.init();
   * }</pre>
   */
  public static void init() {
    // Add some sample groceries to the fridge
    fridgeService.addGrocery(new Grocery("Milk", 2, "liters", 15, LocalDate.now().plusDays(5)));
    fridgeService.addGrocery(new Grocery("Eggs", 12, "pieces", 2, LocalDate.now().plusDays(10)));
    fridgeService.addGrocery(new Grocery("Flour", 1, "kg", 20, LocalDate.now().plusMonths(6)));

    // Add some sample recipes to the cookbook
    Map<String, Double> pancakeIngredients = Map.of("Milk", 1.5, "Eggs", 2.0, "Flour", 0.5);
    recipeService.addRecipe(
        new Recipe(
            "Pancakes",
            "Delicious breakfast",
            "Mix and cook on a skillet.",
            pancakeIngredients,
            4));
  }

  /**
   * Start the console menu for user interaction.
   *
   * <p>This method displays the main menu and processes user inputs to perform various actions.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.start();
   * }</pre>
   */
  public static void start() {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      displayMenu();
      System.out.print("Select an option: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline character
      try {
        switch (choice) {
          case 1:
            addGrocery(scanner);
            break;
          case 2:
            removeGrocery(scanner);
            break;
          case 3:
            viewAllGroceries();
            break;
          case 4:
            viewExpiredGroceries();
            break;
          case 5:
            calculateTotalValue();
            break;
          case 6:
            addRecipe(scanner);
            break;
          case 7:
            viewAllRecipes();
            break;
          case 8:
            viewPossibleRecipes(scanner);
            break;
          case 9:
            System.out.println("Exiting application. Goodbye!");
            exit = true;
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    scanner.close();
  }

  /**
   * Display the main menu options to the user.
   *
   * <p>This method prints the available actions the user can select from the console menu.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.displayMenu();
   * }</pre>
   */
  private static void displayMenu() {
    System.out.println("\n--- Food Waste Management Menu ---");
    System.out.println("1. Add Grocery");
    System.out.println("2. Remove Grocery");
    System.out.println("3. View All Groceries");
    System.out.println("4. View Only Expired Groceries");
    System.out.println("5. Calculate Total Value of Groceries");
    System.out.println("6. Add Recipe");
    System.out.println("7. View All Recipes");
    System.out.println("8. View Possible Recipes with Current Groceries");
    System.out.println("9. Exit");
  }

  /**
   * Add a new grocery item to the fridge.
   *
   * <p>This method prompts the user to enter details for a new grocery item and adds it to the
   * fridge.
   *
   * @param scanner Scanner object for user input
   *     <p>Example usage:
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.addGrocery(scanner);
   * }</pre>
   */
  private static void addGrocery(Scanner scanner) {
    System.out.print("Enter grocery name: ");
    String name = scanner.nextLine();

    System.out.print("Enter quantity: ");
    double quantity = scanner.nextDouble();

    System.out.print("Enter unit (e.g., liters, kg, pieces): ");
    String unit = scanner.next();

    System.out.print("Enter price per unit (in NOK): ");
    double pricePerUnit = scanner.nextDouble();

    System.out.print("Enter expiry date (YYYY-MM-DD): ");
    String expiryDateInput = scanner.next();
    LocalDate expiryDate = LocalDate.parse(expiryDateInput);

    fridgeService.addGrocery(new Grocery(name, quantity, unit, pricePerUnit, expiryDate));
    System.out.println("Grocery added successfully.");
  }

  /**
   * Remove a grocery item from the fridge.
   *
   * <p>This method prompts the user to enter details for removing a grocery item from the fridge.
   *
   * @param scanner Scanner object for user input
   *     <p>Example usage:
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.removeGrocery(scanner);
   * }</pre>
   */
  private static void removeGrocery(Scanner scanner) {
    System.out.print("Enter grocery name to remove: ");
    String name = scanner.nextLine();

    System.out.print("Enter quantity to remove: ");
    double quantity = scanner.nextDouble();

    boolean success = fridgeService.removeGrocery(name, quantity);
    if (success) {
      System.out.println("Grocery removed successfully.");
    } else {
      System.out.println("Failed to remove grocery. Check the name and quantity.");
    }
  }

  /**
   * View all grocery items in the fridge.
   *
   * <p>This method displays all groceries sorted by expiry date, indicating if any are expired.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewAllGroceries();
   * }</pre>
   */
  private static void viewAllGroceries() {
    System.out.println("\n--- All Groceries ---");
    fridgeService.getAllGroceries().stream()
        .sorted(Comparator.comparing(Grocery::getExpiryDate))
        .forEach(
            x -> {
              if (groceryService.isExpired(x)) {
                System.out.println(x + " (expired)");
              } else {
                System.out.println(x);
              }
            });
  }

  /**
   * View all expired grocery items in the fridge.
   *
   * <p>This method displays only the groceries that have passed their expiry date.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewExpiredGroceries();
   * }</pre>
   */
  private static void viewExpiredGroceries() {
    System.out.println("\n--- Expired Groceries ---");
    List<Grocery> expiredGroceries = fridgeService.getExpiredGroceries();
    if (expiredGroceries.isEmpty()) {
      System.out.println("No expired groceries found.");
    } else {
      expiredGroceries.forEach(System.out::println);
    }
  }

  /**
   * Calculate the total value of all groceries in the fridge.
   *
   * <p>This method computes and prints the total value of the groceries currently in the fridge.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.calculateTotalValue();
   * }</pre>
   */
  private static void calculateTotalValue() {
    double totalValue = fridgeService.calculateTotalValue();
    System.out.printf("Total value of groceries: NOK %.2f%n", totalValue);
  }

  /**
   * Add a new recipe to the recipe book.
   *
   * <p>This method prompts the user to enter details for a new recipe and adds it to the recipe
   * book.
   *
   * @param scanner Scanner object for user input
   *     <p>Example usage:
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.addRecipe(scanner);
   * }</pre>
   */
  private static void addRecipe(Scanner scanner) {
    System.out.print("Enter recipe name: ");
    String name = scanner.nextLine();

    System.out.print("Enter recipe description: ");
    String description = scanner.nextLine();

    System.out.print("Enter procedure: ");
    String procedure = scanner.nextLine();

    Map<String, Double> ingredients = new HashMap<>();
    System.out.println("Enter ingredients (type 'done' to finish):");
    while (true) {
      System.out.print("Ingredient name: ");
      String ingredientName = scanner.nextLine();
      if (ingredientName.equalsIgnoreCase("done")) break;

      System.out.print("Quantity: ");
      double quantity = scanner.nextDouble();
      scanner.nextLine(); // Consume newline

      ingredients.put(ingredientName, quantity);
    }

    System.out.print("Serves (number of people): ");
    int serves = scanner.nextInt();

    recipeService.addRecipe(new Recipe(name, description, procedure, ingredients, serves));
    System.out.println("Recipe added successfully.");
  }

  /**
   * View all recipes in the recipe book.
   *
   * <p>This method displays all recipes stored in the recipe book.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewAllRecipes();
   * }</pre>
   */
  private static void viewAllRecipes() {
    System.out.println("\n--- All Recipes ---");
    recipeService.getRecipes().forEach(System.out::println);
  }

  /**
   * View all possible recipes that can be made with current groceries.
   *
   * <p>This method prompts the user to indicate whether to include expired groceries and displays
   * recipes that can be made with the groceries available in the fridge.
   *
   * @param scanner Scanner object for user input
   *     <p>Example usage:
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.viewPossibleRecipes(scanner);
   * }</pre>
   */
  private static void viewPossibleRecipes(Scanner scanner) {
    System.out.print("Do you want to see possible recipes including expired groceries? (y/n): ");
    String includeExpiredGrocery = scanner.nextLine();

    System.out.println("\n--- Possible Recipes with Current Groceries ---");
    List<Recipe> possibleRecipes =
        recipeService.getPossibleRecipes(fridgeService.getAllGroceries(), includeExpiredGrocery);
    if (possibleRecipes.isEmpty()) {
      System.out.println("No recipes can be made with the current groceries.");
    } else {
      possibleRecipes.forEach(System.out::println);
    }
  }
}
