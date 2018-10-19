package com.ianprime0509.jsonrecipe.server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math3.fraction.Fraction;

/** A helper class for working with {@link Fraction} objects. */
public final class FractionUtils {
  /** The pattern representing the expected mixed number format. */
  public static final Pattern FRACTION_PATTERN =
      Pattern.compile(
          "([1-9][0-9]*)|([1-9][0-9]*) ?/ ?([1-9][0-9]*)|([1-9][0-9]*) ([1-9][0-9]*) ?/ ?([1-9][0-9]*)");

  private FractionUtils() {}

  /**
   * Parses a fraction from a mixed number string.
   *
   * @param fraction the mixed number string to parse
   * @return the fraction that corresponds to the given string
   * @throws IllegalArgumentException if the given string is not a valid mixed number
   */
  public static Fraction parseFraction(String fraction) {
    Matcher matcher = FRACTION_PATTERN.matcher(fraction);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Not a valid fraction or mixed number.");
    }

    int whole = 0;
    int numerator = 0;
    int denominator = 1;
    if (matcher.group(1) != null) {
      // Just whole number.
      whole = Integer.parseInt(matcher.group(1));
    } else if (matcher.group(2) != null && matcher.group(3) != null) {
      // Just numerator and denominator.
      numerator = Integer.parseInt(matcher.group(2));
      denominator = Integer.parseInt(matcher.group(3));
    } else {
      // All parts given.
      whole = Integer.parseInt(matcher.group(4));
      numerator = Integer.parseInt(matcher.group(5));
      denominator = Integer.parseInt(matcher.group(6));
    }

    return new Fraction(numerator + whole * denominator, denominator);
  }

  /**
   * Converts a fraction to a mixed number string.
   *
   * @param fraction the fraction to convert
   * @return a mixed number string, such as "2", "1/2" or "3 3/4".
   */
  public static String toMixedNumberString(Fraction fraction) {
    // Serialize as a mixed number.
    int denominator = fraction.getDenominator();
    int whole = fraction.getNumerator() / denominator;
    int numerator = fraction.getNumerator() - whole * denominator;

    // We need to make sure we don't include useless information, like 0 for the whole number part.
    StringBuilder formatted = new StringBuilder();
    if (whole != 0) {
      formatted.append(whole);
    }
    if (numerator != 0) {
      if (formatted.length() != 0) {
        formatted.append(' ');
      }
      formatted.append(numerator + "/" + denominator);
    }

    return formatted.toString();
  }
}
