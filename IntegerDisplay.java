package com.jpa1.jpa1;

import org.junit.jupiter.api.Test;

public class IntegerDisplay implements ValueListener {

  private Value value = new Value(0);;

  public IntegerDisplay() {
    value.addListener(this);
  }

  @Override
  public void valueChanged(ValueChangedEvent e) {
    if (e.getSource() == value) {
      System.out.println("valueChanged : " + value.getValue());
    }
  }

  @Test
  void main() {
    IntegerDisplay integerDisplay = new IntegerDisplay();
    integerDisplay.value.setValue(1_000);
  }

}
