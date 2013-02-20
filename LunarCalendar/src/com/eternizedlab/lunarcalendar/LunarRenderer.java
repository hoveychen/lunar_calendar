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

public class LunarRenderer {
  private Context context;

  public LunarRenderer(Context context) {
    this.context = context;
  }

  public String getYear(int year) {
    String[] unitNumbers = context.getResources().getStringArray(R.array.unit_number_array);
    StringBuilder sb = new StringBuilder();
    while (year > 0) {
      sb.append(unitNumbers[year % 10]);
      year /= 10;
    }
    return sb.reverse().toString();
  }

  public String getMonth(int month, boolean isLeapMonth) {
    String[] unitNumbers = context.getResources().getStringArray(R.array.unit_number_array);
    StringBuilder sb = new StringBuilder();
    if (isLeapMonth) {
      sb.append(context.getString(R.string.leap));
    }
    if (month == 1) {
      sb.append(context.getString(R.string.first_month));
    } else {
      sb.append(unitNumbers[month]);
    }
    return sb.toString();
  }

  public String getDay(int day) {
    switch (day) {
      case 10:
        return context.getString(R.string.tenth);
      case 20:
        return context.getString(R.string.twentieth);
      case 30:
        return context.getString(R.string.thirtieth);
      default:
        String[] unitNumbers = context.getResources().getStringArray(R.array.unit_number_array);
        String[] decadeNumbers = context.getResources().getStringArray(R.array.decade_number_array);
        return new StringBuilder().append(decadeNumbers[day / 10])
            .append(unitNumbers[day % 10]).toString();
    }
  }

  public String getAnimal(int animalIdx) {
    return context.getResources().getStringArray(R.array.animal_array)[animalIdx];
  }

  public String getAnicentHour(int anicentHourIdx) {
    return context.getResources().getStringArray(R.array.zhi_array)[anicentHourIdx];
  }

  public String getGanZhi(int ganIdx, int zhiIdx) {
    StringBuffer sb = new StringBuffer();
    sb.append(context.getResources().getStringArray(R.array.gan_array)[ganIdx]);
    sb.append(context.getResources().getStringArray(R.array.zhi_array)[zhiIdx]);
    return sb.toString();
  }

  public String getTerm(int termIdx) {
    return termIdx == -1 ? "" : context.getResources().getStringArray(R.array.term_array)[termIdx];
  }

  public String getHoliday(int holidayIdx) {
    return holidayIdx == -1 ? ""
        : context.getResources().getStringArray(R.array.holiday_array)[holidayIdx];
  }
}
