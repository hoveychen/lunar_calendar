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

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;
import com.eternizedlab.lunarcalendar.LunarCalendar.SolarDate;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class LunarCalendarExtension extends DashClockExtension {
  // private static final String TAG =
  // LunarCalendarExtension.class.getSimpleName();
  private LunarRenderer renderer;
  private LunarCalendar calendar;
  private LunarData data;

  public static final String PREF_STATUS_NUMBER_OF_LINES = "pref_status_number_of_lines";
  public static final String PREF_STATUS_NUMBER_FORMAT = "pref_number_format";

  public LunarCalendarExtension() {
    data = new LunarData(Locale.getDefault());
    calendar = new LunarCalendar(data);
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onUpdateData(int reason) {
    // Get preference value.
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    String numStatusLines = sp.getString(PREF_STATUS_NUMBER_OF_LINES, "1");
    String numberFormat = sp.getString(PREF_STATUS_NUMBER_FORMAT,
        getString(R.string.pref_number_format_default_value));
    renderer = "t".equals(numberFormat) ? new LunarRenderer(this)
        : new DigitalLunarRenderer(this);

    String[] params = getDisplayParams();
    String clickUrl = getString(R.string.click_url,
        URLEncoder.encode(getString(R.string.short_date_template, params[1] /* month */
        , params[2] /* day */)));

    // Publish the extension data update.
    publishUpdate(new ExtensionData()
        .visible(true)
        .icon(R.drawable.ic_extension_lunarcalendar)
        .status(
            getString(
                "1".equals(numStatusLines) ? R.string.status_single_template
                    : R.string.status_double_template, params))
        .expandedTitle(getString(R.string.expanded_title_template, params))
        .expandedBody(getString(R.string.expanded_body_template, params))
        .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(clickUrl))));
  }

  private String[] getDisplayParams() {
    Calendar now = Calendar.getInstance();
    SolarDate solarDate = new SolarDate();
    solarDate.year = now.get(Calendar.YEAR);
    solarDate.month = now.get(Calendar.MONTH) + 1;
    solarDate.day = now.get(Calendar.DAY_OF_MONTH);
    LunarDate lunarDate = calendar.transformLunarDate(solarDate);

    return new String[] {
        renderer.getYear(lunarDate.year),
        renderer.getMonth(lunarDate.month, lunarDate.isLeapMonth),
        renderer.getDay(lunarDate.day),
        renderer.getGanZhi(data.getGanIdx(lunarDate.year),
            data.getZhiIdx(lunarDate.year)),
        renderer.getAnimal(data.getAnimalsYearIdx(lunarDate.year)),
        renderer.getTerm(data.getTermIdx(solarDate.year, solarDate.month,
            solarDate.day)),
        renderer.getHoliday(data.getHolidayIdx(lunarDate.year, lunarDate.month,
            lunarDate.isLeapMonth, lunarDate.day)),
        getSmartDay(lunarDate, solarDate),
        renderer.getAnicentHour(data.getAncientHourIdx(now)) };
  }

  private String getSmartDay(LunarDate lunarDate, SolarDate solarDate) {
    int holidayIdx = data.getHolidayIdx(lunarDate.year, lunarDate.month,
        lunarDate.isLeapMonth, lunarDate.day);
    if (holidayIdx != -1) {
      return renderer.getHoliday(holidayIdx);
    }
    int termIdx = data.getTermIdx(solarDate.year, solarDate.month,
        solarDate.day);
    if (termIdx != -1) {
      return renderer.getTerm(termIdx);
    }
    return renderer.getDay(lunarDate.day);
  }

  private String getString(int resId, String[] strs) {
    if (strs == null || strs.length == 0) {
      return getString(resId);
    }
    switch (strs.length) {
    case 1:
      return getString(resId, strs[0]);
    case 2:
      return getString(resId, strs[0], strs[1]);
    case 3:
      return getString(resId, strs[0], strs[1], strs[2]);
    case 4:
      return getString(resId, strs[0], strs[1], strs[2], strs[3]);
    case 5:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4]);
    case 6:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4],
          strs[5]);
    case 7:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4],
          strs[5], strs[6]);
    case 8:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4],
          strs[5], //
          strs[6], strs[7]);
    case 9:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4],
          strs[5],//
          strs[6], strs[7], strs[8]);
    default:
      return getString(resId, strs[0], strs[1], strs[2], strs[3], strs[4],
          strs[5],//
          strs[6], strs[7], strs[8], strs[9]);
    }
  }

}
