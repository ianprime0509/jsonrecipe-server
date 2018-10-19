package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
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
public class IngredientJsonTest {
  @Autowired private JacksonTester<Ingredient> json;

  @Test
  public void testSerialize() throws Exception {
    assertThat(
            json.write(
                new Ingredient(
                    new Fraction(5, 2), "cups", "flour", Arrays.asList("finely ground"))))
        .isEqualToJson(
            "{\"quantity\": \"2 1/2\", \"unit\": \"cups\", \"item\": \"flour\", \"preparation\": [\"finely ground\"]}");
  }

  @Test
  public void testSerialize_withImplicitUnitAndPreparation() throws Exception {
    assertThat(json.write(new Ingredient(new Fraction(2), "apples")))
        .isEqualToJson(
            "{\"quantity\": \"2\", \"unit\": \"each\", \"item\": \"apples\", \"preparation\": []}");
  }

  @Test
  public void testDeserialize_fromFullFormat() throws Exception {
    // Java 12 raw string literals would be awesome here...
    assertThat(
            json.parse(
                "{\"quantity\": \"2 1/2\", \"unit\": \"cups\", \"item\": \"flour\", \"preparation\": [\"finely ground\"]}"))
        .isEqualTo(
            new Ingredient(new Fraction(5, 2), "cups", "flour", Arrays.asList("finely ground")));
  }

  @Test
  public void testDeserialize_fromFullFormatWithImplicitUnitAndPreparation() throws Exception {
    assertThat(json.parse("{\"quantity\": 2, \"item\": \"apples\"}"))
        .isEqualTo(new Ingredient(new Fraction(2), "each", "apples", Arrays.asList()));
  }

  @Test
  public void testDeserialize_fromIngredientString() throws Exception {
    assertThat(json.parse("\"2 each apples\""))
        .isEqualTo(new Ingredient(new Fraction(2), "apples"));
  }

  @Test
  public void testDeserialize_fromIngredientStringWithAllFields() throws Exception {
    assertThat(json.parse("\"1 1/2 cups potatoes, diced, boiled\""))
        .isEqualTo(
            new Ingredient(
                new Fraction(3, 2), "cups", "potatoes", Arrays.asList("diced", "boiled")));
  }

  @Test
  public void testDeserialize_fromInvalidIngredientString_fails() throws Exception {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> json.parse("\"1 apple\""));
  }

  @Test
  public void testDeserialize_fromObjectMissingQuantity_fails() throws Exception {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> json.parse("{\"item\": \"green peas\"}"));
  }

  @Test
  public void testDeserialize_fromObjectMissingItem_fails() throws Exception {
    assertThatExceptionOfType(JsonMappingException.class)
        .isThrownBy(() -> json.parse("{\"quantity\": \"2 1/2\", \"unit\": \"cups\"}"));
  }
}
