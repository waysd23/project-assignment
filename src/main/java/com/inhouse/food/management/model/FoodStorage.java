package com.inhouse.food.management.model;


import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a storage/inventory such as refrigerator for various food items.
 * The food items are organized in a map where the key is the name of grocery,
 * and the value is the available items corresponding the grocery name grouped using expiry date.
 */
@Getter
public class FoodStorage {
    /**
     * A map that holds the groceries. The key is a string representing the type of grocery,
     * and the value is a list of Grocery objects.
     */
    private final Map<String, List<Grocery>> groceriesPerCategory = new HashMap<>();
}