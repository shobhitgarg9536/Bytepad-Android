package in.silive.bytepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import in.silive.bytepad.Adapters.PapersListAdapter;
import in.silive.bytepad.Fragments.DialogFileDir;
import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.PaperDatabaseModel_Table;
import in.silive.bytepad.R;

public class MainActivity extends AppCompatActivity  {
    public AutoCompleteTextView search_paper;
    public Context c;
    public RecyclerView rview;
    public static List<PaperDatabaseModel> paperList = new ArrayList<>();
    PapersListAdapter adapter;
    String query = "%";
    String paperType = "%";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bytepad", "MainActivity created");
        search_paper = (AutoCompleteTextView) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        rview = (RecyclerView)findViewById(R.id.rview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(mLayoutManager);
        query="";
        setUpList(query);

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
    }

    public void setUpList(String query){
        SQLCondition secondCondition;
        if (paperType.equalsIgnoreCase("download"))
            secondCondition= PaperDatabaseModel_Table.downloaded.is(true);
        else
            secondCondition = PaperDatabaseModel_Table.ExamCategory.like("%"+paperType+"%");
        paperList = new Select().from(PaperDatabaseModel.class)
                .where(PaperDatabaseModel_Table.Title.like("%"+query+"%"),secondCondition)
                .queryList();
        adapter = new PapersListAdapter(this,paperList);
        rview.setAdapter(adapter);

    }
}