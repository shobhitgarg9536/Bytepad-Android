package in.silive.bytepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.RoboRetroSpiceRequest;
import in.silive.bytepad.Network.RoboRetrofitService;
import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;

public class Splash extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    Context context;
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        prefManager = new PrefManager(context);
        splash = (RelativeLayout) findViewById(R.id.splash);
        Log.d("Bytepad","Splash created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        Log.d("Bytepad", "Spice manager initialized");
        roboRetroSpiceRequest = new RoboRetroSpiceRequest("robospice");
        Log.d("Bytepad", "Spice request initialized");
        checkConnection();
    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            //   Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            //no_net_connection.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_LONG);

/*// Changing message text color
            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/
            snackbar.show();
        }







    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Bytepad", "On start called");
        spiceManager.start(this);
        if (!prefManager.isPapersLoaded()) {
            spiceManager.execute(roboRetroSpiceRequest, "bytepad", DurationInMillis.ONE_MINUTE, this);
        }else
            skip();

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
        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show();
        spiceException.printStackTrace();
        Log.d("Bytepad", "Request failure");
        Snackbar snackbar = Snackbar
                .make(splash, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkConnection();
                    }
                });
    }

    @Override
    public void onRequestSuccess(final PaperModel.PapersList result) {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        Log.d("Bytepad", "Request success");



        updatePapers(result);
        skip();
    }
    public void updatePapers(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Update papers called");
        //String originalText = search_paper.toString();

        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        builder.append('\n');
        builder.append('\n');
        for (PaperModel p : result) {
            builder.append('\t');
            builder.append(p.Title);
            builder.append('\t');
            builder.append('(');
            builder.append(p.ExamCategory);
            builder.append(')');
            builder.append(p.PaperCategory);
            builder.append(')');
            builder.append(p.URL);
            builder.append(')');
            builder.append(p.RelativeURL);
            builder.append(')');
            builder.append('\n');
        }
        Toast.makeText(this, builder.toString(), Toast.LENGTH_SHORT).show();
        Log.d("Bytepad", builder.toString());
    }
}
