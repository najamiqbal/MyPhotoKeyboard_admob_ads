package keyboard.cheetahphotokeyboard.neon.ADS_API;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import keyboard.cheetahphotokeyboard.neon.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class InterAd {

    private static InterAd mInstance;
    public InterstitialAd interstitial;
    AdRequest adRequest;
    MyCallback myCallback;
 

    public static InterAd getInstance() {
        if (mInstance == null) {
            mInstance = new InterAd();
        }
        return mInstance;
    }

    public void loadintertialads(final Context context) {


        MobileAds.initialize(context,Constant.ADMOB_APP_ID);
        interstitial = new InterstitialAd(context);


        interstitial.setAdUnitId(Constant.INTERSTITIAL);

        adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
                interstitial.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

            }
        });
    }

    public void displayInterstitial(Context context, MyCallback _myCallback) {
        this.myCallback = _myCallback;
        if (interstitial != null) {
            if (interstitial.isLoaded()) {
                interstitial.show();

            }else if (!interstitial.isLoading()){
                interstitial.loadAd(adRequest);
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
            } else {
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
            }
        }else {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        }
    }

    public interface MyCallback {
        void callbackCall();
    }

    public static boolean isInternetOn(Context con) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager)con.getSystemService(CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            /*Toast InterConnection Required*/

            return false;
        }

        return false;
    }

    public void alert(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setMessage("It looks like your Internet connection is off. Please turn it on and try again");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
