package me.saket.rxdiffutils;

import java.util.List;

public class TaskItemDiffer extends SimpleDiffUtilCallbacks<Task> {

  public static TaskItemDiffer create(List<Task> oldItems, List<Task> newItems) {
    return new TaskItemDiffer(oldItems, newItems);
  }

  private TaskItemDiffer(List<Task> oldItems, List<Task> newItems) {
    super(oldItems, newItems);
  }

  @Override
  public boolean areItemsTheSame(Task oldItem, Task newItem) {
    return oldItem.id() == newItem.id();
  }

  @Override
  protected boolean areContentsTheSame(Task oldItem, Task newItem) {
    return oldItem.equals(newItem);
  }
}
