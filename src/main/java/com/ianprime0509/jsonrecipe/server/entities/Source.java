package com.ianprime0509.jsonrecipe.server.entities;

import lombok.Data;
import lombok.NonNull;

/**
 * The source of a recipe (e.g. the person who wrote it and the book where it was found).
 */
@Data
public class Source {
  /**
   * The full name of the author of the recipe.
   */
  @NonNull
  private String author;

  /**
   * The location from which the recipe was retrived (e.g. a website or a book).
   */
  private Location location;
}
