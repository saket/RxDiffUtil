package me.saket.rxdiffutils;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.searchquery) EditText searchQueryField;
  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  private final Relay<Object> onDestroys = PublishRelay.create();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    GitHubApi gitHubApi = githubApi();

    RxTextView.textChanges(searchQueryField)
        .skipInitialValue()
        .map(CharSequence::toString)
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMapSingle(q -> gitHubApi.search(q).subscribeOn(io()))
        .takeUntil(onDestroys)
        .observeOn(mainThread())
        .subscribe(
            response -> {
              Timber.i("Found %s repositories", response.results().size());
            },
            error -> error.printStackTrace()
        );
  }

  @Override
  protected void onDestroy() {
    onDestroys.accept(Notification.INSTANCE);
    super.onDestroy();
  }

  private GitHubApi githubApi() {
    Moshi moshi = new Moshi.Builder()
        .add(AutoValueMoshiAdapterFactory.create())
        .build();

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message))
        .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build();

    return new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(GitHubApi.class);
  }
}
