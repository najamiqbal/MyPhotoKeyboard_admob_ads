
package keyboard.cheetahphotokeyboard.neon.xml_file;

import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import keyboard.cheetahphotokeyboard.neon.R;


public class My_Keybord_Scren_action extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.prefs_actions);
        SharedPreferences refernce = getPreferenceManager().getSharedPreferences();
        refernce.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public void onSharedPreferenceChanged(SharedPreferences refernce, String chavi) {
        (new BackupManager(this)).dataChanged();
    }
}
