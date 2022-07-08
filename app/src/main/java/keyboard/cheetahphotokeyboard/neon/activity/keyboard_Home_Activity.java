package keyboard.cheetahphotokeyboard.neon.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;


import java.io.File;

import keyboard.cheetahphotokeyboard.neon.ADS_API.Constant;
import keyboard.cheetahphotokeyboard.neon.My_Keyboard_Set_Bhasha;
import keyboard.cheetahphotokeyboard.neon.Keybord_Refernce_Activity;
import keyboard.cheetahphotokeyboard.neon.R;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class keyboard_Home_Activity extends AppCompatActivity {

    private Activity screen;
    Toolbar tollayout;
    SharedPreferences shared_ref;
    Context img_mcont, img_ncont;
    public static final int REQUEST_CAMERA = 3;

    String  Borad_Interstitial ="video";
    InterstitialAd interstitialAd;
    com.google.android.gms.ads.AdView LH_bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Window layout_frem = getWindow();
        layout_frem.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        tollayout = findViewById(R.id.animation_tolbar);
        setSupportActionBar(tollayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        shared_ref = PreferenceManager.getDefaultSharedPreferences(this);

        final Activity that = this;
        screen = this;
        img_mcont = this;
        img_ncont = this.getApplicationContext();

        if (!checkPermissionForCamera()) {
            requestPermissionForCamera();
        }

        MobileAds.initialize(keyboard_Home_Activity.this,Constant.ADMOB_APP_ID);
        final AdRequest adRequest=new AdRequest.Builder().build();
        LH_bottom=findViewById(R.id.diet_detail_bottomAd);
        LH_bottom.loadAd(adRequest);

        interstitialAd =new InterstitialAd(keyboard_Home_Activity.this);
        interstitialAd.setAdUnitId(Constant.INTERSTITIAL);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(adRequest);
            }
        });


        TextView photo_keybord = findViewById(R.id.my_photo_keybord);
//        TextView keyword_text = findViewById(R.id.select_keybord_text);
//        TextView option_name = findViewById(R.id.select_option_name);
//        TextView style_name = findViewById(R.id.keybord_style_name);
//        TextView photo_name = findViewById(R.id.select_my_photo_name);
//        TextView bhasha_name = findViewById(R.id.select_bhasha_name);
//        TextView click_name = findViewById(R.id.click_photo_name);

        Typeface text_style = Typeface.createFromAsset(getAssets(), "raleway_regular.ttf");
        photo_keybord.setTypeface(text_style);
//        keyword_text.setTypeface(text_style);
//        option_name.setTypeface(text_style);
//        style_name.setTypeface(text_style);
//        photo_name.setTypeface(text_style);
//        bhasha_name.setTypeface(text_style);
//        click_name.setTypeface(text_style);

        final ImageView Rel_enable = findViewById(R.id.select_keybord);
        MaterialRippleLayout.on(Rel_enable)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        Rel_enable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);

                    }
                });


            }
        });

        final ImageView Rel_input = findViewById(R.id.select_option);
        MaterialRippleLayout.on(Rel_input)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        Rel_input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    InputMethodManager maneger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    maneger.showInputMethodPicker();

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        InputMethodManager maneger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        maneger.showInputMethodPicker();

                    }
                });

            }
        });

        final ImageView Img_setting = findViewById(R.id.app_keybord_service);
        MaterialRippleLayout.on(Img_setting)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        Img_setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    startActivityForResult(new Intent(that, Keybord_Refernce_Activity.class), 0);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        startActivityForResult(new Intent(that, Keybord_Refernce_Activity.class), 0);

                    }
                });


            }
        });

        final ImageView Rel_theme = findViewById(R.id.keybord_style);
        MaterialRippleLayout.on(Rel_theme)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        Rel_theme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    SharedPreferences.Editor edit_theme = shared_ref.edit();
                    edit_theme.putString("CLICK_INTENT", "THEME");
                    edit_theme.commit();
                    startActivityForResult(new Intent(that, keybord_Set_style.class), 0);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        SharedPreferences.Editor edit_theme = shared_ref.edit();
                        edit_theme.putString("CLICK_INTENT", "THEME");
                        edit_theme.commit();
                        startActivityForResult(new Intent(that, keybord_Set_style.class), 0);

                    }
                });

            }
        });

        final ImageView Rel_imageSelect = findViewById(R.id.select_my_photo);
        MaterialRippleLayout.on(Rel_imageSelect)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        Rel_imageSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    SharedPreferences.Editor edit_theme = shared_ref.edit();
                    edit_theme.putString("CLICK_INTENT", "IMAGE_SELECT");
                    edit_theme.commit();
                    startActivityForResult(new Intent(that, Choise_Photo_My_Keybord.class), 0);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        SharedPreferences.Editor edit_theme = shared_ref.edit();
                        edit_theme.putString("CLICK_INTENT", "IMAGE_SELECT");
                        edit_theme.commit();
                        startActivityForResult(new Intent(that, Choise_Photo_My_Keybord.class), 0);

                    }
                });
            }
        });

        final ImageView bhasha = findViewById(R.id.select_bhasha);
        MaterialRippleLayout.on(bhasha)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        bhasha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    startActivityForResult(new Intent(that, My_Keyboard_Set_Bhasha.class), 0);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        startActivityForResult(new Intent(that, My_Keyboard_Set_Bhasha.class), 0);

                    }
                });

            }
        });

/*        final ImageView img = findViewById(R.id.click_photo);
        MaterialRippleLayout.on(img)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    SharedPreferences.Editor edit_theme = shared_ref.edit();
                    edit_theme.putString("CLICK_INTENT", "MY_BACK");
                    edit_theme.commit();
                    startActivityForResult(new Intent(that, My_Keyboard_Set_Photo_Style.class), 0);

            }
        });*/
    }




    protected static int getScreenWidth(Context context) {
        Display screen = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point point = new Point();
            screen.getSize(point);
            return point.x;

        } else {
            return screen.getWidth();
        }
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimCache(Context img_mcont) {
        try {
            File derctory = img_mcont.getCacheDir();
            if (derctory != null && derctory.isDirectory()) {
                deleteDir(derctory);
            }
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File derctory) {
        if (derctory != null && derctory.isDirectory()) {
            String[] child = derctory.list();
            for (int i = 0; i < child.length; i++) {
                boolean success = deleteDir(new File(derctory, child[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return derctory.delete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_rate:
                Intent rate = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()));
                rate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                rate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(rate);
                return true;

            case R.id.action_share:
                Intent uplode = new Intent(Intent.ACTION_SEND);
                uplode.setType("text/plain");
                uplode.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                uplode.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(uplode, "Share with Friends"));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(screen, Manifest.permission.CAMERA)) {
            Toast.makeText(screen, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(screen, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    public boolean checkPermissionForCamera() {
        int camera = ContextCompat.checkSelfPermission(screen, Manifest.permission.CAMERA);
        return camera == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobileAds.initialize(keyboard_Home_Activity.this,Constant.ADMOB_APP_ID);
        final AdRequest adRequest=new AdRequest.Builder().build();
        interstitialAd =new InterstitialAd(keyboard_Home_Activity.this);
        interstitialAd.setAdUnitId(Constant.INTERSTITIAL);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(adRequest);
            }
        });
    }
}
