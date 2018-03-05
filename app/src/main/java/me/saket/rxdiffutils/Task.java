package me.saket.rxdiffutils;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Task {

  public abstract long id();

  public abstract String label();

  public abstract boolean isChecked();

  public static Task pending(long id, String label) {
    return new AutoValue_Task(id, label, false);
  }

  public Task withChecked(boolean checked) {
    return new AutoValue_Task(id(), label(), checked);
  }
}
