package com.ianprime0509.jsonrecipe.server.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Location;
import com.ianprime0509.jsonrecipe.server.entities.WebLocation;
import java.io.IOException;

/**
 * A deserializer for {@link Location}s that resolves the correct concrete type (web, book, etc.).
 */
@SuppressWarnings("serial")
public class LocationDeserializer extends StdDeserializer<Location> {
  public LocationDeserializer() {
    super(Location.class);
  }

  @Override
  public Location deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectCodec codec = p.getCodec();
    JsonNode rootNode = codec.readTree(p);

    // We should always be able to disambiguate concrete location type by looking at the presence of
    // a type-specific data field.
    if (rootNode.has("url")) {
      return codec.treeToValue(rootNode, WebLocation.class);
    }
    throw JsonMappingException.from(ctxt, "Unrecognized location type.");
  }
}
