package in.silive.bytepad.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.RoboRetroSpiceRequest;
import in.silive.bytepad.Network.RoboRetrofitService;
import in.silive.bytepad.R;

public class MainActivity extends AppCompatActivity {
    public static String lastRequestCacheKey;
    public static EditText search_paper;
    SpiceManager spiceManager;
    public static Context c;
    RoboRetroSpiceRequest roboRetroSpiceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        c = getApplicationContext();
        search_paper = (EditText) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        DialogFileDir dialogFileDir = new DialogFileDir();
        dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
        dialogFileDir.setListener(new DialogFileDir.Listener() {
            @Override
            public void onDirSelected(String addr) {
                Log.d("Bytepad", "Directory added "+ addr);
            }
        });
        Log.d("Bytepad", "File chooser Dialog created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        roboRetroSpiceRequest = new RoboRetroSpiceRequest("robospice");
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(getApplicationContext());
        spiceManager.execute(roboRetroSpiceRequest, "bytepad", DurationInMillis.ONE_MINUTE, new ListPaperRequestListener());
    }


    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }



    public static  class ListPaperRequestListener implements RequestListener<PaperModel.PapersList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final PaperModel.PapersList result) {
            //Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            updatePapers(result);
        }
    }
     static void updatePapers(final PaperModel.PapersList result) {
        String originalText = search_paper.toString();

        StringBuilder builder = new StringBuilder();
        builder.append(originalText);
        builder.append('\n');
        builder.append('\n');
        for (PaperModel p : result) {
            builder.append('\t');
            builder.append(p.Title);
            builder.append('\t');
            builder.append('(');
            builder.append(p.ExamCategory);
            builder.append(')');
            builder.append('\n');
        }
         Toast.makeText(c,builder.toString(),Toast.LENGTH_SHORT).show();
    }


}