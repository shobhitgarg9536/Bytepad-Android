package in.silive.bytepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import in.silive.bytepad.R;

public class Splash extends AppCompatActivity {
    Context context;
    RelativeLayout splash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        splash = (RelativeLayout) findViewById(R.id.splash);
        Log.d("Bytepad","Splash created");
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


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 4000);




    }
}
