package com.inhouse.food.management.service;

import com.inhouse.food.management.model.FoodStorage;
import com.inhouse.food.management.model.Grocery;
import java.util.*;

/** Service class for managing groceries and food storage in a fridge. */
public class FridgeService {
  private FoodStorage foodStorage;

  private GroceryService groceryService;

  public FridgeService(FoodStorage mockFoodStorage, GroceryService mockGroceryService) {
    this.foodStorage = mockFoodStorage;
    this.groceryService = mockGroceryService;
  }

  public FridgeService() {
    foodStorage = new FoodStorage();
    groceryService = new GroceryService();
  }

  /**
   * Adds a grocery item to the food storage. If the same grocery item added multiple times then
   * records would clubbed to an existing one based on expiry date, their quantities are combined.
   * In case where expiry dates are different the groceries even if falls under same category would
   * be treated differently and hence there quatities won't be clubbed
   *
   * @param grocery the grocery item to be added
   */
  public void addGrocery(Grocery grocery) {
    ArrayList<Grocery> groceryToBeAdded = new ArrayList<>();
    groceryToBeAdded.add(grocery);
    foodStorage
        .getGroceriesPerCategory()
        .merge(
            grocery.getName(),
            groceryToBeAdded,
            (existing, newGrocery) -> {
              Optional<Grocery> existingGrocery =
                  existing.stream()
                      .filter(g -> groceryService.areGroceriesClubbable(g, grocery))
                      .findFirst();

              if (existingGrocery.isPresent()) {
                existingGrocery
                    .get()
                    .setQuantity(existingGrocery.get().getQuantity() + grocery.getQuantity());
              } else {
                existing.addAll(newGrocery);
              }
              return existing;
            });
  }

  /**
   * Removes a specified quantity of a grocery item from the food storage.
   *
   * @param name the name of the grocery item to be removed
   * @param quantity the quantity to be removed
   * @return true if the specified quantity was removed successfully, false otherwise
   */
  public boolean removeGrocery(String name, double quantity) {
    List<Grocery> groceries = foodStorage.getGroceriesPerCategory().get(name);
    if (groceries == null) return false;

    // Calculate total quantity of the groceries
    double totalQuantity = groceries.stream().map(Grocery::getQuantity).reduce(0.0, Double::sum);
    if (totalQuantity < quantity) return false;

    // Remove the specified quantity
    double remainingQuantity = quantity;
    Iterator<Grocery> iterator = groceries.iterator();
    while (iterator.hasNext() && remainingQuantity > 0) {
      Grocery grocery = iterator.next();
      double groceryQuantity = grocery.getQuantity();
      if (groceryQuantity <= remainingQuantity) {
        remainingQuantity -= groceryQuantity;
        iterator.remove();
      } else {
        grocery.setQuantity(groceryQuantity - remainingQuantity);
        remainingQuantity = 0;
      }
    }

    if (groceries.isEmpty()) {
      foodStorage.getGroceriesPerCategory().remove(name);
    }
    return true;
  }

  /**
   * Retrieves all groceries stored in the storage. Expired groceries also gets retrieved to give an
   * overview of the storage
   *
   * @return a list of all grocery items in the food storage
   */
  public List<Grocery> getAllGroceries() {
    return new ArrayList<>(
        foodStorage.getGroceriesPerCategory().values().stream()
            .flatMap(Collection::stream)
            .toList());
  }

  /**
   * Retrieves all expired groceries from the food storage.
   *
   * @return a list of expired grocery items
   */
  public List<Grocery> getExpiredGroceries() {
    return foodStorage.getGroceriesPerCategory().values().stream()
        .flatMap(Collection::stream)
        .filter(groceryService::isExpired)
        .toList();
  }

  /**
   * Calculates the total value of all groceries stored in the fridge.
   *
   * @return the total value of all grocery items
   */
  public double calculateTotalValue() {
    return foodStorage.getGroceriesPerCategory().values().stream()
        .flatMap(Collection::stream)
        .mapToDouble(groceryService::calculateValue)
        .sum();
  }

    /**
     * Calculates the total value of all expired groceries stored in the fridge.
     *
     * @return the total value of all expired grocery items
     */
    public double calculateTotalValueOfExpiredGroceries() {
        return foodStorage.getGroceriesPerCategory().values().stream()
            .flatMap(Collection::stream)
            .filter(groceryService::isExpired)
            .mapToDouble(groceryService::calculateValue)
            .sum();
    }
}
