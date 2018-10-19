package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

/** A group of ingredients under a single heading. */
@Data
public class IngredientGroup {
  /** The heading for this group of ingredients. */
  @NonNull private String heading;

  /** The ingredients under this heading. */
  @NonNull private List<Ingredient> ingredients;
}
