package com.inhouse.food.management.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * The {@code Cookbook} class represents a collection of recipes. It provides methods to manage the
 * list of recipes.
 *
 * <p>Example usage:
 *
 * <pre>
 * Cookbook cookbook = new Cookbook();
 * cookbook.getRecipes().add(new Recipe("Spaghetti Bolognese"));
 * </pre>
 */
@Data
public class Cookbook {

    /** A list of recipes contained in the cookbook. */
    private List<Recipe> recipes = new ArrayList<>();
}
