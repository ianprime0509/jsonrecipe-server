package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class IngredientGroup {
  @NonNull
  private String heading;

  @NonNull
  private List<Ingredient> ingredients;
}
