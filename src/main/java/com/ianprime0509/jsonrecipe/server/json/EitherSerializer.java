package com.ianprime0509.jsonrecipe.server.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ianprime0509.jsonrecipe.server.entities.Either;
import java.io.IOException;

@SuppressWarnings("serial")
public class EitherSerializer extends StdSerializer<Either<?, ?>> {
  public EitherSerializer() {
    // We need the dummy 'false' parameter here due to what I feel is a design
    // flaw in the Jackson library.
    super(Either.class, false);
  }

  @Override
  public void serialize(Either<?, ?> value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    provider.defaultSerializeValue(value.asEither(), gen);
  }
}
