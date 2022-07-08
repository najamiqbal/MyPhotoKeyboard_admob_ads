package keyboard.cheetahphotokeyboard.neon.other;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;



import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import keyboard.cheetahphotokeyboard.neon.R;


public final class My_Global_Service {

    protected static final String TAG = "HK/Globals";
    public int zerobyone = 0x1;
    public float stratsize = 1.0f;
    public boolean clickview = false;
    public String reviewsymbole = "!?,.";
    public int typingbordzero = 0;
    public int typingvaluetwo = 2;
    public boolean compactModeEnabled = false;
    public int ymglobezero = 0;
    public int interchavi = 0;
    public int mtadta = 0;
    public float soundeffect = 0.0f;
    public int chaviselected = 0;
    public boolean selectedkeybord = true;
    public boolean keyshifevalue = false;
    public float refernctext = 1.0f;
    public float symbolekey = 1.0f;
    public int uplodekeybord = 0;
    public int keyboardMode = 0;
    public boolean bolenglobemode = false;
    public float typingwordhit = 40.0f;
     public int haelpspling = 0;
    public int readingmode = 1;
    public int seekbarwatch = 400;
    public String entertypingtext;
    public String etertextname;
    public int entertextid;
    public int entertextmethed;
    public Locale defultevalue = Locale.getDefault();
    private Map<String, BooleanPref> mapbolen = new HashMap<String, BooleanPref>();
    private Map<String, StringPref> referncevalue = new HashMap<String, StringPref>();
    public static final int FLAG_PREF_NONE = 0;
    public static final int FLAG_PREF_NEED_RELOAD = 0x1;
    public static final int FLAG_PREF_NEW_PUNC_LIST = 0x2;
    public static final int FLAG_PREF_RECREATE_INPUT_VIEW = 0x4;
    public static final int FLAG_PREF_RESET_KEYBOARDS = 0x8;
    public static final int FLAG_PREF_RESET_MODE_OVERRIDE = 0x10;
    private int mCurrentFlags = 0;
    
    private interface BooleanPref {
        void set(boolean val);
        boolean getDefault();
        int getFlags();
    }

    private interface StringPref {
        void set(String val);
        String getDefault();
        int getFlags();
    }

    public void initPrefs(SharedPreferences prefs, Resources resources) {
        final Resources res = resources;

        addBooleanPref("pref_compact_mode_enabled", new BooleanPref() {
            public void set(boolean val) { compactModeEnabled = val; Log.i(TAG, "Setting compactModeEnabled to " + val); }
            public boolean getDefault() { return res.getBoolean(R.bool.default_compact_mode_enabled); }
            public int getFlags() { return FLAG_PREF_RESET_MODE_OVERRIDE; }
        });

        addStringPref("pref_keyboard_mode_portrait", new StringPref() {
            public void set(String val) { typingbordzero = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_keyboard_mode_portrait); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS | FLAG_PREF_RESET_MODE_OVERRIDE; }
        });

        addStringPref("pref_keyboard_mode_landscape", new StringPref() {
            public void set(String val) { typingvaluetwo = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_keyboard_mode_landscape); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS | FLAG_PREF_RESET_MODE_OVERRIDE; }
        });

        addStringPref("pref_slide_keys_int", new StringPref() {
            public void set(String val) { uplodekeybord = Integer.valueOf(val); }
            public String getDefault() { return "0"; }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        addBooleanPref("pref_touch_pos", new BooleanPref() {
            public void set(boolean val) { clickview = val; }
            public boolean getDefault() { return false; }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        addStringPref("pref_popup_content", new StringPref() {
            public void set(String val) { zerobyone = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_popup_content); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_suggested_punctuation", new StringPref() {
            public void set(String val) { reviewsymbole = val; }
            public String getDefault() { return res.getString(R.string.suggested_punctuations_default); }
            public int getFlags() { return FLAG_PREF_NEW_PUNC_LIST; }
        });

        addStringPref("pref_label_scale", new StringPref() {
            public void set(String val) { refernctext = Float.valueOf(val); }
            public String getDefault() { return "1.0"; }
            public int getFlags() { return FLAG_PREF_RECREATE_INPUT_VIEW; }
        });

        addStringPref("pref_candidate_scale", new StringPref() {
            public void set(String val) { symbolekey = Float.valueOf(val); }
            public String getDefault() { return "1.0"; }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_top_row_scale", new StringPref() {
            public void set(String val) { stratsize = Float.valueOf(val); }
            public String getDefault() { return "1.0"; }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_chording_ctrl_key", new StringPref() {
            public void set(String val) { ymglobezero = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_chording_ctrl_key); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_chording_alt_key", new StringPref() {
            public void set(String val) { interchavi = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_chording_alt_key); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_chording_meta_key", new StringPref() {
            public void set(String val) { mtadta = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_chording_meta_key); }
            public int getFlags() { return FLAG_PREF_RESET_KEYBOARDS; }
        });

        addStringPref("pref_click_volume", new StringPref() {
            public void set(String val) { soundeffect = Float.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_click_volume); }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        addStringPref("pref_click_method", new StringPref() {
            public void set(String val) { chaviselected = Integer.valueOf(val); }
            public String getDefault() { return res.getString(R.string.default_click_method); }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        addBooleanPref("pref_caps_lock", new BooleanPref() {
            public void set(boolean val) { selectedkeybord = val; }
            public boolean getDefault() { return res.getBoolean(R.bool.default_caps_lock); }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        addBooleanPref("pref_shift_lock_modifiers", new BooleanPref() {
            public void set(boolean val) { keyshifevalue = val; }
            public boolean getDefault() { return res.getBoolean(R.bool.default_shift_lock_modifiers); }
            public int getFlags() { return FLAG_PREF_NONE; }
        });

        for (String key : mapbolen.keySet()) {
            BooleanPref refernce = mapbolen.get(key);
            refernce.set(prefs.getBoolean(key, refernce.getDefault()));
        }
        for (String chavi : referncevalue.keySet()) {
            StringPref refernce = referncevalue.get(chavi);
            refernce.set(prefs.getString(chavi, refernce.getDefault()));
        }
    }
    
    public void sharedPreferenceChanged(SharedPreferences refrence, String chavi) {
        boolean found = false;
        mCurrentFlags = FLAG_PREF_NONE;
        BooleanPref refernce = mapbolen.get(chavi);
        if (refernce != null) {
            found = true;
            refernce.set(refrence.getBoolean(chavi, refernce.getDefault()));
            mCurrentFlags |= refernce.getFlags();
        }
        StringPref stringn = referncevalue.get(chavi);
        if (stringn != null) {
            found = true;
            stringn.set(refrence.getString(chavi, stringn.getDefault()));
            mCurrentFlags |= stringn.getFlags();
        }
    }
    
    public boolean hasFlag(int flag) {
        if ((mCurrentFlags & flag) != 0) {
            mCurrentFlags &= ~flag;
            return true;
        }
        return false;
    }
    
    public int unhandledFlags() {
        return mCurrentFlags;
    }

    private void addBooleanPref(String key, BooleanPref setter) {
        mapbolen.put(key, setter);
    }

    private void addStringPref(String key, StringPref setter) {
        referncevalue.put(key, setter);
    }
}
