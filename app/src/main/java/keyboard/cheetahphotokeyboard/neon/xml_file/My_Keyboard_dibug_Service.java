
package keyboard.cheetahphotokeyboard.neon.xml_file;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.util.Log;

import keyboard.cheetahphotokeyboard.neon.R;


public class My_Keyboard_dibug_Service extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "My_Keyboard_dibug_Service";
    private static final String KEYBORDMODE = "debug_mode";

    private CheckBoxPreference checkbox;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.prefs_for_debug);
        SharedPreferences refernce = getPreferenceManager().getSharedPreferences();
        refernce.registerOnSharedPreferenceChangeListener(this);

        checkbox = (CheckBoxPreference) findPreference(KEYBORDMODE);
        updateDebugMode();
    }

    public void onSharedPreferenceChanged(SharedPreferences refrence, String chavi) {
        if (chavi.equals(KEYBORDMODE)) {
            if (checkbox != null) {
                checkbox.setChecked(refrence.getBoolean(KEYBORDMODE, false));
                updateDebugMode();
            }
        }
    }

    private void updateDebugMode() {
        if (checkbox == null) {
            return;
        }
        boolean isDebugMode = checkbox.isChecked();
        String version = "";
        try {
            PackageInfo pkg = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = "Version " + pkg.versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Could not find version info.");
        }
        if (!isDebugMode) {
            checkbox.setTitle(version);
            checkbox.setSummary("");
        } else {
            checkbox.setTitle(getResources().getString(R.string.prefs_debug_mode));
            checkbox.setSummary(version);
        }
    }
}
