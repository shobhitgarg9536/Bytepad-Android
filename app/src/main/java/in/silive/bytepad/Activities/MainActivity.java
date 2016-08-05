package in.silive.bytepad.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.R;

public class MainActivity extends AppCompatActivity  {
    public static String lastRequestCacheKey;
    public static AutoCompleteTextView search_paper;
    public static Context c;
    ArrayAdapter adapter_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        c = getApplicationContext();
        search_paper = (AutoCompleteTextView) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        //adapter_search = new ArrayAdapter(this,android.R.layout.simple_list_item_1,R.s);
        DialogFileDir dialogFileDir = new DialogFileDir();
        dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
        dialogFileDir.setListener(new DialogFileDir.Listener() {
            @Override
            public void onDirSelected(String addr) {
                Log.d("Bytepad", "Directory added " + addr);
            }
        });
        Log.d("Bytepad", "File chooser Dialog created");

    }









}