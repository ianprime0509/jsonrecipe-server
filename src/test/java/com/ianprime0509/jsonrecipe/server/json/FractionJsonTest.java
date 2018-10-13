package com.ianprime0509.jsonrecipe.server.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class FractionJsonTest {
  @Autowired
  private JacksonTester<Fraction> json;

  @Test
  public void testSerialize_wholeNumber() throws Exception {
    assertThat(json.write(new Fraction(4))).isEqualToJson("\"4\"");
  }

  @Test
  public void testSerialize_pureFraction() throws Exception {
    assertThat(json.write(new Fraction(3, 7))).isEqualToJson("\"3/7\"");
  }

  @Test
  public void testSerialize_mixedNumber() throws Exception {
    assertThat(json.write(new Fraction(5, 3))).isEqualToJson("\"1 2/3\"");
  }

  @Test
  public void testDeserialize_wholeNumber() throws Exception {
    assertThat(json.parse("\"3\"")).isEqualTo(new Fraction(3));
  }

  @Test
  public void testDeserialize_wholeNumberFromNumber() throws Exception {
    assertThat(json.parse("5")).isEqualTo(new Fraction(5));
  }

  @Test
  public void testDeserialize_pureFraction() throws Exception {
    assertThat(json.parse("\"2/3\"")).isEqualTo(new Fraction(2, 3));
  }

  @Test
  public void testDeserialize_mixedNumber() throws Exception {
    assertThat(json.parse("\"1 3/4\"")).isEqualTo(new Fraction(7, 4));
  }

  @Test
  public void testDeserialize_decimal_fails() throws Exception {
    assertThatExceptionOfType(Exception.class).isThrownBy(() -> json.parse("2.5"));
  }
}
