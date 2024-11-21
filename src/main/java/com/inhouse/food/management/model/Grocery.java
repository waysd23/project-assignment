package com.inhouse.food.management.model;

import java.time.LocalDate;
import lombok.Getter;

/** Represents a grocery item in the inventory. */
@Getter
public class Grocery {
  /** Name of the grocery item. */
  private String name;
  /** Quantity of the grocery item (e.g., in liters or kilograms). */
  private double quantity; // e.g., in liters or kilograms
  /** Unit of measurement for the quantity (e.g., "liters", "kg", "pieces"). */
  private String unit; // e.g., "liters", "kg", "pieces"
  /** Price per unit of the grocery item (in NOK). */
  private double pricePerUnit; // in NOK
  /** Expiry date of the grocery item. */
  private LocalDate expiryDate;

  // Constructor
  /**
   * Constructs a new Grocery item.
   *
   * @param name the name of the grocery item
   * @param quantity the quantity of the grocery item
   * @param unit the unit of measurement for the quantity
   * @param pricePerUnit the price per unit of the grocery item
   * @param expiryDate the expiry date of the grocery item
   */
  public Grocery(
      String name, double quantity, String unit, double pricePerUnit, LocalDate expiryDate) {
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("Name cannot be null or empty");
    if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
    if (pricePerUnit < 0) throw new IllegalArgumentException("Price per unit cannot be negative");
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.pricePerUnit = pricePerUnit;
    this.expiryDate = expiryDate;
  }

  /**
   * Sets the quantity of the grocery item.
   *
   * @param quantity the quantity to set
   * @throws IllegalArgumentException if the quantity is negative
   */
  public void setQuantity(double quantity) {
    if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
    this.quantity = quantity;
  }

  /**
   * Returns a string representation of the grocery item.
   *
   * @return a string representation of the grocery item
   */
  @Override
  public String toString() {
    return String.format(
        "%s: %.2f %s, NOK %.2f/unit, Expiry: %s", name, quantity, unit, pricePerUnit, expiryDate);
  }
}
