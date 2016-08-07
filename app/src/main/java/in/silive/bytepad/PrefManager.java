package in.silive.bytepad;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akriti on 5/8/16.
 */
public class PrefManager {
    public static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    public static Context context;

    public PrefManager(Context c) {
        this.context = c;
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
}
