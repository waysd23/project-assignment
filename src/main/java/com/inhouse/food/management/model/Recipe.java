package com.inhouse.food.management.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * Represents a recipe with its details like name, description, procedure, ingredients, and the
 * number it serves.
 */
@Data
public class Recipe {
  /** unique id */
  private Integer id;
  /** Name of the recipe. */
  private String name;

  /** Brief description of the recipe. */
  private String description;

  /** Detailed procedure to prepare the recipe. */
  private String procedure;

  /** Ingredients required for the recipe. Maps ingredient name to the required quantity. */
  private Map<String, Double> ingredients; // Ingredient name -> Required quantity

  /** The number of people the recipe serves. */
  private int serves;

  /**
   * Returns a string representation of the recipe.
   *
   * @return the name and description of the recipe.
   */

  public Recipe(Integer id, String name, String description, String procedure,
                Map<String, Double> ingredients, int serves) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.procedure = procedure;
      this.ingredients = ingredients;
      this.serves = serves;
  }

    // Method to calculate the maximum column widths dynamically
    private static int[] calculateColumnWidths(List<Recipe> recipes) {
        int idWidth = "ID".length();
        int nameWidth = "Name".length();
        int descriptionWidth = "Description".length();
        int procedureWidth = "Procedure".length();
        int ingredientsWidth = "Ingredients".length();
        int servesWidth = "Serves".length();

        for (Recipe recipe : recipes) {
            idWidth = Math.max(idWidth, String.valueOf(recipe.id).length());
            nameWidth = Math.max(nameWidth, recipe.name.length());
            descriptionWidth = Math.max(descriptionWidth, recipe.description.length());
            procedureWidth = Math.max(procedureWidth, recipe.procedure.length());
            ingredientsWidth = Math.max(ingredientsWidth, recipe.ingredients.toString().length());
            servesWidth = Math.max(servesWidth, String.valueOf(recipe.serves).length());
        }

        return new int[]{idWidth, nameWidth, descriptionWidth, procedureWidth, ingredientsWidth, servesWidth};
    }

    // Static method to print a list of Recipe objects in a tabular format
    public static String toTable(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            return "No data available!";
        }

        // Calculate column widths
        int[] columnWidths = calculateColumnWidths(recipes);
        int idWidth = columnWidths[0];
        int nameWidth = columnWidths[1];
        int descriptionWidth = columnWidths[2];
        int procedureWidth = columnWidths[3];
        int servesWidth = columnWidths[4];
        int ingredientsWidth = columnWidths[5];

        // Format strings for headers and rows
        String headerFormat =
            String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds", idWidth, nameWidth,
                descriptionWidth, procedureWidth, servesWidth, ingredientsWidth);
        String rowFormat =
            String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds", idWidth, nameWidth,
                descriptionWidth, procedureWidth, servesWidth, ingredientsWidth);

        // Header Row
        String header = String.format(headerFormat, "ID", "Name", "Description", "Procedure", "Serves", "Ingredients");
        String separator = "-".repeat(header.length());

        // Data Rows
        StringBuilder table = new StringBuilder();
        table.append(header).append("\n").append(separator).append("\n");
        for (Recipe recipe : recipes) {
            table.append(String.format(rowFormat, recipe.id, recipe.name, recipe.description, recipe.procedure, recipe.serves, recipe.getIngredients())).append("\n");
        }
        return table.toString();
    }

  @Override
  public String toString() {
    return name + ": " + description;
  }
}
