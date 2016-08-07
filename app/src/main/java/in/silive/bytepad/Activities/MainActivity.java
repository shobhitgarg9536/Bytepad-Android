package in.silive.bytepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import in.silive.bytepad.Adapters.PapersListAdapter;
import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.Network.CheckConnectivity;
import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.PaperDatabaseModel_Table;
import in.silive.bytepad.R;

public class MainActivity extends AppCompatActivity  {
    public AutoCompleteTextView search_paper;
    public Context c;
    public RecyclerView rview;
    public TabLayout tabview;
    public TabItem all,st,put,ut,saved;
    public TabHost tabHost;
    public static List<PaperDatabaseModel> paperList = new ArrayList<>();
    PapersListAdapter adapter;
    String query = "%";
    String paperType = "%";
    Toolbar toolbar;
    ImageView ivClearSearch;
    public CoordinatorLayout coordinatorLayout;
    RelativeLayout recyclerEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        Log.d("Bytepad", "MainActivity created");
        search_paper = (AutoCompleteTextView) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        tabview = (TabLayout)findViewById(R.id.tabview);
        Log.d("Bytepad", "Tab Layout added");
        rview = (RecyclerView)findViewById(R.id.rview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(mLayoutManager);
        query="";
        ivClearSearch = (ImageView)findViewById(R.id.ivClearSearch);
        recyclerEmptyView = (RelativeLayout)findViewById(R.id.recyclerEmptyView);
        setUpList(query);


      /*  all = (TabItem)findViewById(R.id.all);
        st = (TabItem)findViewById(R.id.st);
        put = (TabItem)findViewById(R.id.put);
        ut = (TabItem)findViewById(R.id.ut);
        saved = (TabItem)findViewById(R.id.saved)*/;
        tabview.addTab(tabview.newTab().setText("ALL"),0);
        tabview.addTab(tabview.newTab().setText("ST"),1);
        tabview.addTab(tabview.newTab().setText("PUT"),2);
        tabview.addTab(tabview.newTab().setText("UT"),3);
        tabview.addTab(tabview.newTab().setText("SAVED"),4);
        tabview.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i){
                    case 0:paperType = "%";
                        break;
                    case 1:paperType = "ST%";
                        break;
                    case 2:paperType = "PUT%";
                        break;
                    case 3:paperType = "UT%";
                        break;
                    case  4: paperType = "download";
                        break;

                }
                setUpList(query);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (!CheckConnectivity.isNetConnected(this)){
            TabLayout.Tab tab = tabview.getTabAt(4);
            tab.select();
        }



        search_paper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
            query = editable.toString();
                setUpList(query);
            }
        });

        ivClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_paper.setText("");
            }
        });
    }
    public void setUpList(String query){
        SQLCondition secondCondition;
        if (paperType.equalsIgnoreCase("download"))
            secondCondition= PaperDatabaseModel_Table.downloaded.is(true);
        else
            secondCondition = PaperDatabaseModel_Table.ExamCategory.like(paperType);
        paperList = new Select().from(PaperDatabaseModel.class)
                .where(PaperDatabaseModel_Table.Title.like("%"+query+"%"),secondCondition)
                .queryList();
        adapter = new PapersListAdapter(this, paperList);
        rview.setAdapter(adapter);
        if (paperList.size()!=0) {
            recyclerEmptyView.setVisibility(View.GONE);
        }else{
            recyclerEmptyView.setVisibility(View.VISIBLE);
        }


    }
}