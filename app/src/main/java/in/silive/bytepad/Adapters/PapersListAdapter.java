package in.silive.bytepad.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.R;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    private List<PaperModel> papersList;

    public PapersListAdapter(List<PaperModel> papersList) {
        this.papersList = papersList;
    }

    public class PaperViewHolder extends RecyclerView.ViewHolder {
        public TextView tv,iv;
       public Button paper_view,paper_download,paper_share;

        public PaperViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.stud_name);
            iv = (TextView) view.findViewById(R.id.stud_occp);
            paper_view = (Button)view.findViewById(R.id.paper_view);
            paper_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            paper_download = (Button)view.findViewById(R.id.paper_download);
            paper_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            paper_share = (Button)view.findViewById(R.id.paper_share);
            paper_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return papersList.size();
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_row, parent, false);

        return new PaperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        PaperModel p  = papersList.get(position);
        holder.tv.setText(p.getTitle());
        holder.iv.setText(p.ExamCategory);

    }
}
