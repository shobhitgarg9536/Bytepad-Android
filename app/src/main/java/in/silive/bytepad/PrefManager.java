package in.silive.bytepad;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akriti on 5/8/16.
 */
public class PrefManager {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    public static Context context;

    public PrefManager(Context c) {
        context = c;
        pref = context.getSharedPreferences(Config.KEY_SHAREDPREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public boolean isPapersLoaded(){
        return pref.getBoolean(Config.PAPERS_LOADED,false);
    }

    public void setPapersLoaded(boolean b){
        editor.putBoolean(Config.PAPERS_LOADED,b);
        editor.apply();
    }

    public String getDownloadPath(){
        return pref.getString(Config.KEY_DOWNLOAD_DIR,"");
    }

    public void setDownloadPath(String downloadPath) {
        editor.putString(Config.KEY_DOWNLOAD_DIR,downloadPath);
        editor.apply();
    }
    public boolean isGCMTokenSentToServer() {
        return pref.getBoolean(Config.KEY_GCM_SENT_TO_SEVER,false);
    }
    public void GCMTokenSent() {
        editor.putBoolean(Config.KEY_GCM_SENT_TO_SEVER,true);
        editor.apply();
    }
}
