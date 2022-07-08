package keyboard.cheetahphotokeyboard.neon.seting.text;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class Oto_Text_Edite_My_KeyBord extends EditTextPreference {

    public Oto_Text_Edite_My_KeyBord(Context appcont) {
        super(appcont);
    }

    public Oto_Text_Edite_My_KeyBord(Context appcont, AttributeSet appatrsess) {
        super(appcont, appatrsess);
    }

    public Oto_Text_Edite_My_KeyBord(Context appcont, AttributeSet appatrsess,
                                     int appdeful) {
        super(appcont, appatrsess, appdeful);
    }

    @Override
    public void setText(String typing) {
        super.setText(typing);
        setSummary(typing);
    }
}
