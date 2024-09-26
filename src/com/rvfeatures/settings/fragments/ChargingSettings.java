package com.rvfeatures.settings.fragments;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.util.Log;

public class ChargingSettings extends PreferenceFragmentCompat {
    private static final String BYPASS_CHARGING_PATH = "/sys/class/power_supply/battery/input_suspend";
    private static final String KEY_BYPASS_CHARGING = "bypass_charging_key";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.charging_settings_preferences, rootKey);

        SwitchPreferenceCompat bypassChargingSwitch = findPreference(KEY_BYPASS_CHARGING);

        if (bypassChargingSwitch != null) {
            bypassChargingSwitch.setChecked(checkCurrentBypassChargingStatus());
            bypassChargingSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                setBypassChargingValue(isEnabled ? "1" : "0");
                return true;
            });
        }
    }

    private boolean checkCurrentBypassChargingStatus() {
        try {
            File file = new File(BYPASS_CHARGING_PATH);
            if (file.exists() && file.canRead()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                fis.read(buffer);
                fis.close();
                String currentValue = new String(buffer).trim();
                return "1".equals(currentValue);
            }
        } catch (IOException e) {
            Log.e("ChargingSettings", "Failed to read bypass charging", e);
        }
        return false;
    }

    private void setBypassChargingValue(String value) {
        try {
            File file = new File(BYPASS_CHARGING_PATH);
            if (file.exists() && file.canWrite()) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(value.getBytes());
                fos.close();
            }
        } catch (IOException e) {
            Log.e("ChargingSettings", "Failed to write bypass charging", e);
        }
    }
}