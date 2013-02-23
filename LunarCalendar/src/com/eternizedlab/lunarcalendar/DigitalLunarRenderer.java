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

public class DigitalLunarRenderer extends LunarRenderer {

  public DigitalLunarRenderer(Context context) {
    super(context);
  }

  @Override
  public String getYear(int year) {
    return String.valueOf(year);
  }

  @Override
  public String getMonth(int month, boolean isLeapMonth) {
    return (isLeapMonth ? context.getString(R.string.leap) : "") + month;
  }

  @Override
  public String getDay(int day) {
    return String.valueOf(day);
  }
}
