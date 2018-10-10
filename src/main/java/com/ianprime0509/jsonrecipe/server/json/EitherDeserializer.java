package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Either;

@SuppressWarnings("serial")
public class EitherDeserializer extends StdDeserializer<Either<?, ?>>
    implements ContextualDeserializer {
  private JavaType leftType;
  private JavaType rightType;

  public EitherDeserializer() {
    super(Either.class);
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
      throws JsonMappingException {
    // Thanks to https://stackoverflow.com/a/36223769 for guidance on this implementation.
    JavaType wrapperType = property == null ? ctxt.getContextualType() : property.getType();
    EitherDeserializer deserializer = new EitherDeserializer();
    deserializer.rightType = wrapperType.containedType(0);
    deserializer.leftType = wrapperType.containedType(1);
    return deserializer;
  }

  @Override
  public Either<?, ?> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    try {
      return Either.right(ctxt.readValue(p, rightType));
    } catch (JsonProcessingException e) {
      // Not of the right type; try the left type.
      return Either.left(ctxt.readValue(p, leftType));
    }
  }
}
