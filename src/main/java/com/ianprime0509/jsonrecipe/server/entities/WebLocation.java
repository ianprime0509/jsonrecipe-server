package com.ianprime0509.jsonrecipe.server.entities;

import java.net.URL;
import java.time.LocalDate;
import lombok.Data;
import lombok.NonNull;

/**
 * A website or other source accessible online.
 */
@Data
public class WebLocation implements Location {
  /**
   * The URL of the source material.
   */
  @NonNull
  private URL url;

  /**
   * The date on which the recipe was retrieved from the source.
   */
  private LocalDate retrievalDate;
}
