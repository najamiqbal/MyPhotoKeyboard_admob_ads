package keyboard.cheetahphotokeyboard.neon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import keyboard.cheetahphotokeyboard.neon.R;
import keyboard.cheetahphotokeyboard.neon.theme.Text_Style_Sumry_refrence;

public class Style_My_Keybord_array_adep extends ArrayAdapter<CharSequence> implements OnClickListener {

	int stylishkeybord;
	CharSequence[] seqencekeybord, charkeyword;
	Context keywordcontext;
	TextView keybordtextview;
	Text_Style_Sumry_refrence ototext;
	int[] textstyle;

	public Style_My_Keybord_array_adep(Context imgcont, int nameid, CharSequence[] seqence, CharSequence[] charcter, int choise, Text_Style_Sumry_refrence textauto, int[] inttext) {
		super(imgcont, nameid, seqence);
		keywordcontext = imgcont;
		stylishkeybord = choise;
		seqencekeybord = seqence;
		charkeyword = charcter;
		textstyle = inttext;
		this.ototext = textauto;
	}

	@Override
	public View getView(int intpos, View keybordgetvie, ViewGroup group) {

		CharSequence style = this.getItem(intpos);
		LayoutInflater layoutin = ((Activity)getContext()).getLayoutInflater();
		View keybordroow = layoutin.inflate(R.layout.text_sample_screen, group, false);
		keybordroow.setId(Integer.parseInt(style.toString()));
		keybordroow.setOnClickListener(this);
		keybordtextview = (TextView) keybordroow.findViewById(R.id.txt_themes);
		ImageView ivthem = (ImageView)keybordroow.findViewById(R.id.img_show_theme);
		keybordtextview.setText(charkeyword[intpos].toString());
		ivthem.setBackgroundResource(textstyle[intpos]);

		return keybordroow;
	}

	@Override
	public void onClick(View v)
	{
		ototext.SetResult(keybordtextview.getText().toString(),v.getId());
	}
}