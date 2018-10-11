package com.ianprime0509.jsonrecipe.server.entities;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A wrapper for an object that can be one of two types (the "left" or the "right" type).
 *
 * @param <L> the left type
 * @param <R> the right type
 */
public abstract class Either<L, R> {
  @Value(staticConstructor = "of")
  @EqualsAndHashCode(callSuper = false)
  private static class Left<L, R> extends Either<L, R> {
    L contents;

    @Override
    public Object asEither() {
      return contents;
    }
  }

  @Value(staticConstructor = "of")
  @EqualsAndHashCode(callSuper = false)
  private static class Right<L, R> extends Either<L, R> {
    R contents;

    @Override
    public Object asEither() {
      return contents;
    }
  }

  private Either() {}

  /**
   * Returns an {@link Either} wrapping the given contents, of the right type.
   *
   * @param contents the object to be wrapped
   */
  public static <L, R> Either<L, R> right(R contents) {
    return Right.of(contents);
  }

  /**
   * Returns an {@link Either} wrapping the given contents, of the left type.
   *
   * @param contents the object to be wrapped
   */
  public static <L, R> Either<L, R> left(L contents) {
    return Left.of(contents);
  }

  /**
   * Returns whether the wrapped object is of the left type.
   */
  public boolean isLeft() {
    return this instanceof Left;
  }

  /**
   * Returns whether the wrapped object is of the right type.
   */
  public boolean isRight() {
    return this instanceof Right;
  }

  /**
   * Gets the wrapped object, regardless of its type.
   *
   * @return the wrapped object
   */
  public abstract Object asEither();

  /**
   * Returns the wrapped object as an instance of the right type.
   *
   * @return the wrapped object
   * @throws IllegalStateException if the wrapped object is not of the right type
   */
  public R asRight() {
    if (!isRight()) {
      throw new IllegalStateException("Not Right.");
    }
    return ((Right<L, R>) this).getContents();
  }

  /**
   * Returns the wrapped object as an instance of the left type.
   *
   * @return the wrapped object
   * @throws IllegalStateException if the wrapped object is not of the left type
   */
  public L asLeft() {
    if (!isLeft()) {
      throw new IllegalStateException("Not Left.");
    }
    return ((Left<L, R>) this).getContents();
  }
}
