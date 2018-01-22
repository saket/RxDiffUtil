package me.saket.rxdiffutils;

import java.util.List;

public class WizardDiffCallbacks extends SimpleDiffUtilCallbacks<Wizard> {

  public static WizardDiffCallbacks create(List<Wizard> oldItems, List<Wizard> newItems) {
    return new WizardDiffCallbacks(oldItems, newItems);
  }

  private WizardDiffCallbacks(List<Wizard> oldItems, List<Wizard> newItems) {
    super(oldItems, newItems);
  }

  @Override
  public boolean areItemsTheSame(Wizard oldItem, Wizard newItem) {
    return oldItem.id() == newItem.id();
  }

  @Override
  protected boolean areContentsTheSame(Wizard oldItem, Wizard newItem) {
    return oldItem.equals(newItem);
  }
}
