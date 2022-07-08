package keyboard.cheetahphotokeyboard.neon.moden;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import keyboard.cheetahphotokeyboard.neon.R;


public class My_Keybord_Star {

	private static long DAY_TIME = 24 * 60 * 60 * 1000;
	private static String appname = "";
	private static String pkgtext = "";
	private static String firstscreen = "last_launch_pref";
	private static String noshow = "dont_show_pref";
	private static String onescreen = "first_launch_pref";



	public static void setTimePref(Context appcont, long value) {

		SharedPreferences sharedPreferences = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		sharedPreferences.edit().putLong(firstscreen, value).commit();
	}

	public static long getTimePref(Context appcont) {
		SharedPreferences refrence = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		return refrence.getLong(firstscreen, 0);
	}

	public static void setDontShowPref(Context appcont, boolean value) {

		SharedPreferences refernce = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		refernce.edit().putBoolean(noshow, value).commit();
	}

	public static boolean getDontShowPref(Context appcont) {
		SharedPreferences refernce = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		return refernce.getBoolean(noshow, false);
	}

	public static void setFirstLaunchPref(Context appcont, boolean value) {

		SharedPreferences refernce = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		refernce.edit().putBoolean(onescreen, value).commit();
	}

	public static boolean getFirstLaunchPref(Context appcont) {
		SharedPreferences refernce = appcont.getSharedPreferences(appcont.getPackageName(), Context.MODE_PRIVATE);
		return refernce.getBoolean(onescreen, true);
	}

	public static void rateNow(Context nappcont) {

		appname = nappcont.getResources().getString(R.string.english_ime_name);
		pkgtext = nappcont.getPackageName();
		if (getFirstLaunchPref(nappcont)) {
			setFirstLaunchPref(nappcont, false);
			return;
		}

		if (getDontShowPref(nappcont)) {
			return;
		}

		if (System.currentTimeMillis() >= getTimePref(nappcont) + DAY_TIME) {
			setTimePref(nappcont, System.currentTimeMillis());
			showRateDialog(nappcont);
		}

	}

	public static void showRateDialog(final Context mContext) {
		final Dialog alert = new Dialog(mContext);
		alert.setTitle("Rate " + appname);

		LinearLayout lenear = new LinearLayout(mContext);
		lenear.setOrientation(LinearLayout.VERTICAL);
		lenear.setPadding(8, 8, 8, 8);

		TextView textname = new TextView(mContext);
		textname.setText("If you enjoy using " + appname + ", please take a moment to rate it. Thanks for your support!");
		textname.setWidth(320);
		textname.setPadding(8, 8, 8, 8);
		lenear.addView(textname);

		Button alertbuton = new Button(mContext);
		alertbuton.setText("Rate Now");
		alertbuton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setDontShowPref(mContext, true);
				try {
					mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkgtext)));
				} catch (android.content.ActivityNotFoundException anfe) {
					mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkgtext)));
				}

				alert.dismiss();
			}
		});
		lenear.addView(alertbuton);

		Button alertmboutn = new Button(mContext);
		alertmboutn.setText("Remind me later");
		alertmboutn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				alert.dismiss();
			}
		});
		lenear.addView(alertmboutn);

		Button nalertmboutn = new Button(mContext);
		nalertmboutn.setText("No, thanks");
		nalertmboutn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setDontShowPref(mContext, true);
				alert.dismiss();
			}
		});
		lenear.addView(nalertmboutn);
		alert.setContentView(lenear);
		alert.show();
	}
}