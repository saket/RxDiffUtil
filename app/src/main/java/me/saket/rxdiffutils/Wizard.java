package me.saket.rxdiffutils;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Wizard {

  public abstract long id();

  public abstract String name();

  public abstract String house();

  public static Wizard create(long id, String name, String house) {
    return new AutoValue_Wizard(id, name, house);
  }
}
