package com.ianprime0509.jsonrecipe.server.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ianprime0509.jsonrecipe.server.json.DirectionDeserializer;
import com.ianprime0509.jsonrecipe.server.json.DirectionSerializer;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonSerialize(using = DirectionSerializer.class)
@JsonDeserialize(using = DirectionDeserializer.class)
public class Direction {
  @NonNull
  private String text;
}
