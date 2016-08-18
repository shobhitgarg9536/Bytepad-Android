package in.silive.bytepad.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.bytepad.R;

/**
 * Created by AKG002 on 03-08-2016.
 */
public class DirListAdapter extends BaseAdapter {


    private ArrayList<String> dirList;
    private LayoutInflater layoutInflater;

    public DirListAdapter(Context context, ArrayList<String> dirList) {
        this.dirList = dirList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dirList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(R.layout.item_directory, null);
        TextView tv = (TextView) view.findViewById(R.id.tvDirName);
        tv.setText(dirList.get(i));
        return view;
    }
}
