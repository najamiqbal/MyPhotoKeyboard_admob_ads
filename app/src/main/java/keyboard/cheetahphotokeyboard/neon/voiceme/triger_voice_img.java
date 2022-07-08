package keyboard.cheetahphotokeyboard.neon.voiceme;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import java.util.List;
import java.util.Map;

class triger_voice_img implements Voice_me_triger {

    private static final String SOPUNDTRIGER = "voice";

    private static final String GOOGELADS = "com.google.android";

    private final InputMethodService setingmarthed;

    public triger_voice_img(InputMethodService teriger) {
        setingmarthed = teriger;
    }

    @Override
    public void startVoiceRecognition(String bhasha) {
        InputMethodManager trigermet = getInputMethodManager(setingmarthed);

        InputMethodInfo information = getVoiceImeInputMethodInfo(trigermet);

        if (information == null) {
            return;
        }

        trigermet.setInputMethodAndSubtype(setingmarthed.getWindow().getWindow()
                .getAttributes().token,
                information.getId(),
                getVoiceImeSubtype(trigermet, information));
    }

    private static InputMethodManager getInputMethodManager(InputMethodService setingmethed) {
        return (InputMethodManager) setingmethed
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private InputMethodSubtype getVoiceImeSubtype(InputMethodManager trigerserv, InputMethodInfo inputMethodInfo) throws SecurityException, IllegalArgumentException {
        Map<InputMethodInfo, List<InputMethodSubtype>> map = trigerserv.getShortcutInputMethodsAndSubtypes();
        List<InputMethodSubtype> list = map.get(inputMethodInfo);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    private static InputMethodInfo getVoiceImeInputMethodInfo(InputMethodManager inputMethodManager) throws SecurityException, IllegalArgumentException {
        for (InputMethodInfo information : inputMethodManager.getEnabledInputMethodList()) {
            for (int i = 0; i < information.getSubtypeCount(); i++) {
                InputMethodSubtype subtype = information.getSubtypeAt(i);
                if (SOPUNDTRIGER.equals(subtype.getMode())) {
                    if (information.getComponent().getPackageName().startsWith(GOOGELADS)) {
                        return information;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isInstalled(InputMethodService installmet) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return false;
        }

        InputMethodInfo voicemethed = getVoiceImeInputMethodInfo(
                getInputMethodManager(installmet));

        if (voicemethed == null) {
            return false;
        }

        return voicemethed.getSubtypeCount() > 0;
    }

    @Override
    public void onStartInputView() {
    }
}
