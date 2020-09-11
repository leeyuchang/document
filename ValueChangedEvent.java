package com.jpa1.jpa1;

public class ValueChangedEvent {

  private Value source;

  public ValueChangedEvent(Value vaule) {
    this.source = vaule;
  }

  public Value getSource() {
    return source;
  }
}
