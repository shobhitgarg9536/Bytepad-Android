package in.silive.bytepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.raizlabs.android.dbflow.sql.language.Delete;

import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.CheckConnectivity;
import in.silive.bytepad.Network.RoboRetroSpiceRequest;
import in.silive.bytepad.Network.RoboRetrofitService;
import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;

public class Splash extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    Context context;
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    PrefManager prefManager;
    public static PaperModel pm;
    Bundle paperModelBundle;
    ProgressBar progressBar;
    TextView tvProgressInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        prefManager = new PrefManager(context);
        splash = (RelativeLayout) findViewById(R.id.splash);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tvProgressInfo = (TextView)findViewById(R.id.tvProgressInfo);
        Log.d("Bytepad","Splash created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        Log.d("Bytepad", "Spice manager initialized");
        roboRetroSpiceRequest = new RoboRetroSpiceRequest();
        Log.d("Bytepad", "Spice request initialized");
        checkPapersList();
    }


    public void checkPapersList() {
            if (!prefManager.isPapersLoaded()) {
               downloadPaperList();
            }else {
                tvProgressInfo.setText("Papers list loaded.");
                checkDownloadDir();
            }
    }

    public void downloadPaperList(){
        tvProgressInfo.setText("Checking Internet connection.");
        if (!CheckConnectivity.isNetConnected(this)){
            Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadPaperList();
                        }
                    }).show();
        }else {
            tvProgressInfo.setText("Internet connection found.");
            tvProgressInfo.setText("Loading Papers list..");
            spiceManager.execute(roboRetroSpiceRequest, "bytepad", DurationInMillis.ONE_MINUTE, this);
        }
    }

    public void checkDownloadDir(){
        if (TextUtils.isEmpty(prefManager.getDownloadPath())){
            DialogFileDir dialogFileDir = new DialogFileDir();
            dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
            dialogFileDir.setListener(new DialogFileDir.Listener() {
                @Override
                public void onDirSelected(String addr) {
                    checkDownloadDir();
                    Log.d("Bytepad", "Directory added " + addr);
                }
            });
        }
        else skip();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Bytepad", "On start called");
        spiceManager.start(this);
    }


    private void skip() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
        Log.d("Bytepad", "onStop called");
    }
    @Override
    public void onRequestFailure(SpiceException spiceException) {
        spiceException.printStackTrace();
        Log.d("Bytepad", "Request failure");
        Snackbar
                .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadPaperList();
                    }
                }).show();
    }

    @Override
    public void onRequestSuccess(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Request success");
        updatePapers(result);

    }
    public void updatePapers(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Update papers called");
        //String originalText = search_paper.toString();
tvProgressInfo.setText("Storing Papers list.");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                new Delete().from(PaperDatabaseModel.class).query();
                for (PaperModel paper : result) {
                    PaperDatabaseModel paperDatabaseModel = new PaperDatabaseModel();
                    paperDatabaseModel.Title = paper.Title;
                    paperDatabaseModel.ExamCategory = paper.ExamCategory;
                    paperDatabaseModel.PaperCategory = paper.PaperCategory;
                    paperDatabaseModel.URL = paper.URL;
                    paperDatabaseModel.RelativeURL = paper.RelativeURL;
                    paperDatabaseModel.Size = paper.Size;
                    paperDatabaseModel.downloaded = false;
                    paperDatabaseModel.save();
                }
                prefManager.setPapersLoaded(true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                checkDownloadDir();
            }
        }.execute();


    }
    public void checkUpdate(){
        MAHUpdaterController.init(this,"http://highsoft.az/mah-android-updater-sample.php");
        MAHUpdaterController.callUpdate();
    }
}
