package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import com.ianprime0509.jsonrecipe.server.entities.DirectionGroup;
import com.ianprime0509.jsonrecipe.server.entities.Either;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
import com.ianprime0509.jsonrecipe.server.entities.IngredientGroup;
import java.util.Arrays;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class EitherJsonTest {
  @Autowired private JacksonTester<Either<Ingredient, IngredientGroup>> ingredientOrGroupJson;

  @Autowired private JacksonTester<Either<Direction, DirectionGroup>> directionOrGroupJson;

  @Test
  public void testSerialize_ingredientOrGroupWithIngredient() throws Exception {
    assertThat(ingredientOrGroupJson.write(Either.left(new Ingredient(new Fraction(2), "apples"))))
        .isEqualToJson(
            "{\"quantity\": \"2\", \"unit\": \"each\", \"item\": \"apples\", \"preparation\": []}");
  }

  @Test
  public void testSerialize_ingredientOrGroupWithGroup() throws Exception {
    assertThat(
            ingredientOrGroupJson.write(
                Either.right(
                    new IngredientGroup(
                        "Test group", Arrays.asList(new Ingredient(new Fraction(2), "apples"))))))
        .isEqualToJson(
            "{\"heading\": \"Test group\", \"ingredients\": [{\"quantity\": \"2\", \"unit\": \"each\", \"item\": \"apples\", \"preparation\": []}]}");
  }

  @Test
  public void testSerialize_directionOrGroupWithDirection() throws Exception {
    assertThat(directionOrGroupJson.write(Either.left(new Direction("This is a test."))))
        .isEqualToJson("\"This is a test.\"");
  }

  @Test
  public void testSerialize_directionOrGroupWithGroup() throws Exception {
    assertThat(
            directionOrGroupJson.write(
                Either.right(
                    new DirectionGroup(
                        "Group of directions", Arrays.asList(new Direction("Eat apples."))))))
        .isEqualTo("{\"heading\": \"Group of directions\", \"directions\": [\"Eat apples.\"]}");
  }

  @Test
  public void testDeserialize_ingredientOrGroupWithIngredientString() throws Exception {
    assertThat(ingredientOrGroupJson.parse("\"2 each apples\""))
        .isEqualTo(Either.left(new Ingredient(new Fraction(2), "apples")));
  }

  @Test
  public void testDeserialize_ingredientOrGroupWithIngredientObject() throws Exception {
    assertThat(ingredientOrGroupJson.parse("{\"quantity\": \"2\", \"item\": \"apples\"}"))
        .isEqualTo(Either.left(new Ingredient(new Fraction(2), "apples")));
  }

  @Test
  public void testDeserialize_ingredientOrGroupWithGroup() throws Exception {
    assertThat(
            ingredientOrGroupJson.parse(
                "{\"heading\": \"Test group\", \"ingredients\": [\"2 each apples\"]}"))
        .isEqualTo(
            Either.right(
                new IngredientGroup(
                    "Test group", Arrays.asList(new Ingredient(new Fraction(2), "apples")))));
  }

  @Test
  public void testDeserialize_ingredientOrGroupWithEmptyObject_fails() throws Exception {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> ingredientOrGroupJson.parse("{}"));
  }

  @Test
  public void testDeserialize_directionOrGroupWithDirection() throws Exception {
    assertThat(directionOrGroupJson.parse("\"Mix flour and water.\""))
        .isEqualTo(Either.left(new Direction("Mix flour and water.")));
  }

  @Test
  public void testDeserialize_directionOrGroupWithGroup() throws Exception {
    assertThat(
            directionOrGroupJson.parse(
                "{\"heading\": \"Group of directions\", \"directions\": [\"Eat apples.\"]}"))
        .isEqualTo(
            Either.right(
                new DirectionGroup(
                    "Group of directions", Arrays.asList(new Direction("Eat apples.")))));
  }

  @Test
  public void testDeserialize_directionOrGroupWithEmptyObject_fails() throws Exception {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> directionOrGroupJson.parse("{}"));
  }
}
