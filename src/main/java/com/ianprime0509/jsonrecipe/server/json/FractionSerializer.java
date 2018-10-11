package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.math3.fraction.Fraction;

@SuppressWarnings("serial")
public class FractionSerializer extends StdSerializer<Fraction> {
  public FractionSerializer() {
    super(Fraction.class);
  }

  @Override
  public void serialize(Fraction value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    // Serialize as a mixed number.
    int denominator = value.getDenominator();
    int whole = value.getNumerator() / denominator;
    int numerator = value.getNumerator() - whole * denominator;

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

    gen.writeString(formatted.toString());
  }
}
