package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import java.net.URL;
import java.time.LocalDate;
import com.ianprime0509.jsonrecipe.server.entities.Location;
import com.ianprime0509.jsonrecipe.server.entities.WebLocation;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class LocationJsonTest {
  @Autowired
  private JacksonTester<Location> json;

  @Test
  @Ignore
  public void testSerialize_webLocationWithUrlOnly() throws Exception {
    assertThat(json.write(new WebLocation(new URL("https://www.gnu.org"))))
        .isEqualToJson("{\"url\": \"https://www.gnu.org\"}");
  }

  @Test
  @Ignore
  public void testSerialize_completeWebLocation() throws Exception {
    assertThat(json
        .write(new WebLocation(new URL("https://www.duckduckgo.com"), LocalDate.of(2018, 10, 12))))
            .isEqualToJson(
                "{\"url\": \"https://www.duckduckgo.com\", \"retrievalDate\": \"2018-10-12\"}");
  }

  @Test
  public void testDeserialize_webLocationWithUrlOnly() throws Exception {
    assertThat(json.parse("{\"url\": \"https://www.gnu.org\"}"))
        .isEqualTo(new WebLocation(new URL("https://www.gnu.org")));
  }

  @Test
  public void testDeserialize_completeWebLocation() throws Exception {
    assertThat(
        json.parse("{\"url\": \"https://www.duckduckgo.com\", \"retrievalDate\": \"2018-10-12\"}"))
            .isEqualTo(
                new WebLocation(new URL("https://www.duckduckgo.com"), LocalDate.of(2018, 10, 12)));
  }

  @Test
  public void testDeserialize_unknownLocationType_fails() throws Exception {
    assertThatExceptionOfType(Exception.class)
        .isThrownBy(() -> json.parse("\"location\": \"somewhere\""));
  }
}
