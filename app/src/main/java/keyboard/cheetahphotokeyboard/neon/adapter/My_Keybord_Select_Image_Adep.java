package keyboard.cheetahphotokeyboard.neon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import keyboard.cheetahphotokeyboard.neon.R;


public class My_Keybord_Select_Image_Adep extends BaseAdapter {

    private Activity layout;
    private String[] derctorypath;
    Holder select_img = null;
    private static LayoutInflater layoutin = null;

    public My_Keybord_Select_Image_Adep(Activity screen, String[] derictry, String[] fname) {
        layout = screen;
        derctorypath = derictry;
        layoutin = (LayoutInflater) layout.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return derctorypath.length;
    }

    public Object getItem(int getitem) {
        return getitem;
    }

    public long getItemId(int itemid) {
        return itemid;
    }

    public View getView(int getview, View imgview, ViewGroup viewgroup) {
        View imageview = imgview;
        if (imgview == null) {

            imageview = layoutin.inflate(R.layout.img_listview_style, null);
            select_img = new Holder();
            imageview.setTag(select_img);
            select_img.holderimg = (ImageView) imageview.findViewById(R.id.image);
        } else {
            select_img = (Holder) imageview.getTag();
        }
        try {
            Uri link = Uri.fromFile(new File(derctorypath[getview]));
            Bitmap imgbitmap = readBitmap(link);
            select_img.holderimg.setImageBitmap(imgbitmap);
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
        }
        return imageview;
    }

    public static class Holder {
        ImageView holderimg;
    }

    public Bitmap readBitmap(Uri selectedImage) {
        Bitmap imgbitmap = null;
        BitmapFactory.Options factory = new BitmapFactory.Options();
        factory.inSampleSize = 1;
        AssetFileDescriptor assetfolder = null;
        try {
            assetfolder = layout.getContentResolver().openAssetFileDescriptor(selectedImage, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                imgbitmap = BitmapFactory.decodeFileDescriptor(assetfolder.getFileDescriptor(), null, factory);
                assetfolder.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgbitmap;
    }
}