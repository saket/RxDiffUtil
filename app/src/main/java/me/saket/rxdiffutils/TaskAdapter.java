package me.saket.rxdiffutils;

import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import me.saket.rxdiffutils.TaskAdapter.TaskVH;

public class TaskAdapter extends RecyclerView.Adapter<TaskVH>
    implements Consumer<Pair<List<Task>, DiffResult>>
{

  private List<Task> wizards = new ArrayList<>();
  private Relay<Task> itemClicks = PublishRelay.create();

  public TaskAdapter() {
    setHasStableIds(true);
  }

  public Observable<Task> itemClicks() {
    return itemClicks;
  }

  @Override
  public void accept(Pair<List<Task>, DiffResult> pair) throws Exception {
    wizards = pair.first;
    pair.second.dispatchUpdatesTo(this);
  }

  @Override
  public TaskVH onCreateViewHolder(ViewGroup parent, int viewType) {
    TaskVH holder = TaskVH.create(LayoutInflater.from(parent.getContext()), parent);
    holder.nameView.setOnCheckedChangeListener((o, isChecked) -> itemClicks.accept(holder.item));
    return holder;
  }

  @Override
  public void onBindViewHolder(TaskVH holder, int position) {
    holder.setItem(wizards.get(position));
    holder.render();
  }

  @Override
  public long getItemId(int position) {
    return wizards.get(position).id();
  }

  @Override
  public int getItemCount() {
    return wizards == null ? 0 : wizards.size();
  }

  public static class TaskVH extends RecyclerView.ViewHolder {

    @BindView(R.id.task_label) CheckBox nameView;

    private Task item;

    public static TaskVH create(LayoutInflater inflater, ViewGroup parent) {
      return new TaskVH(inflater.inflate(R.layout.list_item_task, parent, false));
    }

    public TaskVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setItem(Task wizard) {
      this.item = wizard;
    }

    public void render() {
      nameView.setText(item.label());
      nameView.setChecked(item.isChecked());
    }
  }
}
