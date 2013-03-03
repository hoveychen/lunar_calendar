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
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
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
    
    bindPreferenceSummaryToValue(findPreference(LunarCalendarExtension.PREF_STATUS_NUMBER_FORMAT));
    bindPreferenceSummaryToValue(findPreference(LunarCalendarExtension.PREF_STATUS_NUMBER_OF_LINES));
  }

  /**
   * A preference value change listener that updates the preference's summary to
   * reflect its new value.
   */
  private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
      String stringValue = value.toString();

      if (preference instanceof ListPreference) {
        // For list preferences, look up the correct display value in
        // the preference's 'entries' list.
        ListPreference listPreference = (ListPreference) preference;
        int index = listPreference.findIndexOfValue(stringValue);

        // Set the summary to reflect the new value.
        preference.setSummary(index >= 0 ? (listPreference.getEntries()[index])
            .toString().replaceAll("%", "%%") : null);

      } else {
        // For all other preferences, set the summary to the value's
        // simple string representation.
        preference.setSummary(stringValue);
      }
      return true;
    }
  };

  /**
   * Binds a preference's summary to its value. More specifically, when the
   * preference's value is changed, its summary (line of text below the
   * preference title) is updated to reflect the value. The summary is also
   * immediately updated upon calling this method. The exact display format is
   * dependent on the type of preference.
   */
  public static void bindPreferenceSummaryToValue(Preference preference) {
    setAndCallPreferenceChangeListener(preference,
        sBindPreferenceSummaryToValueListener);
  }

  /**
   * When the preference's value is changed, trigger the given listener. The
   * listener is also immediately called with the preference's current value
   * upon calling this method.
   */
  public static void setAndCallPreferenceChangeListener(Preference preference,
      Preference.OnPreferenceChangeListener listener) {
    // Set the listener to watch for value changes.
    preference.setOnPreferenceChangeListener(listener);

    // Trigger the listener immediately with the preference's
    // current value.
    listener.onPreferenceChange(preference,
        PreferenceManager.getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), ""));
  }
}
