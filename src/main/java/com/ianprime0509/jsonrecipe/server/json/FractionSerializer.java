package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
import org.apache.commons.math3.fraction.Fraction;

@SuppressWarnings("serial")
public class FractionSerializer extends StdSerializer<Fraction> {
  public FractionSerializer() {
    super(Fraction.class);
  }

  @Override
  public void serialize(Fraction value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(FractionUtils.toMixedNumberString(value));
  }
}
