package com.jpa1.jpa1;

import static java.util.Collections.synchronizedList;
import java.util.ArrayList;
import java.util.List;

public abstract class NumberGenerator {

  private List<Observer> observers = synchronizedList(new ArrayList<>());

  protected void addObserver(Observer o) {
    observers.add(o);
  }

  protected void delObserver(Observer o) {
    observers.remove(o);
  }

  protected void notifyObservers() {
    observers.forEach(o -> o.update(this));
  }

  protected abstract int getNumber();

  public abstract void execute();
}
