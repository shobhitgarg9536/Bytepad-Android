package in.silive.bo.Services;


import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class InstanceIdListener extends InstanceIDListenerService {


    @Override
    public void onTokenRefresh() {
        Intent i = new Intent(this, RegisterGCM.class);
        startService(i);
    }
}
