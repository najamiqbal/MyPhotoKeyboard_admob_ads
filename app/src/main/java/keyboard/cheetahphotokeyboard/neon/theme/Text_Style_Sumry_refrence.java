package keyboard.cheetahphotokeyboard.neon.theme;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListAdapter;


import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.adapter.Style_My_Keybord_array_adep;

public class Text_Style_Sumry_refrence extends ListPreference {

	private static final String TAG = "HK/Oto_Text_Style_Sumry_refrence";
	Context nappcont;

	CharSequence entry;
	private static final int[] KEYBORDSTYLE = new int[] {
			R.drawable.style_bg_a, R.drawable.style_bg_b,
			R.drawable.style_bg_c, R.drawable.style_bg_d,
			R.drawable.style_bg_e, R.drawable.style_bg_f,
			R.drawable.style_bg_g, R.drawable.style_bg_h,
			R.drawable.style_bg_i, R.drawable.style_bg_j,
			R.drawable.style_bg_k, R.drawable.style_bg_l,
			R.drawable.style_bg_m, R.drawable.style_bg_n,
			R.drawable.style_bg_o, R.drawable.style_bg_p,
			R.drawable.style_bg_q, R.drawable.style_bg_r,
			R.drawable.style_bg_s, R.drawable.style_bg_t,
			R.drawable.style_bg_u, R.drawable.style_bg_v,
			R.drawable.style_bg_w, R.drawable.style_bg_d,
            R.drawable.style_bg_e, R.drawable.style_bg_f,
            R.drawable.style_bg_g, R.drawable.style_bg_h,};

	public Text_Style_Sumry_refrence(Context appcont) {
		super(appcont);
	}

	public Text_Style_Sumry_refrence(Context appcont, AttributeSet appatrsess) {
		super(appcont, appatrsess);
		nappcont = appcont;
	}

	private void trySetSummary() {
		entry = null;
		try {
			entry = getEntry();
		} catch (ArrayIndexOutOfBoundsException array) {
		}
		if (entry != null) {
			String percent = "percent";
			setSummary(entry.toString().replace("%", " " + percent));
		}
	}

	private void trySetSummary(String edeite) {
		entry = null;
		try {
			entry = edeite;
		} catch (ArrayIndexOutOfBoundsException array) {
		}
		if (entry != null) {
			String percent = "percent";
			setSummary(entry.toString().replace("%", " " + percent));
		}
	}

	@Override
	public void setEntries(CharSequence[] sequnce) {
		super.setEntries(sequnce);
		trySetSummary();
	}

	@Override
	public void setEntryValues(CharSequence[] msequnce) {
		super.setEntryValues(msequnce);
		trySetSummary();
	}

	@Override
	public void setValue(String value) {
		super.setValue(value);
		trySetSummary();
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(getSharedPreferences().getString(getKey(), "1"));

		ListAdapter styleadep = (ListAdapter) new Style_My_Keybord_array_adep(nappcont,
				R.layout.text_sample_screen, this.getEntryValues(), this.getEntries(),
				index, this, KEYBORDSTYLE);

		builder.setAdapter(styleadep, this);
		super.onPrepareDialogBuilder(builder);
	}

	public void SetResult(String enty, int value) {
		this.setValue(value + "");
		trySetSummary(enty);
		this.getDialog().dismiss();
	}
}
