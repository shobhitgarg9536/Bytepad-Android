package in.silive.bo.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AKG002 on 03-08-2016.
 */
public class CheckConnectivity {
    public static boolean isNetConnected(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null &&
                netInfo.isConnectedOrConnecting();
    }
}
