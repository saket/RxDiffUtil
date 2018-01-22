package me.saket.rxdiffutils;

import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;
import android.util.Pair;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import java.util.Collections;
import java.util.List;

public class RxDiffUtils {

  public static <T> ObservableTransformer<List<T>, Pair<List<T>, DiffResult>> calculateDiff(
      BiFunction<List<T>, List<T>, DiffUtil.Callback> diffCallbacks)
  {
    Pair<List<T>, DiffUtil.DiffResult> initialPair = Pair.create(Collections.emptyList(), null);
    return upstream -> upstream
        .scan(initialPair, (latestPair, nextItems) -> {
          DiffUtil.Callback callback = diffCallbacks.apply(latestPair.first, nextItems);
          DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback, true);
          return Pair.create(nextItems, result);
        })
        .skip(1);  // Initial value is dummy.
  }
}
