package me.saket.rxdiffutils;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  private final Relay<Object> onDestroys = PublishRelay.create();
  private TaskRepository taskRepository = new TaskRepository();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    for (int i = 0; i < 5; i++) {
      taskRepository.addPendingTask(Task.pending(i, "Task #" + i));
    }
    Timber.i("tasks: %s", taskRepository.pendingTasks.size());

    TaskAdapter taskAdapter = new TaskAdapter();
    taskRepository.streamTasks()
        .doOnNext(o -> Timber.i("found %s tasks", o.size()))
        .observeOn(io())
        .compose(RxDiffUtil.calculate(TaskItemDiffer::create))
        .observeOn(mainThread())
        .takeUntil(onDestroys)
        .subscribe(taskAdapter);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new SlideDownAlphaAnimator());
    recyclerView.setAdapter(taskAdapter);

    taskAdapter.itemClicks()
        .takeUntil(onDestroys)
        .subscribe(task -> {
          if (task.isChecked()) {
            taskRepository.markAsPending(task);
          } else {
            taskRepository.markAsCompleted(task);
          }
        });
  }

  @Override
  protected void onDestroy() {
    onDestroys.accept(Notification.INSTANCE);
    super.onDestroy();
  }
}
