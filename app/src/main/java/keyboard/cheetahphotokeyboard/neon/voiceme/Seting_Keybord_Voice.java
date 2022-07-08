
package keyboard.cheetahphotokeyboard.neon.voiceme;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

class Seting_Keybord_Voice {

    private static final String TAG = "Seting_Keybord_Voice";

    private final Voice_triger_Set_keybord.Callback mobilebefore;

    public Seting_Keybord_Voice() {
        this(null);
    }

    public Seting_Keybord_Voice(Voice_triger_Set_keybord.Callback mobilebef) {
        mobilebefore = mobilebef;
    }

    public void startVoiceRecognition(final Context appcont, final String bhasha) {
        final ConnectionRequest conReq = new ConnectionRequest(bhasha);
        conReq.setServiceCallback(new ServiceHelper.Callback() {

            @Override
            public void onResult(final String recognitionResult) {
                mobilebefore.onRecognitionResult(recognitionResult);
                appcont.unbindService(conReq);
            }
        });

        appcont.bindService(new Intent(appcont,
                ServiceHelper.class), conReq, Context.BIND_AUTO_CREATE);
    }

    public void notifyResult(Context appcont, String recoerd) {
        ServiceConnection services = new ConnectionResponse(appcont, recoerd);
        appcont.bindService(new Intent(appcont,
                ServiceHelper.class), services, Context.BIND_AUTO_CREATE);
    }

    private class ConnectionRequest implements ServiceConnection {

        private final String mLanguageCode;

        private ServiceHelper.Callback mServiceCallback;

        private ConnectionRequest(String languageCode) {
            mLanguageCode = languageCode;
        }

        private void setServiceCallback(ServiceHelper.Callback callback) {
            mServiceCallback = callback;
        }

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceHelper use =
                    ((ServiceHelper.ServiceHelperBinder) service).getService();
            use.startRecognition(mLanguageCode, mServiceCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
        }
    }

    private class ConnectionResponse implements ServiceConnection {

        private final String recordfile;
        private final Context mappcont;

        private ConnectionResponse(Context appcont, String record) {
            recordfile = record;
            mappcont = appcont;
        }

        @Override
        public void onServiceDisconnected(ComponentName text) {
        }

        @Override
        public void onServiceConnected(ComponentName text, IBinder seting) {
            ServiceHelper usefull = ((ServiceHelper.ServiceHelperBinder) seting).getService();
            usefull.notifyResult(recordfile);
            mappcont.unbindService(this);
        }
    }
}
