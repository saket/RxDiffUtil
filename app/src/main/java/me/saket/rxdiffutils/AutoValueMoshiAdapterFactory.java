package me.saket.rxdiffutils;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

@MoshiAdapterFactory
public abstract class AutoValueMoshiAdapterFactory implements JsonAdapter.Factory {

  public static AutoValueMoshiAdapterFactory create() {
    return new AutoValueMoshi_AutoValueMoshiAdapterFactory();
  }
}
