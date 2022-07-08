package keyboard.cheetahphotokeyboard.neon.voiceme;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Voice_triger_Set_keybord implements Voice_me_triger {

    private static final String TAG = "VoiceIntentApiTrigger";
    private final InputMethodService trigervoice;
    private final Seting_Keybord_Voice setingtriger;
    private String recordsetingmethed;
    private Set<Character> charectername;
    private final Handler setkeybord;
    private IBinder binder;

    public Voice_triger_Set_keybord(InputMethodService setingvoice) {
        trigervoice = setingvoice;

        setingtriger = new Seting_Keybord_Voice(new Callback() {

            @Override
            public void onRecognitionResult(String recordfile) {
                postResult(recordfile);
            }
        });

        charectername = new HashSet<Character>();
        charectername.add('.');
        charectername.add('!');
        charectername.add('?');
        charectername.add('\n');

        setkeybord = new Handler();
    }

    @Override
    public void startVoiceRecognition(String bhasha) {
        binder = trigervoice.getWindow().getWindow().getAttributes().token;
        setingtriger.startVoiceRecognition(trigervoice, bhasha);
    }

    public static boolean isInstalled(InputMethodService setingfile) {
        PackageManager pkg = setingfile.getPackageManager();
        List<ResolveInfo> screen = pkg.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return screen.size() > 0;
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) trigervoice
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void postResult(String recordfile) {
        recordsetingmethed = recordfile;

        getInputMethodManager().showSoftInputFromInputMethod(binder,
                InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onStartInputView() {
        Log.i(TAG, "#onStartInputView");
        if (recordsetingmethed != null) {
            scheduleCommit();
        }
    }

    private void scheduleCommit() {
        setkeybord.post(new Runnable() {

            @Override
            public void run() {
                commitResult();
            }
        });
    }

    private void commitResult() {
        if (recordsetingmethed == null) {
            return;
        }

        String result = recordsetingmethed;

        InputConnection connection = trigervoice.getCurrentInputConnection();

        if (connection == null) {
            Log.i(TAG, "Unable to commit recognition result, as the current input connection " + "is null. Did someone kill the IME?");
            return;
        }

        if (!connection.beginBatchEdit()) {
            Log.i(TAG, "Unable to commit recognition result, as a batch edit cannot start");
            return;
        }

        try {
            ExtractedTextRequest texteq = new ExtractedTextRequest();
            texteq.flags = InputConnection.GET_TEXT_WITH_STYLES;

            ExtractedText entertext = connection.getExtractedText(texteq, 0);

            if (entertext == null) {
                Log.i(TAG, "Unable to commit recognition result, as extracted text is null");
                return;
            }

            if (entertext.text != null) {

                if (entertext.selectionStart != entertext.selectionEnd) {
                    connection.deleteSurroundingText(entertext.selectionStart, entertext.selectionEnd);
                }

                result = format(entertext, result);
            }

            if (!connection.commitText(result, 0)) {
                Log.i(TAG, "Unable to commit recognition result");
                return;
            }

            recordsetingmethed = null;
        } finally {
            connection.endBatchEdit();
        }
    }

    private String format(ExtractedText text, String record) {
        int pos = text.selectionStart - 1;

        while (pos > 0 && Character.isWhitespace(text.text.charAt(pos))) {
            pos--;
        }

        if (pos == -1 || charectername.contains(text.text.charAt(pos))) {
            record = Character.toUpperCase(record.charAt(0)) + record.substring(1);
        }

        if (text.selectionStart - 1 > 0 && !Character.isWhitespace(text.text.charAt(text.selectionStart - 1))) {
            record = " " + record;
        }

        if (text.selectionEnd < text.text.length() && !Character.isWhitespace(text.text.charAt(text.selectionEnd))) {
            record = record + " ";
        }
        return record;
    }


    interface Callback {
        void onRecognitionResult(String recordfile);
    }
}
