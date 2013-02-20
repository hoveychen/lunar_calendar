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

/**
 * Lunar calendar. Used to transform date from solar date to lunar date.
 */
public class LunarCalendar {

  /**
   * Epoch is 1900/1/1
   */
  public static class LunarDate {
    /**
     * ranging [1900..2050]
     */
    int year;
    /**
     * ranging [1..12]
     */
    int month;
    /**
     * ranging [1..31]
     */
    int day;
    boolean isLeapMonth;

    public LunarDate() {}

    public LunarDate(int year, int month, int day, boolean isLeapMonth) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.isLeapMonth = isLeapMonth;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof LunarDate)) {
        return false;
      }
      LunarDate date = (LunarDate) o;
      return this.year == date.year && this.month == date.month && this.day == date.day
          && this.isLeapMonth == date.isLeapMonth;
    }

    @Override
    public String toString() {
      return "" + year + "-" + month + (isLeapMonth ? "*" : "") + "-" + day;
    }
  }

  /**
   * Epoch is 1900/1/31
   */
  public static class SolarDate {
    /**
     * ranging [1900..2050]
     */
    int year;
    /**
     * ranging [1..12]
     */
    int month;
    /**
     * ranging [1..31]
     */
    int day;

    public int getDaysSinceEpoch() {
      Calendar baseDate = Calendar.getInstance();
      Calendar currentDate = Calendar.getInstance();
      baseDate.set(1900, Calendar.JANUARY, 31);
      currentDate.set(year, month - 1, day);
      return (int) ((currentDate.getTimeInMillis() - baseDate.getTimeInMillis()) / 86400000L);
    }

    public SolarDate() {}

    public SolarDate(int year, int month, int day) {
      this.year = year;
      this.month = month;
      this.day = day;
    }
  }

  private LunarData lunarData;

  public LunarCalendar(LunarData lunarData) {
    this.lunarData = lunarData;
  }

  public LunarDate transformLunarDate(SolarDate solarDate) {
    LunarDate lunarDate = new LunarDate();
    int offset = solarDate.getDaysSinceEpoch();

    for (lunarDate.year = LunarData.LUNAR_INFO_FIRST_YEAR;
        lunarDate.year <= LunarData.LUNAR_INFO_LAST_YEAR && offset > 0; ++lunarDate.year) {
      int daysInYear = lunarData.getDaysInYear(lunarDate.year);
      if (daysInYear <= offset) {
        offset -= daysInYear;
      } else {
        break;
      }
    }

    int leapMonth = lunarData.getLeapMonthIdx(lunarDate.year);

    for (lunarDate.month = 1; lunarDate.month <= 12 && offset > 0; ++lunarDate.month) {
      int daysInMonth = lunarData.getDaysInMonth(lunarDate.year, lunarDate.month);
      if (daysInMonth <= offset) {
        offset -= daysInMonth;
      } else {
        break;
      }

      if (leapMonth == lunarDate.month) {
        lunarDate.isLeapMonth = true;
        daysInMonth = lunarData.getDaysInMonth(lunarDate.year, 0);
        if (daysInMonth <= offset) {
          offset -= daysInMonth;
        } else {
          break;
        }
        lunarDate.isLeapMonth = false;
      }
    }

    lunarDate.day = offset + 1;
    return lunarDate;
  }

}
