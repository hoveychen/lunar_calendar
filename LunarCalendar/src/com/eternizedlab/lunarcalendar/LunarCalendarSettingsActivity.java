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

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class LunarCalendarSettingsActivity extends PreferenceActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActionBar().setIcon(R.drawable.ic_extension_lunarcalendar);
    getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    setupSimplePreferencesScreen();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("deprecation")
  private void setupSimplePreferencesScreen() {
    // In the simplified UI, fragments are not used at all and we instead
    // use the older PreferenceActivity APIs.

    // Add 'general' preferences.
    addPreferencesFromResource(R.xml.pref_calendar);
  }
}
