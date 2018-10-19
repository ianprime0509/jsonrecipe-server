package com.ianprime0509.jsonrecipe.server.data;

import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * A wrapper that holds a {@link Recipe} along with an ID so that it can be stored in a database.
 */
@Data
@RequiredArgsConstructor(onConstructor_ = @PersistenceConstructor)
@AllArgsConstructor
public class RecipeDto {
  /** The ID of the recipe in the database. */
  @Id private String id;

  /** The actual recipe object to be stored. */
  @NonNull private Recipe contents;
}
