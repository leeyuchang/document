package com.home;

import java.util.List;
import lombok.AllArgsConstructor;

public class takeWhile {

  public static void main(String[] args) {
    List<Base> parents = List.of(new BaseTrue("A"), new BaseFalse("B"), new BaseTrue("C"));

    parents // filter the element returns false
        .stream() // START
        .takeWhile(Base::call) // Stop when it meets false
        .forEach(System.out::println);
  }
}

interface Base {
  default boolean call() {
    return true;
  }
}

@AllArgsConstructor
class BaseTrue implements Base {
  private String myName;

  @Override
  public boolean call() {
    return Base.super.call();
  }

  @Override
  public String toString() {
    return "my name is " + myName;
  }
}

@AllArgsConstructor
class BaseFalse implements Base {
  private String myName;

  @Override
  public boolean call() {
    return false;
  }

  @Override
  public String toString() {
    return "my name is " + myName;
  }
}
