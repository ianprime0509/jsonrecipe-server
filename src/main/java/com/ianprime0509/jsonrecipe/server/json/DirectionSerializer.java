package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ianprime0509.jsonrecipe.server.entities.Direction;

@SuppressWarnings("serial")
public class DirectionSerializer extends StdSerializer<Direction> {
  public DirectionSerializer() {
    super(Direction.class);
  }

  @Override
  public void serialize(Direction value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(value.getText());
  }
}
