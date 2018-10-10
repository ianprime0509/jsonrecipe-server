package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import org.apache.commons.math3.fraction.Fraction;
import lombok.NonNull;

public class Ingredient {
  @NonNull
  private Fraction quantity;

  @NonNull
  private String unit;

  @NonNull
  private String item;

  @NonNull
  private List<String> preparation;
}
