package com.ianprime0509.jsonrecipe.server.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Arrays;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/** Tests for the {@link Ingredient} class. */
@RunWith(SpringRunner.class)
public class IngredientTest {
  @Test
  public void testParse_withSimpleIngredient() {
    assertThat(Ingredient.parse("2 each apples"))
        .isEqualTo(new Ingredient(new Fraction(2), "apples"));
  }

  @Test
  public void testParse_withMultiWordItem() {
    assertThat(Ingredient.parse("1 each green pepper"))
        .isEqualTo(new Ingredient(new Fraction(1), "green pepper"));
  }

  @Test
  public void testParse_withMixedNumberQuantity() {
    assertThat(Ingredient.parse("1 1/4 tbsp red pepper flakes"))
        .isEqualTo(new Ingredient(new Fraction(5, 4), "tbsp", "red pepper flakes"));
  }

  @Test
  public void testParse_withPreparationInstruction() {
    assertThat(Ingredient.parse("1 1/2 cups onion, finely chopped"))
        .isEqualTo(
            new Ingredient(new Fraction(3, 2), "cups", "onion", Arrays.asList("finely chopped")));
  }

  @Test
  public void testParse_withMultiplePreparationInstructions() {
    assertThat(Ingredient.parse("1/2 pound potatoes, diced, peeled"))
        .isEqualTo(
            new Ingredient(
                new Fraction(1, 2), "pound", "potatoes", Arrays.asList("diced", "peeled")));
  }

  @Test
  public void testParse_withTrailingComma_fails() {
    assertThatIllegalArgumentException().isThrownBy(() -> Ingredient.parse("1 cup chili peppers,"));
  }

  @Test
  public void testParse_withMissingUnit_fails() {
    assertThatIllegalArgumentException().isThrownBy(() -> Ingredient.parse("1 apple"));
  }

  @Test
  public void testToString() {
    assertThat(
            new Ingredient(
                new Fraction(1, 2), "pound", "potatoes", Arrays.asList("diced", "peeled")))
        .hasToString("1/2 pound potatoes, diced, peeled");
  }

  @Test
  public void testToString_implicitUnit() {
    assertThat(new Ingredient(new Fraction(3), "green apples")).hasToString("3 each green apples");
  }
}
