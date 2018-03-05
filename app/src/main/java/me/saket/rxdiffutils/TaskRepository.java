package me.saket.rxdiffutils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class TaskRepository {

  RxArrayList<Task> pendingTasks = new RxArrayList<>();
  RxArrayList<Task> completedTasks = new RxArrayList<>();

  public Observable<List<Task>> streamTasks() {
    return Observable.combineLatest(
        pendingTasks.changes().doOnNext(o -> Timber.i("boop")),
        completedTasks.changes().doOnNext(o -> Timber.i("boop")),
        (pending, completed) -> {
          List<Task> merged = new ArrayList<>(pending.size() + completed.size());
          merged.addAll(pending);
          merged.addAll(completed);
          Timber.i("merged: %s", merged.size());
          return merged;
        });
  }

  public void addPendingTask(Task task) {
    pendingTasks.add(task);
  }

  public void markAsCompleted(Task task) {
    pendingTasks.remove(task);

    Task updated = task.withChecked(true);
    completedTasks.add(updated);
  }

  public void markAsPending(Task task) {
    completedTasks.remove(task);

    Task updated = task.withChecked(false);
    pendingTasks.add(updated);
  }
}
