package com.ianprime0509.jsonrecipe.server.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ianprime0509.jsonrecipe.server.entities.Either;

/**
 * A Jackson deserializer for {@link Either}.
 *
 * <p>
 * It is important to note that, when deserializing JSON data as an {@link Either}, this
 * implementation will attempt to deserialize as the left type before attempting to deserialize as
 * the right type. Because Jackson performs certain conversions automatically, such as deserializing
 * a number into a string (1 -> "1"), this implies that attempting to deserialize an
 * {@code Either<String, Integer>} will never result in the right type ({@code Integer}) being
 * found. Other such cases may occur when other implicit conversions are possible, or if the
 * deserializer for the left type is too lax.
 * </p>
 */
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
    JavaType wrapperType = ctxt.getContextualType();
    EitherDeserializer deserializer = new EitherDeserializer();
    deserializer.rightType = wrapperType.containedType(0);
    deserializer.leftType = wrapperType.containedType(1);
    System.out.println("Right: " + deserializer.rightType + " Left: " + deserializer.leftType);
    return deserializer;
  }

  @Override
  public Either<?, ?> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectCodec codec = p.getCodec();
    // We need to read in the current parse tree so it can be reused; we will potentially need to
    // traverse the tree twice, which will not be possible if we don't reuse this tree (there is no
    // way to "reset" a JsonParser to an earlier position).
    JsonNode rootNode = p.readValueAsTree();

    try {
      JsonParser rootParser = codec.treeAsTokens(rootNode);
      // It doesn't seem to be documented anywhere, but the parser returned from the treeAsTokens
      // method does not point at any token initially, but all deserializers expect the parser to be
      // pointing at the first token of whatever they have to deserialize. Thus, we need to
      // explicitly tell our parser to point at the first token of the tree.
      rootParser.nextToken();
      return Either.left(ctxt.readValue(rootParser, leftType));
    } catch (JsonProcessingException e) {
      // Not of the left type; try the right type.
      JsonParser rootParser = codec.treeAsTokens(rootNode);
      rootParser.nextToken();
      return Either.right(ctxt.readValue(rootParser, rightType));
    }
  }
}
