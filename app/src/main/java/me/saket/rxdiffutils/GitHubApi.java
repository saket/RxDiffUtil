package me.saket.rxdiffutils;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface GitHubApi {

  @GET("/search/repositories")
  Single<GithubSearchResponse> search(@Query("q") String query);
}
