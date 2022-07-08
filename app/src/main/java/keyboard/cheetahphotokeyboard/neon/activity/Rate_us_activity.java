package keyboard.cheetahphotokeyboard.neon.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;


import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.yugansh.tyagi.smileyrating.SmileyRatingView;


public class Rate_us_activity extends AppCompatActivity {

    RatingBar ratingBar;
    SmileyRatingView smileyRating;
    Button rate, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smiling_rating_box);

        smileyRating = findViewById(R.id.smiley_view);
        rate = (Button) findViewById(R.id.ratin);
        close = (Button) findViewById(R.id.exite);

        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.d("Rating", String.valueOf(rating));
                smileyRating.setSmiley(rating);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishAffinity();
            }
        });
    }
}
