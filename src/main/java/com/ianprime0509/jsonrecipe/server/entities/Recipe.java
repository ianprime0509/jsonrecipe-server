package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * A culinary recipe, consisting of a list of ingredients and directions for combining those
 * ingredients to create a food.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Recipe extends ResourceSupport {
  /**
   * The ID of the recipe in the database.
   */
  @Id
  String recipeId;

  /**
   * The title of the recipe.
   */
  @NonNull
  String title;

  /**
   * The source of the recipe (e.g. the person who wrote it and the book where it was found).
   */
  Source source;

  /**
   * The ingredients of the recipe.
   */
  @NonNull
  List<Either<Ingredient, IngredientGroup>> ingredients;

  /**
   * The instructions for preparing the finished product from the ingredients.
   */
  @NonNull
  List<Either<Direction, DirectionGroup>> directions;
}
