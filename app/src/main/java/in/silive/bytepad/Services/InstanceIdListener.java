package in.silive.bytepad.Services;


import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class InstanceIdListener extends InstanceIDListenerService {

    private static final String TAG = "InstanceIDLS";


    @Override
    public void onTokenRefresh() {
        Intent i = new Intent(this, RegisterGCM.class);
        startService(i);
    }
}
