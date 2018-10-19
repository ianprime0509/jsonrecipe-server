package com.ianprime0509.jsonrecipe.server.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/** Tests for the {@link FractionUtils} class. */
@RunWith(SpringRunner.class)
public class FractionTest {
  @Test
  public void testParse_wholeNumber() {
    assertThat(FractionUtils.parseFraction("2")).isEqualTo(new Fraction(2));
  }

  @Test
  public void testParse_pureFraction() {
    assertThat(FractionUtils.parseFraction("3/4")).isEqualTo(new Fraction(3, 4));
  }

  @Test
  public void testParse_mixedNumber() {
    assertThat(FractionUtils.parseFraction("1 1/4")).isEqualTo(new Fraction(5, 4));
  }

  @Test
  public void testParse_pureFractionWithOptionalSpaces() {
    assertThat(FractionUtils.parseFraction("1 / 3")).isEqualTo(new Fraction(1, 3));
  }

  @Test
  public void testParse_mixedNumberWithOptionalSpaces() {
    assertThat(FractionUtils.parseFraction("2 1 / 4")).isEqualTo(new Fraction(9, 4));
  }

  @Test
  public void testParse_mixedNumberWithTooManySpaces_fails() {
    assertThatIllegalArgumentException().isThrownBy(() -> FractionUtils.parseFraction("1  1/2"));
  }

  @Test
  public void testToMixedNumberString_wholeNumber() {
    assertThat(FractionUtils.toMixedNumberString(new Fraction(5))).isEqualTo("5");
  }

  @Test
  public void testToMixedNumberString_pureFraction() {
    assertThat(FractionUtils.toMixedNumberString(new Fraction(5, 6))).isEqualTo("5/6");
  }

  @Test
  public void testToMixedNumberString_mixedNumber() {
    assertThat(FractionUtils.toMixedNumberString(new Fraction(15, 4))).isEqualTo("3 3/4");
  }
}
