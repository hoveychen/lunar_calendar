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

import java.util.Locale;

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;

public class TraditionalLunarRenderer extends LunarRenderer {

  public String getYear(LunarDate date) {
    StringBuilder sb = new StringBuilder();
    int year = date.year;
    while (year > 0) {
      sb.append(RenderHelper.getStringFromList(R.array.unit_number_array,
          year % 10));
      year /= 10;
    }
    return sb.reverse().toString();
  }

  public String getMonth(LunarDate date) {
    StringBuilder sb = new StringBuilder();
    if (date.isLeapMonth) {
      sb.append(RenderHelper.getString(R.string.leap));
    }
    if (date.month == 1) {
      sb.append(RenderHelper.getString(R.string.first_month));
    } else {
      sb.append(RenderHelper.getStringFromList(R.array.unit_number_array,
          date.month));
    }
    return sb.toString();
  }

  public String getDay(LunarDate date) {
    switch (date.day) {
    case 10:
      return RenderHelper.getString(R.string.tenth);
    case 20:
      return RenderHelper.getString(R.string.twentieth);
    case 30:
      return RenderHelper.getString(R.string.thirtieth);
    default:
      return RenderHelper.getStringFromList(R.array.decade_number_array,
          date.day / 10)
          + RenderHelper.getStringFromList(R.array.unit_number_array,
              date.day % 10);
    }
  }

  private String getStatusDay(LunarDate date) {
    // TODO(hoveychen): Maybe introduce English abbr. to display the holiday or
    // solar term in status?
    if (Locale.SIMPLIFIED_CHINESE.equals(Locale.getDefault())
        || Locale.TRADITIONAL_CHINESE.equals(Locale.getDefault())) {
      if (date.holidayIdx != -1) {
        return RenderHelper.getStringFromList(R.array.holiday_array,
            date.holidayIdx);
      }
      if (date.termIdx != -1) {
        return RenderHelper.getStringFromList(R.array.term_array, date.termIdx);
      }
    }
    return getDay(date);
  }

  @Override
  protected String getSingleLineStatus(LunarDate date) {
    return RenderHelper.getString(R.string.template_traditional_status_single,
        getStatusDay(date));
  }

  @Override
  protected String getDoubleLineStatus(LunarDate date) {
    return RenderHelper.getString(R.string.template_traditional_status_double,
        getMonth(date), getDay(date));
  }

  @Override
  public String getDisplayExpandedTitle(LunarDate date) {
    return RenderHelper.getString(R.string.template_traditional_expanded_title,
        getYear(date), getMonth(date), getDay(date), getSpecialDay(date));
  }

  @Override
  public String getDisplayExpandedBody(LunarDate date) {
    return RenderHelper.getString(R.string.template_traditional_expanded_body,
        getGanZhiYear(date), getZodiac(date), getHour(date));
  }

}
