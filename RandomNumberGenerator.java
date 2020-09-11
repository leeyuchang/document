package com.jpa1.jpa1;

import java.util.Random;

public class RandomNumberGenerator extends NumberGenerator {

  private static NumberGenerator randomNumberGenerator = new RandomNumberGenerator();
  private int number;
  private Random random = new Random();
  
  private RandomNumberGenerator() {}

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public void execute() {
    for (int i = 0; i < 20; i++) {
      number = random.nextInt(50);
      notifyObservers();
    }
  }

  public static NumberGenerator getInstance() {
    return randomNumberGenerator;
  }
}
