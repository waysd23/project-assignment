package com.inhouse.food.management.service;

import com.inhouse.food.management.model.Grocery;

import java.time.LocalDate;

public class GroceryService {

    // Utility
    /**
     * Checks if the given grocery item is expired.
     *
     * @param grocery the grocery item to check
     * @return {@code true} if the grocery item is expired, {@code false} otherwise
     * @example
     * <pre>
     * Grocery grocery = new Grocery();
     * grocery.setExpiryDate(LocalDate.of(2023, 10, 1));
     * boolean expired = groceryService.isExpired(grocery);
     * </pre>
     */
    public boolean isExpired(Grocery grocery) {
        return LocalDate.now().isAfter(grocery.getExpiryDate());
    }

    /**
     * Calculates the total value of the given grocery item.
     *
     * @param grocery the grocery item to calculate the value for
     * @return the total value of the grocery item
     * @example
     * <pre>
     * Grocery grocery = new Grocery();
     * grocery.setQuantity(10);
     * grocery.setPricePerUnit(2.5);
     * double value = groceryService.calculateValue(grocery);
     * </pre>
     */
    public double calculateValue(Grocery grocery) {
        return grocery.getQuantity() * grocery.getPricePerUnit();
    }

    /**
     * Checks if two grocery items can be clubbed together.
     *
     * @param existingGrocery the existing grocery item
     * @param newlyAddedGrocery the newly added grocery item
     * @return {@code true} if the two grocery items can be clubbed together, {@code false} otherwise
     * @example
     * <pre>
     * Grocery grocery1 = new Grocery();
     * grocery1.setName("Apple");
     * grocery1.setUnit("kg");
     * grocery1.setPricePerUnit(3.0);
     * grocery1.setExpiryDate(LocalDate.of(2023, 10, 1));
     *
     * Grocery grocery2 = new Grocery();
     * grocery2.setName("Apple");
     * grocery2.setUnit("kg");
     * grocery2.setPricePerUnit(3.0);
     * grocery2.setExpiryDate(LocalDate.of(2023, 10, 1));
     *
     * boolean clubbable = groceryService.areGroceriesClubbable(grocery1, grocery2);
     * </pre>
     */
    public boolean areGroceriesClubbable(Grocery existingGrocery, Grocery newlyAddedGrocery) {
        return existingGrocery.getName().equals(newlyAddedGrocery.getName()) &&
                existingGrocery.getUnit().equals(newlyAddedGrocery.getUnit()) &&
                existingGrocery.getPricePerUnit() == newlyAddedGrocery.getPricePerUnit() &&
                existingGrocery.getExpiryDate().isEqual(newlyAddedGrocery.getExpiryDate());
    }
}
