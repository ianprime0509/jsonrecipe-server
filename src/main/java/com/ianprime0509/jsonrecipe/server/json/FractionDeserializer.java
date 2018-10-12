package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
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
    return FractionUtils.parseFraction(fraction);
  }
}
