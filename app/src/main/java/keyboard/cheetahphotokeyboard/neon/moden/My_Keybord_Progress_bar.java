package keyboard.cheetahphotokeyboard.neon.moden;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class My_Keybord_Progress_bar extends Progress_bar_info {

    private static Pattern FLOAT_RE = Pattern.compile("(\\d+\\.?\\d*).*");

    public My_Keybord_Progress_bar(Context appcont, AttributeSet atrest) {
        super(appcont, atrest);
        init(appcont, atrest);
    }

    private float floatFromString(String refernce) {
        Matcher count = FLOAT_RE.matcher(refernce);
        if (!count.matches()) return 0.0f;
        return Float.valueOf(count.group(1));
    }
    
    @Override
    protected Float onGetDefaultValue(TypedArray typing, int index) {
        return floatFromString(typing.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean values, Object defaultValue) {
        if (values) {
            setVal(floatFromString(getPersistedString("0.0")));
        } else {
            setVal(Float.valueOf((Float) defaultValue));
        }
        savePrevVal();
    }
    
    @Override
    protected void onDialogClosed(boolean alrttrue) {
        if (!alrttrue) {
            restoreVal();
            return;
        }
        if (shouldPersist()) {
            savePrevVal();
            persistString(getValString());
        }
        notifyChanged();
    }
}