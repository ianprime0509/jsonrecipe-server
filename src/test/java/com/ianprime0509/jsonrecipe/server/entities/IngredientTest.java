package com.ianprime0509.jsonrecipe.server.entities;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for the {@link Ingredient} class.
 */
@RunWith(SpringRunner.class)
public class IngredientTest {
  @Test
  public void testParse_withSimpleIngredient() {
    assertEquals(new Ingredient(new Fraction(2), "apples"), Ingredient.parse("2 each apples"));
  }

  @Test
  public void testParse_withMultiWordItem() {
    assertEquals(new Ingredient(new Fraction(1), "green pepper"),
        Ingredient.parse("1 each green pepper"));
  }

  @Test
  public void testParse_withMixedNumberQuantity() {
    assertEquals(new Ingredient(new Fraction(5, 4), "tbsp", "red pepper flakes"),
        Ingredient.parse("1 1/4 tbsp red pepper flakes"));
  }

  @Test
  public void testParse_withPreparationInstruction() {
    assertEquals(
        new Ingredient(new Fraction(3, 2), "cups", "onion", Arrays.asList("finely chopped")),
        Ingredient.parse("1 1/2 cups onion, finely chopped"));
  }

  @Test
  public void testParse_withMultiplePreparationInstructions() {
    assertEquals(
        new Ingredient(new Fraction(1, 2), "pound", "potatoes", Arrays.asList("diced", "peeled")),
        Ingredient.parse("1/2 pound potatoes, diced, peeled"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParse_withTrailingComma_fails() {
    Ingredient.parse("1 cup chili peppers,");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParse_withMissingUnit_fails() {
    Ingredient.parse("1 apple");
  }
}
