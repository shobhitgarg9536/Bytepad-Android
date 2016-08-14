package in.silive.bytepad.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import in.silive.bytepad.R;

public class ViewPaperActivity extends AppCompatActivity {
    Toolbar toolbar;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        File file = new File("/storage/emulated/0/D.doc");
        pdfView.fromFile(file).load();

    }
}
