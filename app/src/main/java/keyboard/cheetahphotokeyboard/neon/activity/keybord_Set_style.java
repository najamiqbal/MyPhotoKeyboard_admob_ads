package keyboard.cheetahphotokeyboard.neon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import com.balysv.materialripple.MaterialRippleLayout;


import java.io.File;

import keyboard.cheetahphotokeyboard.neon.ADS_API.InterAd;
import keyboard.cheetahphotokeyboard.neon.ADS_API.MyReq;
import keyboard.cheetahphotokeyboard.neon.ADS_API.Constant;
import keyboard.cheetahphotokeyboard.neon.LatinIME;
import keyboard.cheetahphotokeyboard.neon.R;


public class keybord_Set_style extends AppCompatActivity {

    ImageView imgKeyboard1, imgKeyboard2, imgKeyboard3, imgKeyboard4,
            imgKeyboard5, imgKeyboard6, imgKeyboard7, imgKeyboard8, imgKeyboard9, imgKeyboard10,
            imgKeyboard11, imgKeyboard12, imgKeyboard13, imgKeyboard14, imgKeyboard15, imgKeyboard16, imgKeyboard17, imgKeyboard18, imgKeyboard19, imgKeyboard20, imgKeyboard21, imgKeyboard22, imgKeyboard23, imgKeyboard24,
            imgKeyboard25, imgKeyboard26, imgKeyboard27, imgKeyboard28;

    SharedPreferences Sharedface;
    private Toolbar tollayout;
    String  Borad_Interstitial ="video";
    private UnifiedNativeAd admob_nativeAd;

    public void LoadUnifiedNativeAd(final Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder((Context) activity, Constant.NATIVE_ADS);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                if (admob_nativeAd != null) {
                    admob_nativeAd.destroy();
                }
                admob_nativeAd = unifiedNativeAd;

                FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.native_ad_layout);
                UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_unified, null);
                PopulateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
                frameLayout.removeAllViews();
                frameLayout.addView(unifiedNativeAdView);
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.e("Unified Native:", "Failed to load native ad!");
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }


    private static void PopulateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        com.google.android.gms.ads.formats.MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }



    private void Banner_Admob_Loaded() {
        com.google.android.gms.ads.AdView adView;
        com.google.android.gms.ads.AdRequest adRequest;
        final RelativeLayout rlAdview;


        rlAdview = findViewById(R.id.adViewRel);
        adRequest = MyReq.getI().myreq();

        adView = new com.google.android.gms.ads.AdView(this);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        adView.setAdUnitId( Constant.BANNER);
        adView.loadAd(adRequest);
        rlAdview.addView(adView);
        adView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                rlAdview.setVisibility(View.GONE);
                Log.e("error", i + "");

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                rlAdview.setVisibility(View.VISIBLE);
            }
        });
    }

    //private com.facebook.ads.InterstitialAd Fb_interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_keybord_style);

        Window layoutfrem = getWindow();
        layoutfrem.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        tollayout = findViewById(R.id.animation_tolbar);
        setSupportActionBar(tollayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LoadUnifiedNativeAd(this);

        if (InterAd.isInternetOn(this)) {
            Banner_Admob_Loaded();
        }




        TextView keyword = findViewById(R.id.my_photo_keybord);
        Typeface textstyle = Typeface.createFromAsset(getAssets(), "raleway_regular.ttf");
        keyword.setTypeface(textstyle);

        imgKeyboard1 = findViewById(R.id.imgScreen1);
        MaterialRippleLayout.on(imgKeyboard1)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 0);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 1 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard2 = findViewById(R.id.imgScreen2);
        MaterialRippleLayout.on(imgKeyboard2)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!InterAd.getInstance().isInternetOn(keybord_Set_style.this)) {
                    InterAd.getInstance().alert(keybord_Set_style.this);

                } else {
                    InterAd.getInstance().displayInterstitial(keybord_Set_style.this, new InterAd.MyCallback() {
                        @Override
                        public void callbackCall() {
                            Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Editor edite = Sharedface.edit();
                            edite.putInt("SET_THEMES", 1);
                            edite.apply();
                            startService(new Intent(keybord_Set_style.this, LatinIME.class));
                            Toast.makeText(keybord_Set_style.this, "Theme 2 Set Successfully...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            }
        });

        imgKeyboard3 = findViewById(R.id.imgScreen3);
        MaterialRippleLayout.on(imgKeyboard3)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edit = Sharedface.edit();
                edit.putInt("SET_THEMES", 2);
                edit.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 3 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard4 = findViewById(R.id.imgScreen4);
        MaterialRippleLayout.on(imgKeyboard4)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 3);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 4 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();


            }
        });

        imgKeyboard5 = findViewById(R.id.imgScreen5);
        MaterialRippleLayout.on(imgKeyboard5)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 4);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 5 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard6 = findViewById(R.id.imgScreen6);
        MaterialRippleLayout.on(imgKeyboard6)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Editor edite = Sharedface.edit();
                    edite.putInt("SET_THEMES", 5);
                    edite.apply();
                    startService(new Intent(keybord_Set_style.this, LatinIME.class));
                    Toast.makeText(keybord_Set_style.this, "Theme 6 Set Successfully...", Toast.LENGTH_SHORT).show();
                    finish();


            }
        });

        imgKeyboard7 = findViewById(R.id.imgScreen7);
        MaterialRippleLayout.on(imgKeyboard7)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 6);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 7 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard8 = findViewById(R.id.imgScreen8);
        MaterialRippleLayout.on(imgKeyboard8)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                    Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Editor edite = Sharedface.edit();
                    edite.putInt("SET_THEMES", 7);
                    edite.apply();
                    startService(new Intent(keybord_Set_style.this, LatinIME.class));
                    Toast.makeText(keybord_Set_style.this, "Theme 8 Set Successfully...", Toast.LENGTH_SHORT).show();
                    finish();

            }
        });

        imgKeyboard9 = findViewById(R.id.imgScreen9);
        MaterialRippleLayout.on(imgKeyboard9)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 8);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 9 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard10 = findViewById(R.id.imgScreen10);
        MaterialRippleLayout.on(imgKeyboard10)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 9);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 10 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard11 = findViewById(R.id.imgScreen11);
        MaterialRippleLayout.on(imgKeyboard11)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard11.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 10);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 11 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard12 = findViewById(R.id.imgScreen12);
        MaterialRippleLayout.on(imgKeyboard12)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard12.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!InterAd.getInstance().isInternetOn(keybord_Set_style.this)) {
                    InterAd.getInstance().alert(keybord_Set_style.this);

                } else {
                    InterAd.getInstance().displayInterstitial(keybord_Set_style.this, new InterAd.MyCallback() {
                        @Override
                        public void callbackCall() {
                            Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Editor edite = Sharedface.edit();
                            edite.putInt("SET_THEMES", 11);
                            edite.apply();
                            startService(new Intent(keybord_Set_style.this, LatinIME.class));
                            Toast.makeText(keybord_Set_style.this, "Theme 12 Set Successfully...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            }
        });

        imgKeyboard13 = findViewById(R.id.imgScreen13);
        MaterialRippleLayout.on(imgKeyboard13)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard13.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 12);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 13 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard14 = findViewById(R.id.imgScreen14);
        MaterialRippleLayout.on(imgKeyboard14)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard14.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 13);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 14 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        imgKeyboard15 = findViewById(R.id.imgScreen15);
        MaterialRippleLayout.on(imgKeyboard15)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard15.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 14);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 15 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard16 = findViewById(R.id.imgScreen16);
        MaterialRippleLayout.on(imgKeyboard16)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard16.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!InterAd.getInstance().isInternetOn(keybord_Set_style.this)) {
                    InterAd.getInstance().alert(keybord_Set_style.this);

                } else {
                    InterAd.getInstance().displayInterstitial(keybord_Set_style.this, new InterAd.MyCallback() {
                        @Override
                        public void callbackCall() {
                            Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Editor edite = Sharedface.edit();
                            edite.putInt("SET_THEMES", 15);
                            edite.apply();
                            startService(new Intent(keybord_Set_style.this, LatinIME.class));
                            Toast.makeText(keybord_Set_style.this, "Theme 16 Set Successfully...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            }
        });

        imgKeyboard17 = findViewById(R.id.imgScreen17);
        MaterialRippleLayout.on(imgKeyboard17)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard17.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 16);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 17 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard18 = findViewById(R.id.imgScreen18);
        MaterialRippleLayout.on(imgKeyboard18)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard18.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 17);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 18 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard19 = findViewById(R.id.imgScreen19);
        MaterialRippleLayout.on(imgKeyboard19)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard19.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 18);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 19 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard20 = findViewById(R.id.imgScreen20);
        MaterialRippleLayout.on(imgKeyboard20)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard20.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 19);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 20 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard21 = findViewById(R.id.imgScreen21);
        MaterialRippleLayout.on(imgKeyboard21)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard21.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 20);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 21 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard22 = findViewById(R.id.imgScreen22);
        MaterialRippleLayout.on(imgKeyboard22)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        imgKeyboard22.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 21);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 22 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard23 = findViewById(R.id.imgScreen23);
        MaterialRippleLayout.on(imgKeyboard23)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard23.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 22);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 23 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard24 = findViewById(R.id.imgScreen24);
        MaterialRippleLayout.on(imgKeyboard24)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard24.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 23);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 24 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard25 = findViewById(R.id.imgScreen25);
        MaterialRippleLayout.on(imgKeyboard25)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard25.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 24);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 25 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard26 = findViewById(R.id.imgScreen26);
        MaterialRippleLayout.on(imgKeyboard26)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard26.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 25);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 26 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard27 = findViewById(R.id.imgScreen27);
        MaterialRippleLayout.on(imgKeyboard27)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard27.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 26);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 27 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgKeyboard28 = findViewById(R.id.imgScreen28);
        MaterialRippleLayout.on(imgKeyboard28)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();

        imgKeyboard28.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Sharedface = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Editor edite = Sharedface.edit();
                edite.putInt("SET_THEMES", 27);
                edite.apply();
                startService(new Intent(keybord_Set_style.this, LatinIME.class));
                Toast.makeText(keybord_Set_style.this, "Theme 28 Set Successfully...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    protected static int getScreenWidth(Context context) {
        Display screen = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            screen.getSize(size);
            return size.x;
        } else {
            return screen.getWidth();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (admob_nativeAd != null) {
            admob_nativeAd.destroy();
        }
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File derctory = context.getCacheDir();
            if (derctory != null && derctory.isDirectory()) {
                deleteDir(derctory);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File derctory) {
        if (derctory != null && derctory.isDirectory()) {
            String[] children = derctory.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(derctory, children[i]));
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



}
