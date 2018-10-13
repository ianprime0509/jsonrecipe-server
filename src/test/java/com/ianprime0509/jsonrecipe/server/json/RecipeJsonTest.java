package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import com.ianprime0509.jsonrecipe.server.entities.Either;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.entities.Source;
import com.ianprime0509.jsonrecipe.server.entities.WebLocation;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class RecipeJsonTest {
  @Autowired
  private JacksonTester<Recipe> json;

  /**
   * The expected object for the German potato salad recipe in this directory.
   */
  private Recipe germanPotatoSalad;

  @Before
  public void setUp() throws Exception {
    germanPotatoSalad = Recipe.builder().title("Authentic German potato salad")
        .source(new Source("Angela Louise Miller",
            new WebLocation(
                new URL("https://www.allrecipes.com/recipe/83097/authentic-german-potato-salad/"),
                LocalDate.of(2018, 8, 11))))
        .ingredients(Arrays.asList(
            Either.left(new Ingredient(new Fraction(3), "cups", "potatoes",
                Arrays.asList("diced", "peeled"))),
            Either.left(new Ingredient(new Fraction(4), "slices", "bacon")),
            Either.left(
                new Ingredient(new Fraction(1), "each", "small onion", Arrays.asList("diced"))),
            Either.left(new Ingredient(new Fraction(1, 4), "cup", "white vinegar")),
            Either.left(new Ingredient(new Fraction(2), "tablespoons", "water")),
            Either.left(new Ingredient(new Fraction(3), "tablespoons", "white sugar")),
            Either.left(new Ingredient(new Fraction(1), "teaspoon", "salt")),
            Either.left(new Ingredient(new Fraction(1, 8), "teaspoon", "black pepper",
                Arrays.asList("ground"))),
            Either.left(new Ingredient(new Fraction(1), "tablespoon", "fresh parsley",
                Arrays.asList("chopped")))))
        .directions(Arrays.asList(Either.left(new Direction(
            "Place the potatoes into a pot, and fill with enough water to cover. Bring to a boil, and cook for about 10 minutes, or until easily pierced with a fork. Drain, and set aside to cool.")),
            Either.left(new Direction(
                "Place the bacon in a large deep skillet over medium-high heat. Fry until browned and crisp, turning as needed. Remove from the pan and set aside.")),
            Either.left(new Direction(
                "Add onion to the bacon grease, and cook over medium heat until browned. Add the vinegar, water, sugar, salt and pepper to the pan. Bring to a boil, then add the potatoes and parsley. Crumble in half of the bacon. Heat through, then transfer to a serving dish. Crumble the remaining bacon over the top, and serve warm."))))
        .build();
  }

  @Test
  public void testSerialize_fullRecipe() throws Exception {
    assertThat(json.write(germanPotatoSalad)).isEqualToJson("german-potato-salad-expanded.json");
  }

  @Test
  public void testDeserialize_fullRecipe() throws Exception {
    assertThat(json.read("german-potato-salad.json")).isEqualTo(germanPotatoSalad);
  }

  @Test
  public void testDeserialize_expandedFullRecipe() throws Exception {
    assertThat(json.read("german-potato-salad-expanded.json")).isEqualTo(germanPotatoSalad);
  }
}
