package me.saket.rxdiffutils;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class GithubSearchResponse {

  @Json(name = "items")
  public abstract List<SearchResult> results();

  public static JsonAdapter<GithubSearchResponse> jsonAdapter(Moshi moshi) {
    return new AutoValue_GithubSearchResponse.MoshiJsonAdapter(moshi);
  }

  @AutoValue
  public abstract static class SearchResult {
    @Json(name = "id")
    public abstract long id();

    @Json(name = "name")
    public abstract String name();

    @Json(name = "html_url")
    public abstract String html_url();

    @Nullable
    @Json(name = "description")
    public abstract String description();

    @Json(name = "language")
    public abstract String language();

    @Json(name = "owner")
    public abstract Owner owner();

    public static JsonAdapter<SearchResult> jsonAdapter(Moshi moshi) {
      return new AutoValue_GithubSearchResponse_SearchResult.MoshiJsonAdapter(moshi);
    }
  }

  @AutoValue
  public abstract static class Owner {
    @Json(name = "login")
    public abstract String username();

    public static JsonAdapter<Owner> jsonAdapter(Moshi moshi) {
      return new AutoValue_GithubSearchResponse_Owner.MoshiJsonAdapter(moshi);
    }
  }
}
