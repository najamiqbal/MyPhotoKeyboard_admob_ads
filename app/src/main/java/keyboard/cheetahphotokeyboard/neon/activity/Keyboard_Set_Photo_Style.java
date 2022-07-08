package keyboard.cheetahphotokeyboard.neon.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import keyboard.cheetahphotokeyboard.neon.ADS_API.InterAd;
import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.adapter.My_Keybord_Select_Image_Adep;
import keyboard.cheetahphotokeyboard.neon.gallery.until.My_Photo_Set;

import keyboard.cheetahphotokeyboard.neon.gallery.My_Photo_Gallery_Img;

public class Keyboard_Set_Photo_Style extends AppCompatActivity {

    private String[] set_path;
    private String[] set_text;
    private File[] img_list;
    GridView imggridview;
    My_Keybord_Select_Image_Adep imgadep;
    File derctory;
    TextView text_img;
    private Toolbar tollayout;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_listview_keybord);

        Window lyoutfrem = getWindow();
        lyoutfrem.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        tollayout = findViewById(R.id.animation_tolbar);
        setSupportActionBar(tollayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView keyword = findViewById(R.id.my_photo_keybord);

        Typeface textstyle = Typeface.createFromAsset(getAssets(), "raleway_regular.ttf");
        keyword.setTypeface(textstyle);
        text_img = findViewById(R.id.txtNoCre);
       /* RelativeLayout adViewContainer = findViewById(R.id.adViewContainer);
        AdView adView = new AdView(this, Constant.FBADSBANNER1, AdSize.BANNER_HEIGHT_50);
        adViewContainer.addView(adView);
        adView.loadAd();*/

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();

        } else {
            derctory = new File(Environment.getExternalStorageDirectory() + File.separator + "KeyBoard_Custom/MyBack/");
            derctory.mkdirs();
        }
        if (derctory.isDirectory()) {
            img_list = derctory.listFiles();
            set_path = new String[img_list.length];
            set_text = new String[img_list.length];

            for (int i = 0; i < img_list.length; i++) {
                set_path[i] = img_list[i].getAbsolutePath();
                set_text[i] = img_list[i].getName();
            }
        }

        imggridview = findViewById(R.id.gridview);
        try {
            if (set_path.length == 0) {
                imggridview.setVisibility(View.GONE);
                text_img.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }
        imgadep = new My_Keybord_Select_Image_Adep(this, set_path, set_text);

        try {
            imggridview.setAdapter(imgadep);
        } catch (Exception e) {
        }

        imggridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                My_Photo_Set.bitshow = BitmapFactory.decodeFile(set_path[position]);

                if (!InterAd.getInstance().isInternetOn(Keyboard_Set_Photo_Style.this)) {
                    InterAd.getInstance().alert(Keyboard_Set_Photo_Style.this);

                } else {
                    InterAd.getInstance().displayInterstitial(Keyboard_Set_Photo_Style.this, new InterAd.MyCallback() {
                        @Override
                        public void callbackCall() {
                            Intent img = new Intent(Keyboard_Set_Photo_Style.this, My_Photo_Gallery_Img.class);
                            img.putExtra("ShowCreation", 1);
                            startActivity(img);
                        }
                    });
                }

            }
        });
        imggridview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                // TODO Auto-generated method stub
                AlertDialog.Builder dilogbox = new AlertDialog.Builder(Keyboard_Set_Photo_Style.this);
                dilogbox.setTitle(set_text[arg2]);
                dilogbox.setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                File derctory = new File(set_path[arg2]);
                                boolean deleted = derctory.delete();
                                if (deleted) {
                                    Toast.makeText(Keyboard_Set_Photo_Style.this, "File \"" + set_text[arg2] + "\" deleted!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alrt = dilogbox.create();
                alrt.show();
                return false;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("deprecation")
    protected static int getScreenWidth(Context img_cont) {
        Display display = ((WindowManager) img_cont.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point point = new Point();
            display.getSize(point);
            return point.x;
        } else {
            return display.getWidth();
        }
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


    public static void trimCache(Context img_cont) {
        try {
            File dirctory = img_cont.getCacheDir();
            if (dirctory != null && dirctory.isDirectory()) {
                deleteDir(dirctory);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dirctory) {
        if (dirctory != null && dirctory.isDirectory()) {
            String[] children = dirctory.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dirctory, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dirctory.delete();
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
