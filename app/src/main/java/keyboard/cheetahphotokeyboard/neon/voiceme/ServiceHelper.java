
package keyboard.cheetahphotokeyboard.neon.voiceme;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiceHelper extends Service {

    private static final String TAG = "ServiceHelper";

    private final IBinder service = new ServiceHelperBinder();

    private Callback mobile;

    @Override
    public IBinder onBind(Intent arg0) {
        return service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "#onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "#onDestroy");
    }

    public void startRecognition(String languageLocale, Callback callback) {
        Log.i(TAG, "#startRecognition");
        mobile = callback;
        Intent vioce = new Intent(this, Voice_Screen_help.class);
        vioce.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(vioce);
    }

    public void notifyResult(String recognitionResult) {
        if (mobile != null) {
            mobile.onResult(recognitionResult);
        }
    }

    public interface Callback {
        void onResult(String recognitionResult);
    }

    public class ServiceHelperBinder extends Binder {
        ServiceHelper getService() {
            return ServiceHelper.this;
        }
    }
}
