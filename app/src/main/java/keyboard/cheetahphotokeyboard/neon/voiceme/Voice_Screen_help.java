package keyboard.cheetahphotokeyboard.neon.voiceme;

import android.annotation.SuppressLint;
 import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class Voice_Screen_help extends Activity {

    private static final String TAG = "Voice_Screen_help";
    private static final int PERMISIONONE = 1;
    private Seting_Keybord_Voice setingmetheds;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setingmetheds = new Seting_Keybord_Voice();
        Intent speesh = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speesh.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        speesh.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speesh.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        if (bundle != null) {
            String bhahsacode = bundle.getString(RecognizerIntent.EXTRA_LANGUAGE);
            if (bhahsacode != null) {
                speesh.putExtra(RecognizerIntent.EXTRA_LANGUAGE, bhahsacode);
            }
        }
        startActivityForResult(speesh, PERMISIONONE);
    }

    @Override
    protected void onActivityResult(int pertmision, int record, Intent file) {
        if (pertmision == PERMISIONONE && file != null && file.hasExtra(RecognizerIntent.EXTRA_RESULTS)) {
            ArrayList<String> recordfile = file.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            createResultDialog(recordfile.toArray(new String[recordfile.size()])).show();
        } else {
            notifyResult(null);
        }
    }

    @SuppressLint("NewApi")
	private Dialog createResultDialog(final String[] recognitionResults) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(this);
        } else {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog_NoActionBar);
        }

        builder.setItems(recognitionResults, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { notifyResult(recognitionResults[which]);
            }
        });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                notifyResult(null);
            }
        });

        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyResult(null);
            }
        });

        return builder.create();
    }

    private void notifyResult(String result) {
        setingmetheds.notifyResult(this, result);
        finish();
    }
}
