package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Direction;

@SuppressWarnings("serial")
public class DirectionDeserializer extends StdDeserializer<Direction> {
  public DirectionDeserializer() {
    super(Direction.class);
  }

  @Override
  public Direction deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    return new Direction(p.getText());
  }
}
