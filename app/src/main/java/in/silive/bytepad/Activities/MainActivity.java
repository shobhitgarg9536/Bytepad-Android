package in.silive.bytepad.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TabHost;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;

import in.silive.bytepad.Adapters.PapersListAdapter;
import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.R;

public class MainActivity extends AppCompatActivity  {
    public static String lastRequestCacheKey;
    public static AutoCompleteTextView search_paper;
    public static Context c;
    public RecyclerView rview;
    public TabLayout tabview;
    public TabItem all,st,put,ut,saved;
    public TabHost tabHost;
    List<PaperModel> paperModelList;
    int ALL=0,ST=1,PUT=2,UT=3,SAVED=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        c = getApplicationContext();
        search_paper = (AutoCompleteTextView) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        tabview = (TabLayout)findViewById(R.id.tabview);
        all = (TabItem)findViewById(R.id.all);
        st = (TabItem)findViewById(R.id.st);
        put = (TabItem)findViewById(R.id.put);
        ut = (TabItem)findViewById(R.id.ut);
        saved = (TabItem)findViewById(R.id.saved);
        tabHost = new TabHost(this);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = tabHost.getCurrentTab();
                switch (i){
                    
                }
            }
        });
        Log.d("Bytepad", "Tab Layout added");
        FlowManager.init(new FlowConfig.Builder(this).build());
        Log.d("Bytepad", "DB flow instantiated");
        rview = (RecyclerView)findViewById(R.id.rview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(mLayoutManager);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setAdapter(new PapersListAdapter(paperModelList));
        rview.addOnItemTouchListener(new RecyclerTouchListener(this, rview, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
         );
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
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }









}