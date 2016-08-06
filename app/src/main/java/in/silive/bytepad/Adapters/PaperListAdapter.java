package in.silive.bytepad.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import in.silive.bytepad.R;

/**
 * Created by akriti on 5/8/16.
 */
public class PaperListAdapter extends BaseAdapter {

    public static String result[];
    public static String occ_result[];
    private static LayoutInflater inflater = null;
    Context c;

    public PaperListAdapter(String r[], String o[], Context m) {
        result = r;
        occ_result = o;
        c = m;
        inflater = (LayoutInflater) m.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rview;
        rview = inflater.inflate(R.layout.adapter_row_paper, null);
        holder.tv = (TextView) rview.findViewById(R.id.stud_name);
        holder.iv = (TextView) rview.findViewById(R.id.stud_occp);
        holder.paper_view = (Button)rview.findViewById(R.id.paper_view);
        holder.paper_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.paper_download = (Button)rview.findViewById(R.id.paper_download);
        holder.paper_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.paper_share = (Button)rview.findViewById(R.id.paper_share);
        holder.paper_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.tv.setText(result[i]);
        holder.iv.setText(occ_result[i]);
        return rview;
    }

    public class Holder {
        TextView tv;
        TextView iv;
        Button paper_view,paper_download,paper_share;
    }
}

