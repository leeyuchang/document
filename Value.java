package com.jpa1.jpa1;

import java.util.ArrayList;
import java.util.List;

public class Value {

  List<ValueListener> listeners = new ArrayList<>();

  private int value;

  public Value(int value) {
    this.value = value;
  }
  
  public void setValue(int value) {
    this.value = value;
    notifyListeners();
  }
  
  public int getValue() {
    return value;
  }
  
  void addListener(ValueListener listener) {
    listeners.add(listener);
  }

  void removeListener(ValueListener listener) {
    listeners.remove(listener);
  }

  private void notifyListeners() {
    listeners.forEach(listener -> listener.valueChanged(new ValueChangedEvent(this)));
  }
}
