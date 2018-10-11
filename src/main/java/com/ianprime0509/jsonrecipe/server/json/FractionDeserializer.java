package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.math3.fraction.Fraction;

@SuppressWarnings("serial")
public class FractionDeserializer extends StdDeserializer<Fraction> {
  private static final Pattern FRACTION = Pattern.compile(
      "([1-9][0-9]*)|([1-9][0-9]*) ?/ ?([1-9][0-9]*)|([1-9][0-9]*) ([1-9][0-9]*) ?/ ?([1-9][0-9]*)");

  public FractionDeserializer() {
    super(Fraction.class);
  }

  @Override
  public Fraction deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String fraction = ctxt.readValue(p, String.class);
    Matcher matcher = FRACTION.matcher(fraction);
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
}
