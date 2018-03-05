package me.saket.rxdiffutils;

import android.support.annotation.CheckResult;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

public class RxArrayList<T> extends ArrayList<T> {

  private final PublishRelay<List<T>> changeNotifications = PublishRelay.create();

  @Override
  public boolean add(T t) {
    boolean add = super.add(t);
    changeNotifications.accept(this);
    return add;
  }

  @Override
  public boolean remove(Object o) {
    boolean remove = super.remove(o);
    changeNotifications.accept(this);
    return remove;
  }

  @CheckResult
  public Observable<List<T>> changes() {
    return changeNotifications.startWith(this);
  }
}
