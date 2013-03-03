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
    /**
     * Additional info
     */
    int ganIdx, zhiIdx, zodiacIdx, termIdx, holidayIdx, hourIdx;

    public LunarDate() {
    }

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
      return this.year == date.year && this.month == date.month
          && this.day == date.day && this.isLeapMonth == date.isLeapMonth;
    }

    @Override
    public String toString() {
      return "" + year + "-" + month + (isLeapMonth ? "*" : "") + "-" + day;
    }

  }

  private LunarData data = new LunarData();

  private int getDaysSinceEpoch(Calendar calendar) {
    Calendar baseDate = Calendar.getInstance();
    Calendar currentDate = Calendar.getInstance();
    baseDate.set(1900, Calendar.JANUARY, 31);
    currentDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH));
    return (int) ((currentDate.getTimeInMillis() - baseDate.getTimeInMillis()) / 86400000L);
  }

  public LunarDate transformLunarDate(Calendar calendar) {
    LunarDate lunarDate = new LunarDate();
    int offset = getDaysSinceEpoch(calendar);

    for (lunarDate.year = LunarData.LUNAR_INFO_FIRST_YEAR; lunarDate.year <= LunarData.LUNAR_INFO_LAST_YEAR
        && offset > 0; ++lunarDate.year) {
      int daysInYear = data.getDaysInYear(lunarDate.year);
      if (daysInYear <= offset) {
        offset -= daysInYear;
      } else {
        break;
      }
    }

    int leapMonth = data.getLeapMonthIdx(lunarDate.year);

    for (lunarDate.month = 1; lunarDate.month <= 12 && offset > 0; ++lunarDate.month) {
      int daysInMonth = data.getDaysInMonth(lunarDate.year, lunarDate.month);
      if (daysInMonth <= offset) {
        offset -= daysInMonth;
      } else {
        break;
      }

      if (leapMonth == lunarDate.month) {
        lunarDate.isLeapMonth = true;
        daysInMonth = data.getDaysInMonth(lunarDate.year, 0);
        if (daysInMonth <= offset) {
          offset -= daysInMonth;
        } else {
          break;
        }
        lunarDate.isLeapMonth = false;
      }
    }

    lunarDate.day = offset + 1;
    fillAdditionalInfo(lunarDate, calendar);
    return lunarDate;
  }

  public LunarDate nextDay(LunarDate lunarDate, Calendar calendar) {
    LunarDate date = new LunarDate(lunarDate.year, lunarDate.month,
        lunarDate.day, lunarDate.isLeapMonth);
    Calendar tmpCal = Calendar.getInstance();
    tmpCal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
        calendar.get(Calendar.SECOND));
    tmpCal.add(Calendar.DAY_OF_MONTH, 1);
    date.day++;
    if (date.day > data.getDaysInMonth(date.year, date.isLeapMonth ? 0
        : date.month)) {
      date.day = 1;
      if (!date.isLeapMonth && date.month == data.getLeapMonthIdx(date.year)) {
        // The next month is leap month
        date.isLeapMonth = true;
      } else {
        if (date.isLeapMonth) {
          date.isLeapMonth = false;
        }
        date.month++;
      }
    }
    if (date.month > 12) {
      date.month = 1;
      date.year++;
    }
    fillAdditionalInfo(date, tmpCal);
    return date;
  }

  private void fillAdditionalInfo(LunarDate lunarDate, Calendar calendar) {
    lunarDate.ganIdx = data.getGanIdx(lunarDate.year);
    lunarDate.zhiIdx = data.getZhiIdx(lunarDate.year);
    lunarDate.zodiacIdx = data.getAnimalsYearIdx(lunarDate.year);
    lunarDate.termIdx = data.getTermIdx(calendar);
    lunarDate.holidayIdx = data.getHolidayIdx(lunarDate.year, lunarDate.month,
        lunarDate.isLeapMonth, lunarDate.day);
    lunarDate.hourIdx = data.getAncientHourIdx(calendar);
  }
}
