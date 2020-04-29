package com.tommy.procrastinationtimer.ui.settings;

import android.os.Bundle;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceFragmentCompat;
import com.tommy.procrastinationtimer.R;

public class SettingsFragment extends PreferenceFragmentCompat implements DialogPreference.TargetFragment {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.settings_preferences, s);
    }
}
