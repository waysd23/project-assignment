package com.inhouse.food.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Represents a recipe with its details like name, description, procedure, ingredients, and the number it serves.
 */
@Data
@AllArgsConstructor
public class Recipe {
    /**
     * Name of the recipe.
     */
    private String name;

    /**
     * Brief description of the recipe.
     */
    private String description;

    /**
     * Detailed procedure to prepare the recipe.
     */
    private String procedure;

    /**
     * Ingredients required for the recipe. Maps ingredient name to the required quantity.
     */
    private Map<String, Double> ingredients; // Ingredient name -> Required quantity

    /**
     * The number of people the recipe serves.
     */
    private int serves;

    /**
     * Returns a string representation of the recipe.
     * 
     * @return the name and description of the recipe.
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
