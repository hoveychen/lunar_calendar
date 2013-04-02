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

/**
 * Helper to extract app resource.
 *
 * @author hoveychen@gmail.com
 *
 */
public final class RenderHelper {
  protected static Context context;

  /**
   * Save context into helper. Must be called before any further invoking.
   *
   * @param context
   *          application context
   */
  public static void initialize(Context context) {
    RenderHelper.context = context;
  }

  private RenderHelper() {
  }

  public static String getString(int resId) {
    return context.getString(resId);
  }

  public static String getString(int resId, Object... args) {
    return context.getString(resId, args);
  }

  public static String getStringFromList(int arrayResId, int idx) {
    String[] array = context.getResources().getStringArray(arrayResId);
    if (idx < 0 || idx >= array.length) {
      return "";
    } else {
      return array[idx];
    }
  }

}
