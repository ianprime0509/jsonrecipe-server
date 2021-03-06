package com.ianprime0509.jsonrecipe.server.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Ingredient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.fraction.Fraction;

@SuppressWarnings("serial")
public class IngredientDeserializer extends StdDeserializer<Ingredient> {
  public IngredientDeserializer() {
    super(Ingredient.class);
  }

  @Override
  public Ingredient deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode rootNode = p.readValueAsTree();
    if (rootNode.isTextual()) {
      // Parse ingredient as human-readable string.
      try {
        return Ingredient.parse(rootNode.asText());
      } catch (IllegalArgumentException e) {
        throw JsonMappingException.from(ctxt, "Invalid ingredient string.", e);
      }
    }

    // Parse ingredient manually.
    ObjectCodec codec = p.getCodec();
    JsonNode quantityNode = rootNode.get("quantity");
    if (quantityNode == null) {
      throw JsonMappingException.from(ctxt, "Quantity is required in ingredient objects.");
    }
    Fraction quantity = codec.treeAsTokens(rootNode.get("quantity")).readValueAs(Fraction.class);

    // Provide default unit of "each".
    String unit = "each";
    JsonNode unitNode = rootNode.get("unit");
    if (unitNode != null && !unitNode.isNull()) {
      unit = unitNode.asText(unit);
    }

    JsonNode itemNode = rootNode.get("item");
    if (itemNode == null) {
      throw JsonMappingException.from(ctxt, "Item is required in ingredient objects.");
    }
    String item = itemNode.asText();

    // Provide default empty array.
    List<String> preparation = new ArrayList<>();
    JsonNode preparationNode = rootNode.get("preparation");
    if (preparationNode != null && preparationNode.isArray()) {
      preparationNode.forEach(element -> preparation.add(element.asText()));
    }

    return new Ingredient(quantity, unit, item, preparation);
  }
}
