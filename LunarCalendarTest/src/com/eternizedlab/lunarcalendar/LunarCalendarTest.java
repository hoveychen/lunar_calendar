package com.eternizedlab.lunarcalendar;

import com.eternizedlab.lunarcalendar.LunarCalendar;
import com.eternizedlab.lunarcalendar.LunarCalendar.LunarDate;
import com.eternizedlab.lunarcalendar.LunarData;
import com.eternizedlab.lunarcalendar.LunarCalendar.SolarDate;

import junit.framework.TestCase;

import java.util.Locale;

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
    SolarDate solarDate;
    TestData(LunarDate lunarDate, SolarDate solarDate) {
      this.lunarDate = lunarDate;
      this.solarDate = solarDate;
    }
  }
  
  private TestData[] testDatas= new TestData[] {
      new TestData(new LunarDate(1900, 1, 1, false), new SolarDate(1900, 1, 31)),
      new TestData(new LunarDate(1900, 1, 2, false), new SolarDate(1900, 2, 1)),
      new TestData(new LunarDate(1900, 11, 11, false), new SolarDate(1901, 1, 1)),
      new TestData(new LunarDate(1900, 12, 30, false), new SolarDate(1901, 2, 18)),
      new TestData(new LunarDate(1901, 1, 1, false), new SolarDate(1901, 2, 19)),
      new TestData(new LunarDate(1902, 11, 18, false), new SolarDate(1902, 12, 17)),
      new TestData(new LunarDate(1903, 10, 29, false), new SolarDate(1903, 12, 17)),
      new TestData(new LunarDate(2013, 1, 10, false), new SolarDate(2013, 2, 19)),
      new TestData(new LunarDate(1997, 5, 27, false), new SolarDate(1997, 7, 1)),
      new TestData(new LunarDate(2001, 4, 1, true), new SolarDate(2001, 5, 23)),
      new TestData(new LunarDate(2005, 9, 1, false), new SolarDate(2005, 10, 3)),
      new TestData(new LunarDate(1997, 1, 1, false), new SolarDate(1997, 2, 7)),
  };
  
  public void testTransformLunarDate() {
     for (TestData testData : testDatas) {
       LunarDate expected = testData.lunarDate;
       LunarDate actual = calendar.transformLunarDate(testData.solarDate);
       assertEquals(expected, actual);
     }
  }
}
