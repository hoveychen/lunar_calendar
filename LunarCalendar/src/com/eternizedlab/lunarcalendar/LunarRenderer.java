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

import android.content.Context;

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;

public abstract class LunarRenderer {
  protected Context context;

  public LunarRenderer(Context context) {
    this.context = context;
  }

  protected int numStatusLine = 1;

  public void setNumStatusLine(int numStatusLine) {
    this.numStatusLine = numStatusLine;
  }

  public String getDisplayStatus(LunarDate date) {
    return numStatusLine == 1 ? getSingleLineStatus(date)
        : getDoubleLineStatus(date);
  }

  protected String getStringFromList(int arrayResId, int idx) {
    String[] array = context.getResources().getStringArray(arrayResId);
    if (idx < 0 || idx >= array.length) {
      return "";
    } else {
      return array[idx];
    }
  }

  protected String getGanZhiYear(LunarDate date) {
    return getStringFromList(R.array.gan_array, date.ganIdx)
        + getStringFromList(R.array.zhi_array, date.zhiIdx);
  }

  protected String getZodiac(LunarDate date) {
    return getStringFromList(R.array.animal_array, date.zodiacIdx);
  }

  protected String getSpecialDay(LunarDate date) {
    if (date.holidayIdx != -1) {
      return getStringFromList(R.array.holiday_array, date.holidayIdx);
    }
    if (date.termIdx != -1) {
      return getStringFromList(R.array.term_array, date.termIdx);
    }
    return "";
  }

  protected String getHour(LunarDate date) {
    return getStringFromList(R.array.zhi_array, date.hourIdx);
  }

  protected abstract String getSingleLineStatus(LunarDate date);

  protected abstract String getDoubleLineStatus(LunarDate date);

  public abstract String getDisplayExpandedTitle(LunarDate date);

  public abstract String getDisplayExpandedBody(LunarDate date);

}
