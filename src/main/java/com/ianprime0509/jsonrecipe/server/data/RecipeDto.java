package com.ianprime0509.jsonrecipe.server.data;

import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NonNull;

/**
 * A wrapper that holds a {@link Recipe} along with an ID so that it can be stored in a database.
 */
@Data
public class RecipeDto {
  /**
   * The ID of the recipe in the database.
   */
  @Id
  private String id;

  /**
   * The actual recipe object to be stored.
   */
  @NonNull
  private Recipe contents;
}
