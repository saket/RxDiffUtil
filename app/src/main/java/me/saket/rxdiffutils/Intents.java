package me.saket.rxdiffutils;

import android.content.Intent;
import android.net.Uri;

public class Intents {

  public static Intent forGoogleSearch(String query) {
    return new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/search?q=" + query));
  }
}
