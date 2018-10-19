package com.ianprime0509.jsonrecipe.server.entities;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

/** A group of directions under a single heading. */
@Data
public class DirectionGroup {
  /** The heading for this group of directions. */
  @NonNull private String heading;

  /** The directions under this heading. */
  @NonNull private List<Direction> directions;
}
