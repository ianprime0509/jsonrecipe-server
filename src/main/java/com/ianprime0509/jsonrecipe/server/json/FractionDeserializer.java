package com.ianprime0509.jsonrecipe.server.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
import java.io.IOException;
import org.apache.commons.math3.fraction.Fraction;

@SuppressWarnings("serial")
public class FractionDeserializer extends StdDeserializer<Fraction> {
  public FractionDeserializer() {
    super(Fraction.class);
  }

  @Override
  public Fraction deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String fraction = ctxt.readValue(p, String.class);
    try {
      return FractionUtils.parseFraction(fraction);
    } catch (IllegalArgumentException e) {
      throw JsonMappingException.from(ctxt, "Invalid fraction input", e);
    }
  }
}
