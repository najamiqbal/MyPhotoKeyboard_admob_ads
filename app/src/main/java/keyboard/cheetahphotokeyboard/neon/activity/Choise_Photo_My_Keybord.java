package keyboard.cheetahphotokeyboard.neon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import java.io.File;

import keyboard.cheetahphotokeyboard.neon.ADS_API.InterAd;
import keyboard.cheetahphotokeyboard.neon.ADS_API.MyReq;
import keyboard.cheetahphotokeyboard.neon.ADS_API.Constant;
import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.gallery.until.Set_Save_Photo;

import keyboard.cheetahphotokeyboard.neon.gallery.Mu_Photo_Gallery_Img__Save;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Choise_Photo_My_Keybord extends AppCompatActivity {

    ImageView photo_show;
    RelativeLayout my_photo_relative;
    private static final int photopick = 1;
    private static final int photo_save = 2;
    Matrix photometrix = new Matrix();
    private Toolbar tollayout;
    //private com.facebook.ads.InterstitialAd Fb_interstitialAd;
    InterstitialAd interstitialAd;


    private void Banner_Admob_Loaded() {
        com.google.android.gms.ads.AdView adView;
        com.google.android.gms.ads.AdRequest adRequest;
        final RelativeLayout rlAdview;


        rlAdview =  findViewById(R.id.adViewRel);
        adRequest = MyReq.getI().myreq();

        adView = new com.google.android.gms.ads.AdView(this);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        adView.setAdUnitId(Constant.BANNER);
        adView.loadAd(adRequest);
        rlAdview.addView(adView);
        adView.setAdListener(new com.google.android.gms.ads.AdListener(){
            @Override
            public void onAdOpened() {
                super.onAdOpened();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                rlAdview.setVisibility(View.GONE);
                Log.e("error", i+"");

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                rlAdview.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_photo_screen);

        Window llayout_frem = getWindow();
        llayout_frem.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        tollayout = findViewById(R.id.animation_tolbar);
        setSupportActionBar(tollayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (InterAd.isInternetOn(this)) {
            Banner_Admob_Loaded();
        }
        MobileAds.initialize(Choise_Photo_My_Keybord.this,Constant.ADMOB_APP_ID);
        final AdRequest adRequest=new AdRequest.Builder().build();

        interstitialAd =new InterstitialAd(Choise_Photo_My_Keybord.this);
        interstitialAd.setAdUnitId(Constant.INTERSTITIAL);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(adRequest);
            }
        });



        TextView keyword = findViewById(R.id.my_photo_keybord);
        Typeface text = Typeface.createFromAsset(getAssets(), "raleway_regular.ttf");
        keyword.setTypeface(text);

        photo_show = findViewById(R.id.set_background_imgview);
        my_photo_relative = findViewById(R.id.select_img_reltiv);
        //my_camear_realtive = findViewById(R.id.open_camera_relativ);

        File folder = new File("/sdcard/KeyBoard_Custom/bg_keyboard.jpg");
        if (folder.exists()) {
            Bitmap keywordbitmap = BitmapFactory.decodeFile(folder.getAbsolutePath());
            Drawable imgdroeble = new BitmapDrawable(getResources(), keywordbitmap);
            photo_show.setBackgroundDrawable(imgdroeble);
        } else {
            photo_show.setBackgroundResource(R.drawable.img_defa);
        }
        MaterialRippleLayout.on(my_photo_relative)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        my_photo_relative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    Intent photo_rl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photo_rl.setType("image/*");
                    startActivityForResult(Intent.createChooser(photo_rl, "Choose Image"), photopick);

                }
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Intent photo_rl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        photo_rl.setType("image/*");
                        startActivityForResult(Intent.createChooser(photo_rl, "Choose Image"), photopick);

                    }
                });

            }
        });

/*        MaterialRippleLayout.on(my_camear_realtive)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        my_camear_realtive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                PackageManager pm = getPackageManager();
                if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    if (!InterAd.getInstance().isInternetOn(Choise_Photo_My_Keybord.this)) {
                        InterAd.getInstance().alert(Choise_Photo_My_Keybord.this);

                    } else {
                        InterAd.getInstance().displayInterstitial(Choise_Photo_My_Keybord.this, new InterAd.MyCallback() {
                            @Override
                            public void callbackCall() {
                                Intent img_cpture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
                                img_cpture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(img_cpture, photo_save);
                            }
                        });
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    protected void onActivityResult(int result, int mresult, Intent data) {
        if (mresult == Activity.RESULT_OK) {
            switch (result) {
                case photopick:
                    this.imageFromGallery(mresult, data);
                    break;
                case photo_save:
                    this.imageFromCamera(mresult, data);
                    break;
                default:
                    break;
            }
        }
    }


    private void imageFromGallery(int resultCode, Intent data) throws NullPointerException {
        try {
            Uri gallery = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor photo_cursor = getContentResolver().query(gallery, filePathColumn, null, null, null);
            photo_cursor.moveToFirst();
            int index = photo_cursor.getColumnIndex(filePathColumn[0]);
            String string_folder = photo_cursor.getString(index);
            photo_cursor.close();
            this.updateImageView(BitmapFactory.decodeFile(string_folder));
        } catch (NullPointerException e) {
            // TODO: handle exception
            AlertDialog.Builder dilogbox = new AlertDialog.Builder(Choise_Photo_My_Keybord.this);
            dilogbox.setTitle("Memory Low!!");
            dilogbox.setMessage("Please Try again.")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) { dialog.cancel();
                        }
                    });
            AlertDialog alert = dilogbox.create();
            alert.show();
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            AlertDialog.Builder dilogbox = new AlertDialog.Builder(Choise_Photo_My_Keybord.this);
            dilogbox.setTitle("Memory Low!!");
            dilogbox.setMessage("Please Try again.")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) { dialog.cancel();
                        }
                    });

            AlertDialog alrt = dilogbox.create();
            alrt.show();
        }
    }

    private void imageFromCamera(int resultCode, Intent data) {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
        if (!folder.exists()) {
            Toast.makeText(getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
            return;
        }
        Bitmap setsize = null;
        if (folder.exists()) {
            setsize = BitmapFactory.decodeFile(folder.getAbsolutePath());
            setsize = Bitmap.createBitmap(setsize, 0, 0, setsize.getWidth(), setsize.getHeight(), photometrix, true);
        }

        this.updateImageView(setsize);
    }

    private void updateImageView(Bitmap newImage) {
        Set_Save_Photo.photobit = newImage;
        Intent photo_img = new Intent(Choise_Photo_My_Keybord.this, Mu_Photo_Gallery_Img__Save.class);
        startActivity(photo_img);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options factory = new BitmapFactory.Options();
        factory.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, factory);
        final int height = factory.outHeight;
        final int width = factory.outWidth;
        factory.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 0;
        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }

        int expectedWidth = width / inSampleSize;
        if (expectedWidth < reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        factory.inSampleSize = inSampleSize;
        factory.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, factory);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        File paths = new File("/sdcard/KeyBoard_Custom/bg_keyboard.jpg");
        if (paths.exists()) {
            Bitmap factory = BitmapFactory.decodeFile(paths.getAbsolutePath());
            Drawable img_drawbl = new BitmapDrawable(getResources(), factory);
            photo_show.setBackgroundDrawable(img_drawbl);
        } else {
            photo_show.setBackgroundResource(R.drawable.img_defa);
        }
        MobileAds.initialize(Choise_Photo_My_Keybord.this,Constant.ADMOB_APP_ID);
        final AdRequest adRequest=new AdRequest.Builder().build();

        interstitialAd =new InterstitialAd(Choise_Photo_My_Keybord.this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_i) {
        switch (menu_i.getItemId()) {
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
        return super.onOptionsItemSelected(menu_i);
    }
}
