package in.silive.bytepad.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import in.silive.bytepad.R;

public class ViewPaperActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

    }
}
