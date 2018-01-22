package me.saket.rxdiffutils;

import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

public class WizardsAdapter extends RecyclerView.Adapter<WizardsAdapter.WizardVH>
    implements Consumer<Pair<List<Wizard>, DiffResult>>
{

  private List<Wizard> wizards = new ArrayList<>();
  private Relay<Wizard> itemClicks = PublishRelay.create();

  public WizardsAdapter() {
    setHasStableIds(true);
  }

  public Observable<Wizard> itemClicks() {
    return itemClicks;
  }

  @Override
  public void accept(Pair<List<Wizard>, DiffResult> pair) throws Exception {
    wizards = pair.first;
    pair.second.dispatchUpdatesTo(this);
  }

  @Override
  public WizardVH onCreateViewHolder(ViewGroup parent, int viewType) {
    WizardVH holder = WizardVH.create(LayoutInflater.from(parent.getContext()), parent);
    holder.itemView.setOnClickListener(o -> itemClicks.accept(holder.item));
    return holder;
  }

  @Override
  public void onBindViewHolder(WizardVH holder, int position) {
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

  public static class WizardVH extends RecyclerView.ViewHolder {

    @BindView(R.id.wizard_name) TextView nameView;
    @BindView(R.id.wizard_house) TextView houseView;

    private Wizard item;

    public static WizardVH create(LayoutInflater inflater, ViewGroup parent) {
      return new WizardVH(inflater.inflate(R.layout.list_item_wizard, parent, false));
    }

    public WizardVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setItem(Wizard wizard) {
      this.item = wizard;
    }

    public void render() {
      nameView.setText(item.name());
      houseView.setText(item.house());
    }
  }
}
