package keyboard.cheetahphotokeyboard.neon.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.balysv.materialripple.MaterialRippleLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.Typing_Switch;
import keyboard.cheetahphotokeyboard.neon.gallery.until.My_Photo_Set;

public class My_Photo_Gallery_Img extends AppCompatActivity {

    private Toolbar apptol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_photo_set_bg);
        Window screen = getWindow();
        screen.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        apptol = findViewById(R.id.animation_tolbar);
        setSupportActionBar(apptol);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView photo = findViewById(R.id.img_mycreaton);
        Drawable img = new BitmapDrawable(getResources(), My_Photo_Set.bitshow);
        photo.setImageDrawable(img);

        LinearLayout before = findViewById(R.id.linsetback);

        MaterialRippleLayout.on(before)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#c9c9c9"))
                .rippleAlpha(0.7f)
                .rippleHover(true)
                .create();
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream strem = new ByteArrayOutputStream();
                My_Photo_Set.bitshow.compress(Bitmap.CompressFormat.JPEG, 100, strem);
                File deractory = new File("/sdcard/KeyBoard_Custom/");
                deractory.mkdirs();
                File data = new File(deractory, "bg_keyboard.jpg");
                try {
                    data.createNewFile();
                    FileOutputStream filestrem = new FileOutputStream(data);
                    filestrem.write(strem.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    SharedPreferences refernce = PreferenceManager.getDefaultSharedPreferences(My_Photo_Gallery_Img.this);
                    int myIntValue = refernce.getInt("SET_THEMES", 0);
                    Typing_Switch.changeLatinKeyboardView(Integer.valueOf(Objects.requireNonNull(refernce.getString("pref_keyboard_layout", myIntValue + ""))), true);
                    Toast.makeText(My_Photo_Gallery_Img.this, "Keyboard Set Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    AlertDialog.Builder dilogbox = new AlertDialog.Builder(My_Photo_Gallery_Img.this);

                    dilogbox.setTitle("Error save image..");
                    dilogbox.setMessage("Opps error accured while saving image !!! Please, Go to menu and Enabled Keyboard and Set Input Method.")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) { My_Photo_Gallery_Img.this.finish();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) { dialog.cancel();
                                        }
                                    });
                    AlertDialog box = dilogbox.create();
                    box.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu bar) {
        getMenuInflater().inflate(R.menu.menu_main, bar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem style) {
        switch (style.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_rate:
                Intent star = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()));
                star.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                star.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(star);
                return true;
            case R.id.action_share:
                Intent uplode = new Intent(Intent.ACTION_SEND);
                uplode.setType("text/plain");
                uplode.putExtra(
                        Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()
                );
                uplode.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(uplode, "Share with Friends"));
                return true;


        }
        return super.onOptionsItemSelected(style);
    }
}