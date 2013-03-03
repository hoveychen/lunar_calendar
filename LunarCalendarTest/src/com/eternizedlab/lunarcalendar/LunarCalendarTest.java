package com.eternizedlab.lunarcalendar;

import java.util.Calendar;
import java.util.Locale;

import junit.framework.TestCase;

import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;

public class LunarCalendarTest extends TestCase {

  private LunarCalendar calendar;
  private LunarData data;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    data = new LunarData(Locale.CHINA);
    calendar = new LunarCalendar(data);
  }

  class TestData {
    LunarDate lunarDate;
    Calendar calendar;

    TestData(LunarDate lunarDate, int year, int month, int day) {
      this.lunarDate = lunarDate;
      calendar = Calendar.getInstance();
      calendar.set(year, month - 1, day);
    }
  }

  private TestData[] testDatas = new TestData[] {
      new TestData(new LunarDate(1900, 1, 1, false), 1900, 1, 31),
      new TestData(new LunarDate(1900, 1, 2, false), 1900, 2, 1),
      new TestData(new LunarDate(1900, 11, 11, false), 1901, 1, 1),
      new TestData(new LunarDate(1900, 12, 30, false), 1901, 2, 18),
      new TestData(new LunarDate(1901, 1, 1, false), 1901, 2, 19),
      new TestData(new LunarDate(1902, 11, 18, false), 1902, 12, 17),
      new TestData(new LunarDate(1903, 10, 29, false), 1903, 12, 17),
      new TestData(new LunarDate(2013, 1, 10, false), 2013, 2, 19),
      new TestData(new LunarDate(1997, 5, 27, false), 1997, 7, 1),
      new TestData(new LunarDate(2001, 4, 1, true), 2001, 5, 23),
      new TestData(new LunarDate(2005, 9, 1, false), 2005, 10, 3),
      new TestData(new LunarDate(1997, 1, 1, false), 1997, 2, 7), };

  public void testTransformLunarDate() {
    for (TestData testData : testDatas) {
      assertEquals(testData.lunarDate,
          calendar.transformLunarDate(testData.calendar));
    }
  }
}
