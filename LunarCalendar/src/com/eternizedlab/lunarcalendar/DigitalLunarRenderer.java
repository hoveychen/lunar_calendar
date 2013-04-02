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

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;

public class DigitalLunarRenderer extends LunarRenderer {

  private String getMonth(LunarDate date) {
    return date.isLeapMonth ? RenderHelper.getString(
        R.string.template_digital_month, date.month) : String
        .valueOf(date.month);
  }

  @Override
  protected String getSingleLineStatus(LunarDate date) {
    return RenderHelper.getString(R.string.template_digital_status_single,
        date.day);
  }

  @Override
  protected String getDoubleLineStatus(LunarDate date) {
    return RenderHelper.getString(R.string.template_digital_status_double,
        getMonth(date), date.day);
  }

  @Override
  public String getDisplayExpandedTitle(LunarDate date) {
    return RenderHelper.getString(R.string.template_digital_expanded_title,
        date.year, getMonth(date), date.day, getSpecialDay(date));
  }

  @Override
  public String getDisplayExpandedBody(LunarDate date) {
    return RenderHelper.getString(R.string.template_digital_expanded_body,
        getGanZhiYear(date), getZodiac(date), getHour(date));
  }
}
