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

import junit.framework.TestCase;

public class LunarDataTest extends TestCase {

  private LunarData data;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    Locale.setDefault(Locale.CHINA);
    data = new LunarData();
  }

  public void testGetLeapMonthIdx() {
    assertEquals(8, data.getLeapMonthIdx(1900));
    assertEquals(0, data.getLeapMonthIdx(1901));
    assertEquals(5, data.getLeapMonthIdx(1903));
    assertEquals(5, data.getLeapMonthIdx(2047));
  }

  public void testGetDaysInMonth() {
    assertEquals(29, data.getDaysInMonth(1900, 1));
    assertEquals(30, data.getDaysInMonth(1900, 2));
    assertEquals(30, data.getDaysInMonth(1900, 7));
    assertEquals(30, data.getDaysInMonth(1900, 8));
    assertEquals(29, data.getDaysInMonth(1900, 11));
    assertEquals(30, data.getDaysInMonth(1900, 12));
    assertEquals(29, data.getDaysInMonth(1900, 0));
    assertEquals(30, data.getDaysInMonth(1906, 0));
    assertEquals(29, data.getDaysInMonth(1909, 0));
  }

  public void testGetDaysInYear() {
    assertEquals(384, data.getDaysInYear(1900));
    assertEquals(354, data.getDaysInYear(1901));
    assertEquals(384, data.getDaysInYear(1906));
  }

  public void testGetTermIdx() {
    Calendar cal = Calendar.getInstance();
    cal.set(2013, Calendar.JANUARY, 5); // 2013-1-5
    assertEquals(0, data.getTermIdx(cal));
    cal.set(2013, Calendar.JANUARY, 20); // 2013-1-20
    assertEquals(1, data.getTermIdx(cal));
    cal.set(2013, Calendar.DECEMBER, 7); // 2013-12-7
    assertEquals(22, data.getTermIdx(cal));
    cal.set(2013, Calendar.DECEMBER, 22); // 2013-12-22
    assertEquals(23, data.getTermIdx(cal));
    cal.set(2013, Calendar.FEBRUARY, 14); // 2013-2-4
    assertEquals(-1, data.getTermIdx(cal));
  }

  public void testGetHolidayIdx() {
    assertEquals(0, data.getHolidayIdx(2013, 1, false, 1));
    assertEquals(1, data.getHolidayIdx(2013, 1, false, 15));
    assertEquals(7, data.getHolidayIdx(2012, 12, false, 29));
    assertEquals(7, data.getHolidayIdx(2013, 12, false, 30));
    assertEquals(-1, data.getHolidayIdx(2013, 12, false, 29));
  }

  public void testGetAnimalIdx() {
    assertEquals(5, data.getAnimalsYearIdx(2013));
    assertEquals(0, data.getAnimalsYearIdx(2008));
  }

  public void testGetGanIdx() {
    assertEquals(9, data.getGanIdx(2013));
    assertEquals(4, data.getGanIdx(2008));
  }

  public void testGetZhiIdx() {
    assertEquals(5, data.getZhiIdx(2013));
    assertEquals(0, data.getZhiIdx(2008));
  }

  public void testGetAncientHourIdx() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 3);
    cal.set(Calendar.MINUTE, 3);
    assertEquals(2, data.getAncientHourIdx(cal));
    cal.set(Calendar.HOUR_OF_DAY, 23);
    assertEquals(0, data.getAncientHourIdx(cal));
    cal.set(Calendar.HOUR_OF_DAY, 0);
    assertEquals(0, data.getAncientHourIdx(cal));
  }

}
