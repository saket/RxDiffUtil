package me.saket.rxdiffutils;

import android.support.v7.util.DiffUtil;
import java.util.List;

/**
 * DIffUtils.Callback + generics.
 */
public abstract class SimpleDiffUtilCallbacks<T> extends DiffUtil.Callback {

  private final List<T> oldItems;
  private final List<T> newItems;

  public SimpleDiffUtilCallbacks(List<T> oldItems, List<T> newItems) {
    this.oldItems = oldItems;
    this.newItems = newItems;
  }

  public abstract boolean areItemsTheSame(T oldItem, T newItem);

  protected abstract boolean areContentsTheSame(T oldItem, T newItem);

  @Override
  public final int getOldListSize() {
    return oldItems.size();
  }

  @Override
  public final int getNewListSize() {
    return newItems.size();
  }

  @Override
  public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    T oldItem = oldItems.get(oldItemPosition);
    T newItem = newItems.get(newItemPosition);
    return areItemsTheSame(oldItem, newItem);
  }

  @Override
  public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    T oldItem = oldItems.get(oldItemPosition);
    T newItem = newItems.get(newItemPosition);
    return areContentsTheSame(oldItem, newItem);
  }
}
