package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import org.apache.commons.math3.fraction.Fraction;
import lombok.Data;
import lombok.NonNull;

/**
 * An ingredient in a recipe.
 */
@Data
public class Ingredient {
  /**
   * The quantity of the specified item, in terms of the specified unit.
   */
  @NonNull
  private Fraction quantity;

  /**
   * The unit of measurement which the specified quantity uses (e.g. "cup").
   */
  @NonNull
  private String unit;

  /**
   * The item of which this ingredient consists (e.g. "apple").
   */
  @NonNull
  private String item;

  /**
   * How the ingredient should be prepared prior to use (e.g. "chopped").
   */
  @NonNull
  private List<String> preparation;
}
