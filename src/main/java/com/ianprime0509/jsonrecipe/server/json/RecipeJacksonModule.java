package com.ianprime0509.jsonrecipe.server.json;

import java.net.URL;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import com.ianprime0509.jsonrecipe.server.entities.DirectionGroup;
import com.ianprime0509.jsonrecipe.server.entities.Either;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
import com.ianprime0509.jsonrecipe.server.entities.IngredientGroup;
import com.ianprime0509.jsonrecipe.server.entities.Location;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.entities.Source;
import com.ianprime0509.jsonrecipe.server.entities.WebLocation;
import org.apache.commons.math3.fraction.Fraction;

/**
 * A Jackson module to register the serializers and deserializers needed for recipe-related classes.
 */
@SuppressWarnings("serial")
public class RecipeJacksonModule extends SimpleModule {
  // The mixins here are what enable us to decouple our entity classes from Jackson-specific details
  // provided through annotations. Basically, what we have to do is create a "skeleton" of each
  // class we wish to annotate, annotating the skeleton with the annotations we want, and then
  // register it as a mixin for the actual entity class. Jackson will treat the annotations on the
  // mixin class as if they had been applied to the entity class.
  private static abstract class RecipeMixin {
    @JsonIgnore
    private String recipeId;

    @JsonCreator
    public RecipeMixin(@JsonProperty("title") String title,
        @JsonProperty("ingredients") List<Either<Ingredient, IngredientGroup>> ingredients,
        @JsonProperty("directions") List<Either<Direction, DirectionGroup>> directions) {}
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

  private static abstract class SourceMixin {
    @JsonCreator
    public SourceMixin(@JsonProperty("author") String author) {}
  }

  private static abstract class WebLocationMixin {
    @JsonCreator
    public WebLocationMixin(@JsonProperty("url") URL url) {}
  }

  @SuppressWarnings("unchecked")
  public RecipeJacksonModule() {
    super("JSONRecipe", new Version(0, 1, 0, null, "com.ianprime0509.jsonrecipe", "server"));

    addSerializer(Direction.class, new DirectionSerializer());
    addDeserializer(Direction.class, new DirectionDeserializer());
    addSerializer(Fraction.class, new FractionSerializer());
    addDeserializer(Fraction.class, new FractionDeserializer());
    // We need to use this weird unchecked cast here to get around limitations of Java generics.
    addSerializer((Class<Either<?, ?>>) (Class<?>) Either.class, new EitherSerializer());
    addDeserializer((Class<Either<?, ?>>) (Class<?>) Either.class, new EitherDeserializer());
    addDeserializer(Location.class, new LocationDeserializer());
    addDeserializer(Ingredient.class, new IngredientDeserializer());

    setMixInAnnotation(Recipe.class, RecipeMixin.class);
    setMixInAnnotation(IngredientGroup.class, IngredientGroupMixin.class);
    setMixInAnnotation(DirectionGroup.class, DirectionGroupMixin.class);
    setMixInAnnotation(Source.class, SourceMixin.class);
    setMixInAnnotation(WebLocation.class, WebLocationMixin.class);
  }
}
