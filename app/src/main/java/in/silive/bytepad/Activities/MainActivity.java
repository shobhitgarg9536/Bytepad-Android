package in.silive.bytepad.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.Bytepad;
import in.silive.bytepad.Network.RoboRetroSpiceRequest;
import in.silive.bytepad.R;
import in.silive.bytepad.Services.FollowerList;
import in.silive.bytepad.Services.FollowersRequest;

public class MainActivity extends AppCompatActivity {
    String lastRequestCacheKey;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        DialogFileDir dialogFileDir = new DialogFileDir();
        dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
        Log.d("Bytepad", "File chooser Dialog created");
       // spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
        roboRetroSpiceRequest = new RoboRetroSpiceRequest("octo-online", "robospice");
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSpiceManager().execute(githubRequest, "github", DurationInMillis.ONE_MINUTE, new ListContributorRequestListener());
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
    private void updateContributors(final PaperModel.List result) {
        String originalText = getString(R.string.textview_text);

        StringBuilder builder = new StringBuilder();
        builder.append(originalText);
        builder.append('\n');
        builder.append('\n');
        for (Bytepad contributor : result) {
            builder.append('\t');
            builder.append(contributor.login);
            builder.append('\t');
            builder.append('(');
            builder.append();
            builder.append(')');
            builder.append('\n');
        }
        mTextView.setText(builder.toString());
    }
    public final class ListContributorRequestListener implements RequestListener<Lab.List> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(RoboRetroSpiceRequest.this, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final List.List result) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            updateContributors(result);
        }
    }
}


