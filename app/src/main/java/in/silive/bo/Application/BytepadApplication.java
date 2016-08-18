package in.silive.bo.Application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import in.silive.bo.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by AKG002 on 06-08-2016.
 */
public class BytepadApplication extends Application {
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
