package com.rvfeatures.settings;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RvFeaturesSettings extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.rvfeatures_settings);

        if (!isKernelCompatible()) {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            PreferenceCategory category = findPreference("rvfeatures");
            if (category != null) {
                preferenceScreen.removePreference(category);
            }
        }
    }

    private boolean isKernelCompatible() {
        try {
            Process process = Runtime.getRuntime().exec("uname -r");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String kernelVersion = reader.readLine();
            reader.close();

            return kernelVersion != null && kernelVersion.contains("4.9.377-RvKernel-Be4-v0.6"); // ganti dengan nama kernel yang sesuai
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.RVFEATURES_SETTINGS;
    }
}