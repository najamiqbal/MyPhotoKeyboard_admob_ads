package keyboard.cheetahphotokeyboard.neon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import keyboard.cheetahphotokeyboard.neon.ADS_API.Constant;
import keyboard.cheetahphotokeyboard.neon.R;


public class Keybord_Secure_Screen extends Activity {

    private WebView network;
    private static final String TAG_home = "Home_Activity";
    private ProgressDialog prgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.secure_activity);

        this.network = (WebView) findViewById(R.id.webview);

        WebSettings srvices = network.getSettings();
        srvices.setJavaScriptEnabled(true);
        network.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog atrat_box = new AlertDialog.Builder(this).create();

        prgress = ProgressDialog.show(Keybord_Secure_Screen.this, "Please Wait...", "Loading...");

        network.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG_home, "Processing WebView url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG_home, "Finished loading URL: " + url);
                if (prgress.isShowing()) {
                    prgress.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG_home, "Error: " + description);
                atrat_box.setTitle("Error");
                atrat_box.setMessage(description);
                atrat_box.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                atrat_box.show();
            }
        });
        network.loadUrl(Constant.privacy_policy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}