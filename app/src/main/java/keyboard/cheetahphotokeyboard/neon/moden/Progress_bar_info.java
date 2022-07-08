package keyboard.cheetahphotokeyboard.neon.moden;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.Locale;

import keyboard.cheetahphotokeyboard.neon.R;


public class Progress_bar_info extends DialogPreference {

    private TextView progresname;
    private TextView infoname;
    private TextView valuename;
    private SeekBar progress;
    private float mainmimum;
    private float maeximumax;
    private float imgvalueas;
    private float backvalueas;
    private float info;
    private boolean takainfo;
    private boolean infotext;
    private String screenvalue;

    public Progress_bar_info(Context appcont, AttributeSet appatrs) {
        super(appcont, appatrs);
        init(appcont, appatrs);
    }

    protected void init(Context appcont, AttributeSet appatrs) {
        setDialogLayoutResource(R.layout.progress_dilog_screen);
        
        TypedArray array = appcont.obtainStyledAttributes(appatrs, R.styleable.Progress_bar_info);
        mainmimum = array.getFloat(R.styleable.Progress_bar_info_minValue, 0.0f);
        maeximumax = array.getFloat(R.styleable.Progress_bar_info_maxValue, 100.0f);
        info = array.getFloat(R.styleable.Progress_bar_info_step, 0.0f);
        takainfo = array.getBoolean(R.styleable.Progress_bar_info_asPercent, false);
        infotext = array.getBoolean(R.styleable.Progress_bar_info_logScale, false);
        screenvalue = array.getString(R.styleable.Progress_bar_info_displayFormat);
    }

    @Override
    protected Float onGetDefaultValue(TypedArray typing, int index) {
        return typing.getFloat(index, 0.0f);
    }

    @Override
    protected void onSetInitialValue(boolean values, Object defaultValue) {
        if (values) {
            setVal(getPersistedFloat(0.0f));
        } else {
            setVal((Float) defaultValue);
        }
        savePrevVal();
    }

    private String formatFloatDisplay(Float display) {
        if (takainfo) {
            return String.format("%d%%", (int) (display * 100));
        }
        
        if (screenvalue != null) {
            return String.format(screenvalue, display);
        } else {
            return Float.toString(display);
        }
    }
    
    private void showVal() {
        valuename.setText(formatFloatDisplay(imgvalueas));
    }
    
    protected void setVal(Float set) {
        imgvalueas = set;
    }
    
    protected void savePrevVal() {
        backvalueas = imgvalueas;
    }

    protected void restoreVal() {
        imgvalueas = backvalueas;
    }

    protected String getValString() {
        return Float.toString(imgvalueas);
    }
    
    private float percentToSteppedVal(int percent, float mainimum, float maeximum, float step, boolean logScale) {
        float steppend;
        if (logScale) {
            steppend = (float) Math.exp(percentToSteppedVal(percent, (float) Math.log(mainimum), (float) Math.log(maeximum), step, false));
        } else {
            float remove = percent * (maeximum - mainimum) / 100;
            if (step != 0.0f) {
                remove = Math.round(remove / step) * step;
            }
            steppend = mainimum + remove;
        }
        steppend = Float.valueOf(String.format(Locale.US, "%.2g", steppend));
        return steppend;
    }

    private int getPercent(float val, float mainimum, float maeximum) {
        return (int) (100 * (val - mainimum) / (maeximum - mainimum));
    }
    
    private int getProgressVal() {
        if (infotext) {
            return getPercent((float) Math.log(imgvalueas), (float) Math.log(mainmimum), (float) Math.log(maeximumax));
        } else {
            return getPercent(imgvalueas, mainmimum, maeximumax);
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        progress = (SeekBar) view.findViewById(R.id.seekBarPref);
        progresname = (TextView) view.findViewById(R.id.seekMin);
        infoname = (TextView) view.findViewById(R.id.seekMax);
        valuename = (TextView) view.findViewById(R.id.seekVal);
        
        showVal();
        progresname.setText(formatFloatDisplay(mainmimum));
        infoname.setText(formatFloatDisplay(maeximumax));
        progress.setProgress(getProgressVal());

        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar progress) {}
            public void onStartTrackingTouch(SeekBar progress) {}
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float newVal = percentToSteppedVal(progress, mainmimum, maeximumax, info, infotext);
                    if (newVal != imgvalueas) {
                        onChange(newVal);
                    }
                    setVal(newVal);
                    Progress_bar_info.this.progress.setProgress(getProgressVal());
                }
                showVal();
            }
        });
        
        super.onBindDialogView(view);
    }

    public void onChange(float val) {
    }

    @Override
    public CharSequence getSummary() {
        return formatFloatDisplay(imgvalueas);
    }
    
    @Override
    protected void onDialogClosed(boolean alrttrue) {
        if (!alrttrue) {
            restoreVal();
            return;
        }
        if (shouldPersist()) {
            persistFloat(imgvalueas);
            savePrevVal();
        }
        notifyChanged();
    }
}
