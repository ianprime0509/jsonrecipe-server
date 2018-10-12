package com.ianprime0509.jsonrecipe.server.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
import org.apache.commons.math3.fraction.Fraction;
import lombok.Data;
import lombok.NonNull;

/**
 * An ingredient in a recipe.
 */
@Data
public class Ingredient {
  /**
   * The regex for human-readable ingredient strings, as taken from the schema definition.
   *
   * <p>
   * Slight modifications have been made to make the pattern more amenable to extracting data.
   * Several capturing groups are used for this purpose:
   * <ol>
   * <li>The fraction string, which can be parsed by
   * {@link FractionUtils#parseFraction(String)}.</li>
   * <li>The unit.</li>
   * <li>The item.</li>
   * <li>Each preparation direction, as a separate capturing group.</li>
   * </ol>
   * </p>
   */
  public static final Pattern INGREDIENT_PATTERN = Pattern.compile(
      "([1-9][0-9]* ?(:?(:?/| [1-9][0-9]* ?/) ?[1-9][0-9]*)?) ([^ ]+) ([^,]*[^, ])(:?, ([^,]*[^, ]))*");

  /**
   * The quantity of the specified item, in terms of the specified unit.
   */
  @NonNull
  private Fraction quantity;

  /**
   * The unit of measurement which the specified quantity uses (e.g. "cup").
   */
  @NonNull
  private String unit;

  /**
   * The item of which this ingredient consists (e.g. "apple").
   */
  @NonNull
  private String item;

  /**
   * How the ingredient should be prepared prior to use (e.g. "chopped").
   */
  @NonNull
  private List<String> preparation;

  /**
   * Parses a human-readable ingredient string.
   *
   * <p>
   * The description format consists of four parts, one of which is optional:
   * <ol>
   * <li>The quantity, as a fractional string. The quantity must have a whole-number part, a
   * fractional part or both.</li>
   * <li>The unit. This must be specified; use 'each' to indicate that the natural "counting" unit
   * should be used.</li>
   * <li>The item (e.g. flour). The item name must not contain a comma, since this is used to denote
   * the start of the preparation instructions.</li>
   * <li>Preparation instructions (optional). These are given as a comma-separated list, beginning
   * with a comma after the material.</li>
   * </ol>
   * </p>
   *
   * @param ingredient the human-readable ingredient string to parse
   * @return the parsed ingredient
   * @throws IllegalArgumentException if the ingredient string is not in a valid format
   */
  public static Ingredient parse(String ingredient) {
    Matcher matcher = INGREDIENT_PATTERN.matcher(ingredient);
    Fraction quantity = FractionUtils.parseFraction(matcher.group(1));
    String unit = matcher.group(2);
    String item = matcher.group(3);
    List<String> preparation = new ArrayList<>();
    for (int i = 4; i <= matcher.groupCount(); i++) {
      preparation.add(matcher.group(i));
    }

    return new Ingredient(quantity, unit, item, preparation);
  }

  /**
   * Formats the ingredient as a human-readable string.
   *
   * @return the ingredient, as a human-readable string in the format understood by
   *         {@link #parse(String)}.
   */
  @Override
  public String toString() {
    return FractionUtils.toMixedNumberString(quantity) + " " + unit + " " + item
        + preparation.stream().map(s -> ", " + s).collect(Collectors.joining());
  }
}
