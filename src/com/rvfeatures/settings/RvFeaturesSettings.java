package com.rvfeatures.settings;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

public class RvFeaturesSettings extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.rvfeatures_settings);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.RVFEATURES_SETTINGS;
    }
}