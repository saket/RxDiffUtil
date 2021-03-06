package me.saket.rxdiffutils;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.searchquery) EditText searchQueryField;
  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  private final Relay<Object> onDestroys = PublishRelay.create();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    MinistryOfMagic ministryOfMagic = new MinistryOfMagic();
    Observable<List<Wizard>> searchResults = RxTextView.textChanges(searchQueryField)
        .map(CharSequence::toString)
        .switchMapSingle(ministryOfMagic::search);

    WizardsAdapter wizardsAdapter = new WizardsAdapter();
    searchResults
        .observeOn(io())
        .compose(RxDiffUtil.calculateDiff(WizardDiffCallbacks::create))
        .observeOn(mainThread())
        .takeUntil(onDestroys)
        .subscribe(wizardsAdapter);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new SlideDownAlphaAnimator());
    recyclerView.setAdapter(wizardsAdapter);

    wizardsAdapter.itemClicks()
        .takeUntil(onDestroys)
        .subscribe(wizard -> startActivity(Intents.forGoogleSearch(wizard.name())));
  }

  @Override
  protected void onDestroy() {
    onDestroys.accept(Notification.INSTANCE);
    super.onDestroy();
  }
}
