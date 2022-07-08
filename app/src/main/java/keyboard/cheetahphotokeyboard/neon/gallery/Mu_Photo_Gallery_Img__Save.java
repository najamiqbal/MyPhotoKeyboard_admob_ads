package keyboard.cheetahphotokeyboard.neon.gallery;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.gallery.img_view.Click_Photo_Setview;
import keyboard.cheetahphotokeyboard.neon.gallery.until.Set_Save_Photo;
import keyboard.cheetahphotokeyboard.neon.Typing_Switch;

public class Mu_Photo_Gallery_Img__Save extends AppCompatActivity {

    private Click_Photo_Setview photo;
    RelativeLayout setimg, addimg, degreeimg;
    int displayWidth;
    private InterstitialAd interstitial;
    private Toolbar apptol;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_photo_save_screen);

        Window fream = getWindow();
        fream.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        apptol = (Toolbar) findViewById(R.id.animation_tolbar);
        setSupportActionBar(apptol);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView name = (TextView) findViewById(R.id.my_photo_keybord);

        Typeface style = Typeface.createFromAsset(getAssets(), "raleway_regular.ttf");
        name.setTypeface(style);
        photo = (Click_Photo_Setview) findViewById(R.id.img);
        setimg = (RelativeLayout) findViewById(R.id.relCapImg);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.img_defa);
        addimg = (RelativeLayout) findViewById(R.id.Rel_Save);
        degreeimg = (RelativeLayout) findViewById(R.id.Rel_Rotate);
        Intent relative = getIntent();
        int intValue = relative.getIntExtra("ShowCreation", 0);
        photo.setImageBitmap(Set_Save_Photo.photobit);
        photo.setOnTouchImageViewListener(new Click_Photo_Setview.OnTouchImageViewListener() {
            @Override
            public void onMove() {
            }
        });
        MaterialRippleLayout.on(addimg)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        addimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                SimpleDateFormat dataformet = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String text = dataformet.format(new Date());

                Bitmap servies = loadBitmapFromView1(setimg);
                saveImg(servies, text);

                setimg.setDrawingCacheEnabled(true);
                Bitmap appimg = setimg.getDrawingCache();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                appimg.compress(Bitmap.CompressFormat.JPEG, 100, output);
                File fileder = new File("/sdcard/KeyBoard_Custom/");
                fileder.mkdirs();
                File derctory = new File(fileder, "bg_keyboard.jpg");
                try {
                    derctory.createNewFile();
                    FileOutputStream fileoutput = new FileOutputStream(derctory);
                    fileoutput.write(output.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    SharedPreferences refernce = PreferenceManager.getDefaultSharedPreferences(Mu_Photo_Gallery_Img__Save.this);

                    int myIntValue = refernce.getInt("SET_THEMES", 0);
                    Typing_Switch.changeLatinKeyboardView(Integer.valueOf(refernce.getString("pref_keyboard_layout",                             myIntValue + "")), true);
                    Toast.makeText(Mu_Photo_Gallery_Img__Save.this, "Keyboard Set Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    AlertDialog.Builder dailogbox = new AlertDialog.Builder(Mu_Photo_Gallery_Img__Save.this);

                    dailogbox.setTitle("Error save photo..");

                    dailogbox
                            .setMessage("Opps error accured while saving photo !!! Please, Go to menu and Enabled Keyboard and Set Input Method.")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            Mu_Photo_Gallery_Img__Save.this
                                                    .finish();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog box = dailogbox.create();

                    box.show();
                }

            }
        });
        MaterialRippleLayout.on(degreeimg)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        degreeimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Matrix click = new Matrix();
                    click.postRotate(90);
                    Set_Save_Photo.photobit = Bitmap.createBitmap(
                            Set_Save_Photo.photobit, 0, 0,
                            Set_Save_Photo.photobit.getWidth(),
                            Set_Save_Photo.photobit.getHeight(), click, true);
                    photo.setImageBitmap(Set_Save_Photo.photobit);
                } catch (OutOfMemoryError e) {
                    // TODO: handle exception
                } catch (NullPointerException e) {
                    // TODO: handle exception
                }

            }
        });
    }

    private Bitmap loadBitmapFromView1(RelativeLayout lodephoto) {
        // TODO Auto-generated method stub
        Bitmap lodebit = null;
        if (lodephoto.getMeasuredHeight() <= 0) {
            lodephoto.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lodebit = Bitmap.createBitmap(lodephoto.getMeasuredWidth(), lodephoto.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

            Canvas imgcan = new Canvas(lodebit);
            lodephoto.layout(0, 0, lodephoto.getMeasuredWidth(), lodephoto.getMeasuredHeight());
            lodephoto.draw(imgcan);

        } else {
            lodebit = Bitmap.createBitmap(lodephoto.getWidth(), lodephoto.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas lodecan = new Canvas(lodebit);
            lodephoto.layout(lodephoto.getLeft(), lodephoto.getTop(), lodephoto.getRight(), lodephoto.getBottom());
            lodephoto.draw(lodecan);
        }
        return lodebit;
    }

    public void saveImg(Bitmap img, String text) {
        ByteArrayOutputStream arraylist = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, arraylist);
        File wallpaperDirectory = new File("/sdcard/KeyBoard_Custom/MyBack/");
        wallpaperDirectory.mkdirs();
        File derctory = new File(wallpaperDirectory, text + ".png");
        try {
            derctory.createNewFile();
            FileOutputStream fileout = new FileOutputStream(derctory);
            fileout.write(arraylist.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("deprecation")
    protected static int getScreenWidth(Context appcont) {
        Display display = ((WindowManager) appcont.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point point = new Point();

            display.getSize(point);
            return point.x;
        } else {
            return display.getWidth();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void trimCache(Context context) {
        try {
            File data = context.getCacheDir();
            if (data != null && data.isDirectory()) {
                deleteDir(data);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] appchild = dir.list();
            for (int i = 0; i < appchild.length; i++) {
                boolean success = deleteDir(new File(dir, appchild[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
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
                Intent star = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()));
                star.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                star.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(star);
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
