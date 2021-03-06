package com.ianprime0509.jsonrecipe.server.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import java.io.IOException;

@SuppressWarnings("serial")
public class DirectionDeserializer extends StdDeserializer<Direction> {
  public DirectionDeserializer() {
    super(Direction.class);
  }

  @Override
  public Direction deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    return new Direction(ctxt.readValue(p, String.class));
  }
}
