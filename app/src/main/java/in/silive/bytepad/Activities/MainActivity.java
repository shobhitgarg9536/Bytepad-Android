package in.silive.bytepad.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.R;
import in.silive.bytepad.Services.FollowerList;
import in.silive.bytepad.Services.FollowersRequest;

public class MainActivity extends AppCompatActivity {
    String lastRequestCacheKey;
    SpiceManager spiceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        DialogFileDir dialogFileDir = new DialogFileDir();
        dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
        Log.d("Bytepad", "File chooser Dialog created");
        spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void performRequest(String user) {
        MainActivity.this.setProgressBarIndeterminateVisibility(true);

        FollowersRequest request = new FollowersRequest(user);
        lastRequestCacheKey = request.createCacheKey();

        spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_MINUTE, new ListFollowersRequestListener());
    }
}

class ListFollowersRequestListener implements RequestListener<FollowerList> {

    @Override
    public void onRequestFailure(SpiceException e) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(FollowerList listFollowers) {
        //update your UI
    }
}
