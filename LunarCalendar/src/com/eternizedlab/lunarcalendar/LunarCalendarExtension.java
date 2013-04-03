//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.eternizedlab.lunarcalendar;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class LunarCalendarExtension extends DashClockExtension {
  private static final String TAG = LunarCalendarExtension.class
      .getSimpleName();
  private TraditionalLunarRenderer traditionalRenderer = new TraditionalLunarRenderer();
  private DigitalLunarRenderer digitalRenderer = new DigitalLunarRenderer();
  private LunarCalendar calendar = new LunarCalendar();
  private LunarRenderer renderer;

  public static final String PREF_STATUS_NUMBER_OF_LINES = "pref_status_number_of_lines";
  public static final String PREF_NEXT_SPECIAL = "pref_next_special";
  public static final String PREF_LANGUAGE = "pref_language";

  private void setupRenderer(SharedPreferences sp) {
    String numStatusLines = sp.getString(PREF_STATUS_NUMBER_OF_LINES, "1");
    String language = sp.getString(PREF_LANGUAGE,
        getString(R.string.pref_language_default_value));
    renderer = "cn_traditional".equals(language) ? traditionalRenderer
        : digitalRenderer;
    renderer.setNumStatusLine("1".equals(numStatusLines) ? 1 : 2);
    RenderHelper.setLocale(getDisplayLocale(language));
  }

  @Override
  protected void onUpdateData(int reason) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

    setupRenderer(sp);

    String nextSpecialDayTitle = "";
    if (sp.getBoolean(PREF_NEXT_SPECIAL, true)) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, 1);
      LunarDate ld = calendar.transformLunarDate(cal);
      int daysLeft = 1;
      while (ld.holidayIdx == -1 && ld.termIdx == -1) {
        daysLeft++;
        ld = calendar.nextDay(ld, cal);
        cal.add(Calendar.DAY_OF_MONTH, 1);
      }
      nextSpecialDayTitle = renderer.getNextSpecialDay(ld, daysLeft);
    }

    LunarDate date = calendar.transformLunarDate(Calendar.getInstance());

    Log.v(TAG, renderer.getDisplayStatus(date));
    Log.v(TAG, renderer.getDisplayExpandedTitle(date));
    Log.v(TAG, renderer.getDisplayExpandedBody(date) + nextSpecialDayTitle);
    // Publish the extension data update.
    publishUpdate(new ExtensionData()
        .visible(true)
        .icon(R.drawable.ic_extension_lunarcalendar)
        .status(renderer.getDisplayStatus(date))
        .expandedTitle(renderer.getDisplayExpandedTitle(date))
        .expandedBody(
            renderer.getDisplayExpandedBody(date) + nextSpecialDayTitle)
        .clickIntent(getWebpageIntent()));
  }

  private Intent getDefaultIntent() {
    return new Intent(Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,
        Intent.CATEGORY_APP_CALENDAR)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  }

  private Intent getWebpageIntent() {
    return new Intent(Intent.ACTION_VIEW,
        Uri.parse("http://www.baidu.com/s?wd=%E4%B8%87%E5%B9%B4%E5%8E%86"));
  }

  private Locale getDisplayLocale(String language) {
    if ("en".equals(language)) {
      return Locale.ENGLISH;
    } else {
      return Locale.CHINESE;
    }
  }

  @Override
  protected void onInitialize(boolean isReconnect) {
    super.onInitialize(isReconnect);
    setUpdateWhenScreenOn(true);
    RenderHelper.initialize(this);
  }

}
