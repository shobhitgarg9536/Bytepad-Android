package in.silive.bytepad.Activities;

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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.File;
import java.util.List;

import in.silive.bytepad.Application.BytepadApplication;
import in.silive.bytepad.DownloadQueue;
import in.silive.bytepad.DownloadQueue_Table;
import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.CheckConnectivity;
import in.silive.bytepad.Network.RoboRetroSpiceRequest;
import in.silive.bytepad.Network.RoboRetrofitService;
import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.PaperDatabaseModel_Table;
import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;
import in.silive.bytepad.Services.RegisterGCM;
import in.silive.bytepad.Util;

public class Splash extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    PrefManager prefManager;
    ProgressBar progressBar;
    TextView tvProgressInfo;
    Tracker mTracker;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bundle = new Bundle();
        prefManager = new PrefManager(this);
        BytepadApplication application = (BytepadApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Splash");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        if (!prefManager.isGCMTokenSentToServer()) {
            Intent i = new Intent(this, RegisterGCM.class);
            startService(i);
        }

        splash = (RelativeLayout) findViewById(R.id.splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvProgressInfo = (TextView) findViewById(R.id.tvProgressInfo);
        Log.d("Bytepad", "Splash created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        Log.d("Bytepad", "Spice manager initialized");
        roboRetroSpiceRequest = new RoboRetroSpiceRequest();
        Log.d("Bytepad", "Spice request initialized");
        checkPapersList();
    }


    public void checkPapersList() {
        if (!prefManager.isPapersLoaded()) {
            downloadPaperList();
        } else {
            tvProgressInfo.setText("Papers list loaded.");
            checkDownloadDir();
        }
    }

    public void downloadPaperList() {
        tvProgressInfo.setText("Checking Internet connection.");
        if (!CheckConnectivity.isNetConnected(this)) {
            Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadPaperList();
                        }
                    }).show();

        } else {
            tvProgressInfo.setText("Downloading Papers list.");
            spiceManager.execute(roboRetroSpiceRequest, "bytepad", DurationInMillis.ONE_MINUTE, this);
        }
    }

    public void checkDownloadDir() {
        if (TextUtils.isEmpty(prefManager.getDownloadPath())) {
            DialogFileDir dialogFileDir = new DialogFileDir();
            dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
            dialogFileDir.setListener(new DialogFileDir.Listener() {
                @Override
                public void onDirSelected(String addr) {
                    checkDownloadDir();
                    Log.d("Bytepad", "Directory added " + addr);
                }
            });
        } else checkDownloadList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Bytepad", "On start called");
        spiceManager.start(this);
    }


    private void moveToNextActivity() {
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
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Failed")
                .build());
    }

    @Override
    public void onRequestSuccess(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Request success");
        updatePapers(result);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Success")
                .build());

    }

    public void updatePapers(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Updating papers in DB");
        tvProgressInfo.setText("Saving Papers list.");
        new AsyncTask<Void, Void, Void>() {
            PrefManager pref = prefManager;

            @Override
            protected Void doInBackground(Void... voids) {
                new Delete().from(PaperDatabaseModel.class).query();
                for (int i = 0; i < result.size(); i++) {
                    PaperModel paper = result.get(i);
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
                pref.setPapersLoaded(true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute();
        checkDownloadDir();
    }


    public void checkUpdate() {
        MAHUpdaterController.init(this, "http://highsoft.az/mah-android-updater-sample.php");
        MAHUpdaterController.callUpdate();
    }

    public void checkDownloadList() {
        List<DownloadQueue> list = new Select().from(DownloadQueue.class).queryList();
        for (DownloadQueue item : list) {
            if (Util.isDownloadComplete(this, item.reference)) {
                PaperDatabaseModel paper = new Select().from(PaperDatabaseModel.class)
                        .where(PaperDatabaseModel_Table.id.eq(item.paperId)).querySingle();
                paper.downloaded = true;
                paper.dwnldPath = item.dwnldPath;
                paper.update();
                new Delete().from(DownloadQueue.class).where(DownloadQueue_Table.reference.eq(item.reference)).query();
            }
        }

        List<PaperDatabaseModel> downloadedPapers = new Select().from(PaperDatabaseModel.class).where(PaperDatabaseModel_Table
                .downloaded.eq(true)).queryList();
        for (PaperDatabaseModel paper : downloadedPapers) {
            File file = new File(paper.dwnldPath);
            if (!file.exists()) {
                paper.downloaded = false;
                paper.update();
            }
        }

        moveToNextActivity();
    }


}
