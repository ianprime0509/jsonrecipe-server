package com.ianprime0509.jsonrecipe.server.entities;

import lombok.Data;
import lombok.NonNull;

/**
 * A single step in the preparation of a recipe.
 */
@Data
public class Direction {
  /**
   * The text of the direction.
   */
  @NonNull
  private String text;
}
