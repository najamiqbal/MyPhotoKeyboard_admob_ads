package keyboard.cheetahphotokeyboard.neon;

import android.content.Context;
import android.util.AttributeSet;

import keyboard.cheetahphotokeyboard.neon.moden.My_Keybord_Progress_bar;

public class Refernce_Keyword_Sound extends My_Keybord_Progress_bar {
    public Refernce_Keyword_Sound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public void onChange(float val) {
        LatinIME latme = LatinIME.keywordstring;
        if (latme != null) latme.vibrate((int) val);
    }
}