package com.ianprime0509.jsonrecipe.server.data;

import org.apache.commons.math3.fraction.Fraction;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter to read {@link Fraction} objects from a Mongo document.
 */
public class FractionReadConverter implements Converter<Document, Fraction> {
  @Override
  public Fraction convert(Document source) {
    return new Fraction(source.getInteger("numerator"), source.getInteger("denominator"));
  }
}
