
package keyboard.cheetahphotokeyboard.neon.seting;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class Oto_Text_Refrence_Sumry extends ListPreference {

    private static final String TAG = "HK/Oto_Text_Refrence_Sumry";

    public Oto_Text_Refrence_Sumry(Context appcont) {
        super(appcont);
    }

    public Oto_Text_Refrence_Sumry(Context appcont, AttributeSet appatrsess) {
        super(appcont, appatrsess);
    }

    private void trySetSummary() {
        CharSequence entry = null;
        try {
            entry = getEntry();
        } catch (ArrayIndexOutOfBoundsException array) {
        }
        if (entry != null) {
            String percent = "percent";
            setSummary(entry.toString().replace("%", " " + percent));
        }
    }

    @Override
    public void setEntries(CharSequence[] entries) {
        super.setEntries(entries);
        trySetSummary();
    }

    @Override
    public void setEntryValues(CharSequence[] entryValues) {
        super.setEntryValues(entryValues);
        trySetSummary();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        trySetSummary();
    }
}
