package in.silive.bytepad.Services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;

/**
 * Created by AKG002 on 12-08-2016.
 */
public class RegisterGCM extends IntentService {
    private static final String TAG = "RegisterGCMService";
    PrefManager prefManager;

    public RegisterGCM() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        prefManager = new PrefManager(this);
        Log.i(TAG, "GCM Registration Token: " + "started");
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);
          //  prefManager.GCMTokenSent();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
