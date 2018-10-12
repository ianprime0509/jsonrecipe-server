package com.ianprime0509.jsonrecipe.server.entities;

import static org.junit.Assert.assertEquals;
import com.ianprime0509.jsonrecipe.server.util.FractionUtils;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for the {@link FractionUtils} class.
 */
@RunWith(SpringRunner.class)
public class FractionTest {
  @Test
  public void testParse_wholeNumber() {
    assertEquals(new Fraction(2), FractionUtils.parseFraction("2"));
  }

  @Test
  public void testParse_pureFraction() {
    assertEquals(new Fraction(3, 4), FractionUtils.parseFraction("3/4"));
  }

  @Test
  public void testParse_mixedNumber() {
    assertEquals(new Fraction(5, 4), FractionUtils.parseFraction("1 1/4"));
  }

  @Test
  public void testParse_pureFractionWithOptionalSpaces() {
    assertEquals(new Fraction(1, 3), FractionUtils.parseFraction("1 / 3"));
  }

  @Test
  public void testParse_mixedNumberWithOptionalSpaces() {
    assertEquals(new Fraction(9, 4), FractionUtils.parseFraction("2 1 / 4"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParse_mixedNumberWithTooManySpaces_fails() {
    FractionUtils.parseFraction("1  1/2");
  }

  @Test
  public void testToMixedNumberString_wholeNumber() {
    assertEquals("5", FractionUtils.toMixedNumberString(new Fraction(5)));
  }

  @Test
  public void testToMixedNumberString_pureFraction() {
    assertEquals("5/6", FractionUtils.toMixedNumberString(new Fraction(5, 6)));
  }

  @Test
  public void testToMixedNumberString_mixedNumber() {
    assertEquals("3 3/4", FractionUtils.toMixedNumberString(new Fraction(15, 4)));
  }
}
