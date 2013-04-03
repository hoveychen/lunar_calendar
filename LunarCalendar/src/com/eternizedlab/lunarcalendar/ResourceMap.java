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

import java.util.Locale;

import android.util.SparseIntArray;

/**
 * The mapping for multiple languages.
 *
 * @author hoveychen@gmail.com
 *
 */
public class ResourceMap {

  private SparseIntArray getChineseMap() {
    SparseIntArray map = new SparseIntArray();
    map.put(R.string.first_month, R.string.cn_first_month);
    map.put(R.string.leap, R.string.cn_leap);
    map.put(R.string.tenth, R.string.cn_tenth);
    map.put(R.string.thirtieth, R.string.cn_thirtieth);
    map.put(R.string.twentieth, R.string.cn_twentieth);
    map.put(R.array.animal_array, R.array.cn_animal_array);
    map.put(R.array.decade_number_array, R.array.cn_decade_number_array);
    map.put(R.array.unit_number_array, R.array.cn_unit_number_array);
    map.put(R.array.gan_array, R.array.cn_gan_array);
    map.put(R.array.zhi_array, R.array.cn_zhi_array);
    map.put(R.array.holiday_array, R.array.cn_holiday_array);
    map.put(R.array.term_array, R.array.cn_term_array);
    return map;
  }

  private SparseIntArray getEnglishMap() {
    SparseIntArray map = new SparseIntArray();
    map.put(R.string.first_month, R.string.en_first_month);
    map.put(R.string.leap, R.string.en_leap);
    map.put(R.string.tenth, R.string.en_tenth);
    map.put(R.string.thirtieth, R.string.en_thirtieth);
    map.put(R.string.twentieth, R.string.en_twentieth);
    map.put(R.array.decade_number_array, R.array.en_decade_number_array);
    map.put(R.array.unit_number_array, R.array.en_unit_number_array);
    map.put(R.array.animal_array, R.array.en_animal_array);
    map.put(R.array.gan_array, R.array.en_gan_array);
    map.put(R.array.zhi_array, R.array.en_zhi_array);
    map.put(R.array.holiday_array, R.array.en_holiday_array);
    map.put(R.array.term_array, R.array.en_term_array);
    return map;
  }

  private SparseIntArray currentRessourceMap;

  public ResourceMap(Locale locale) {
    if (Locale.CHINA.equals(locale)) {
      currentRessourceMap = getChineseMap();
    } else {
      currentRessourceMap = getEnglishMap();
    }
  }

  public int getResourceId(int resId) {
    return currentRessourceMap.get(resId, resId);
  }
}
