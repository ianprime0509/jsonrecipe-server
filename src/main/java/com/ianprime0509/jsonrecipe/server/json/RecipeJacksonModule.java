package com.ianprime0509.jsonrecipe.server.json;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import com.ianprime0509.jsonrecipe.server.entities.DirectionGroup;
import com.ianprime0509.jsonrecipe.server.entities.Either;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
import com.ianprime0509.jsonrecipe.server.entities.IngredientGroup;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.apache.commons.math3.fraction.Fraction;

/**
 * A Jackson module to register the serializers and deserializers needed for recipe-related classes.
 */
@SuppressWarnings("serial")
public class RecipeJacksonModule extends SimpleModule {
  private static abstract class RecipeMixin {
    @JsonCreator
    public RecipeMixin(@JsonProperty("title") String title,
        @JsonProperty("ingredients") List<Either<Ingredient, IngredientGroup>> ingredients,
        @JsonProperty("directions") List<Either<Direction, DirectionGroup>> directions) {}
  }

  private static abstract class IngredientMixin {
    @JsonCreator
    public IngredientMixin(@JsonProperty("quantity") Fraction quantity,
        @JsonProperty("unit") String unit, @JsonProperty("item") String item,
        @JsonProperty("preparation") List<String> preparation) {}
  }

  private static abstract class IngredientGroupMixin {
    @JsonCreator
    public IngredientGroupMixin(@JsonProperty("heading") String heading,
        @JsonProperty("ingredients") List<Ingredient> ingredients) {}
  }

  private static abstract class DirectionGroupMixin {
    @JsonCreator
    public DirectionGroupMixin(@JsonProperty("heading") String heading,
        @JsonProperty("directions") List<Direction> directions) {}
  }

  @SuppressWarnings("unchecked")
  public RecipeJacksonModule() {
    super("JSONRecipe", new Version(0, 1, 0, null, "com.ianprime0509.jsonrecipe", "server"));

    addSerializer(Direction.class, new DirectionSerializer());
    addDeserializer(Direction.class, new DirectionDeserializer());
    addSerializer(Fraction.class, new FractionSerializer());
    addDeserializer(Fraction.class, new FractionDeserializer());
    addSerializer((Class<Either<?, ?>>) (Class<?>) Either.class, new EitherSerializer());
    addDeserializer((Class<Either<?, ?>>) (Class<?>) Either.class, new EitherDeserializer());

    setMixInAnnotation(Recipe.class, RecipeMixin.class);
    setMixInAnnotation(Ingredient.class, IngredientMixin.class);
    setMixInAnnotation(IngredientGroup.class, IngredientGroupMixin.class);
    setMixInAnnotation(DirectionGroup.class, DirectionGroupMixin.class);
  }
}
