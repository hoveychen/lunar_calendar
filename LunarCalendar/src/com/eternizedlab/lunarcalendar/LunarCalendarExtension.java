package com.eternizedlab.lunarcalendar;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class LunarCalendarExtension extends DashClockExtension {
  private static final String TAG = LunarCalendarExtension.class.getSimpleName();

  public static final String PREF_NAME = "pref_name";

  @Override
  protected void onUpdateData(int reason) {
    // Get preference value.
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    String name = sp.getString(PREF_NAME, getString(R.string.pref_name_default));

    // Publish the extension data update.
    publishUpdate(new ExtensionData().visible(true)
        .icon(R.drawable.ic_extension_lunarcalendar)
        .status("Hello")
        .expandedTitle("Hello, " + name + "!")
        .expandedBody("Thanks for checking out this example extension for DashClock.")
        .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))));
  }
}
