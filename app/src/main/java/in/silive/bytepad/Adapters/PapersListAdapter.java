package in.silive.bytepad.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.R;
import in.silive.bytepad.Util;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    private Activity context;
    private List<PaperDatabaseModel> papersList;

    public PapersListAdapter(Activity context, List<PaperDatabaseModel> papersList) {
        this.papersList = papersList;
        this.context = context;

    }

    public List<PaperDatabaseModel> getPapersList() {
        return papersList;
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
        final PaperDatabaseModel paper = this.getPapersList().get(position);
        holder.tvPaperTitle.setText(paper.Title);
        holder.tvPaperCategory.setText(paper.PaperCategory);
        holder.tvPaperTitle.setText(paper.Title);
        holder.tvPaperSize.setText(paper.Size);
        int paperImgId;
        if (paper.Title.contains("doc") || paper.Title.contains("DOC") || paper.Title.contains("Doc"))
            paperImgId = R.drawable.doc;
        else if (paper.Title.contains("rtf") || paper.Title.contains("RTF") || paper.Title.contains("Rtf"))
            paperImgId = R.drawable.rtf;
        else
            paperImgId = R.drawable.pdf;

        holder.imageView.setImageResource(paperImgId);
        if (paper.downloaded) {
            holder.tvDownload.setText("View");
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.openDocument(context, paper.dwnldPath);
                }
            });
        } else {
            holder.tvDownload.setText("Download");
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.downloadPaper(context, paper);
                }
            });

        }

    }

    class PaperViewHolder extends RecyclerView.ViewHolder {
        TextView tvPaperTitle, tvPaperCategory, tvPaperSize, tvDownload;
        ImageView imageView;
        RelativeLayout relativeLayout;

        PaperViewHolder(View view) {
            super(view);
            tvPaperTitle = (TextView) view.findViewById(R.id.paper_title);
            tvPaperCategory = (TextView) view.findViewById(R.id.paper_category);
            tvPaperSize = (TextView) view.findViewById(R.id.paper_size);
            tvDownload = (TextView) view.findViewById(R.id.tvDownload);
            imageView = (ImageView) view.findViewById(R.id.ivIcon);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.rl);
        }
    }


}
