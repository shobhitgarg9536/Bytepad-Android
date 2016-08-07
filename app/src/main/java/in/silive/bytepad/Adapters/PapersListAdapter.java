package in.silive.bytepad.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.R;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    private List<PaperDatabaseModel> papersList;
    Context context;

    public PapersListAdapter(Context context,List<PaperDatabaseModel> papersList) {
        this.papersList = papersList;
        this.context = context;
    }

    public class PaperViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPaperTitle,tvPaperCategory,tvPaperSize,tvDownload;
        ImageView ivIcon;
        RelativeLayout rl;

        public PaperViewHolder(View view) {
            super(view);
            tvPaperTitle = (TextView) view.findViewById(R.id.paper_title);
            tvPaperCategory = (TextView) view.findViewById(R.id.paper_category);
            tvPaperSize = (TextView)view.findViewById(R.id.paper_size);
            tvDownload = (TextView)view.findViewById(R.id.tvDownload);
            ivIcon = (ImageView)view.findViewById(R.id.ivIcon);
            rl = (RelativeLayout)view.findViewById(R.id.rl);
        }
    }

    @Override
    public int getItemCount() {
        return papersList.size();
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_paper, parent, false);

        return new PaperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        final PaperDatabaseModel paper = papersList.get(position);
        holder.tvPaperTitle.setText(paper.Title);
        holder.tvPaperCategory.setText(paper.PaperCategory);
        holder.tvPaperTitle.setText(paper.Title);
        int paperImgId;
        if (paper.Title.contains("doc"))
            paperImgId = R.drawable.doc;
        else if (paper.Title.contains("rtf"))
            paperImgId = R.drawable.rtf ;
        else
            paperImgId = R.drawable.pdf;

        holder.ivIcon.setImageResource(paperImgId);
        if (paper.downloaded && !TextUtils.isEmpty(paper.dwnldPath)) {
            holder.tvDownload.setText("View");
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo view paper
                }
            });
        }
        else {

            //todo download paper
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }
}
