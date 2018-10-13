package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.*;
import com.ianprime0509.jsonrecipe.server.entities.Direction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class DirectionJsonTest {
  @Autowired
  private JacksonTester<Direction> json;

  @Test
  public void testSerialize() throws Exception {
    assertThat(json.write(new Direction("This is the first step.")))
        .isEqualToJson("\"This is the first step.\"");
  }

  @Test
  public void testDeserialize() throws Exception {
    assertThat(json.parse("\"Deserialize me.\"")).isEqualTo(new Direction("Deserialize me."));
  }
}
