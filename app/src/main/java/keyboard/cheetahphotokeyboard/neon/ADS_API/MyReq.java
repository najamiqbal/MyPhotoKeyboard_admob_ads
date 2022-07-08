package keyboard.cheetahphotokeyboard.neon.ADS_API;

import android.util.Log;

import com.google.android.gms.ads.AdRequest;

public class MyReq {
    public AdRequest adRequest;

    public AdRequest myreq() {
       if(adRequest==null){
           Log.e("mybad","load");
           adRequest = new AdRequest.Builder().build();
       }
       return adRequest;
    }

    public static MyReq myInst;
    public static MyReq getI() {
        if(myInst==null){
            myInst = new MyReq();
        }
        return myInst;
    }


}
