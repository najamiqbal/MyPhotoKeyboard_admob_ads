
package keyboard.cheetahphotokeyboard.neon.voiceme;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Voise_service_methed_triger {

    private final InputMethodService methedstriger;

    private BroadcastReceiver bordcast;

    private Voice_me_triger voisekeybord;

    private triger_voice_img voiceimg;
    private Voice_triger_Set_keybord trigerimgkey;

    public Voise_service_methed_triger(InputMethodService seting) {
        methedstriger = seting;
        voisekeybord = getTrigger();
    }

    private Voice_me_triger getTrigger() {
        if (triger_voice_img.isInstalled(methedstriger)) {
            return getImeTrigger();
        } else if (Voice_triger_Set_keybord.isInstalled(methedstriger)) {
            return getIntentTrigger();
        } else {
            return null;
        }
    }

    private Voice_me_triger getIntentTrigger() {
        if (trigerimgkey == null) {
            trigerimgkey = new Voice_triger_Set_keybord(methedstriger);
        }
        return trigerimgkey;
    }

    private Voice_me_triger getImeTrigger() {
        if (voiceimg == null) {
            voiceimg = new triger_voice_img(methedstriger);
        }
        return voiceimg;
    }

    public boolean isInstalled() {
        return voisekeybord != null;
    }

    public boolean isEnabled() {
        return isNetworkAvailable();
    }

    public void startVoiceRecognition() {
        startVoiceRecognition(null);
    }

    public void startVoiceRecognition(String bhasha) {
        if (voisekeybord != null) {
            voisekeybord.startVoiceRecognition(bhasha);
        }
    }

    public void onStartInputView() {
        if (voisekeybord != null) {
            voisekeybord.onStartInputView();
        }

        voisekeybord = getTrigger();
    }

    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager workingmenge = (ConnectivityManager) methedstriger
                    .getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            final NetworkInfo network = workingmenge.getActiveNetworkInfo();
            return network != null && network.isConnected();
        } catch (SecurityException e) {
            return true;
        }
    }

    public void register(final Listener listener) {
        bordcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    listener.onVoiceImeEnabledStatusChange();
                }
            }
        };
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        methedstriger.registerReceiver(bordcast, filter);
    }

    public void unregister(Context context) {
        if (bordcast != null) {
            methedstriger.unregisterReceiver(bordcast);
            bordcast = null;
        }
    }

    public interface Listener {

        void onVoiceImeEnabledStatusChange();
    }
}
