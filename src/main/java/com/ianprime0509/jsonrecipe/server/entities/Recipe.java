package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class Recipe extends ResourceSupport {
  @Id
  String recipeId;

  @NonNull
  String title;

  @NonNull
  List<Either<Direction, DirectionGroup>> directions;
}
