package in.silive.bytepad.Adapters;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.silive.bytepad.PaperDatabaseModel;
import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    Context context;
    PrefManager prefManager;
    private List<PaperDatabaseModel> papersList;

    public PapersListAdapter(Context context, List<PaperDatabaseModel> papersList) {
        this.papersList = papersList;
        this.context = context;
        this.prefManager = new PrefManager(context);
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
            paperImgId = R.drawable.rtf;
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
        } else {

            //todo download paper

            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DownloadManager downloadManager;

                    String file_url = paper.URL;
                   file_url =  file_url.replace("Sem  ","Sem%20%20");


                    final long downloadReference;
                    BroadcastReceiver recieveDownloadComplete, notificationClicked;
                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(file_url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle(paper.Title);
                    request.setDescription("Bytepad Paper Download");
                    Uri uri1 = Uri.parse("file://" + prefManager.getDownloadPath() + "/" + paper.Title);
                    request.setDestinationUri(uri1);
                    request.setVisibleInDownloadsUi(true);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    downloadReference = downloadManager.enqueue(request);
                    IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
                    notificationClicked = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String id = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                            long[] references = intent.getLongArrayExtra(id);
                            for (long reference : references) {
                                if (reference == downloadReference) {
                                    // Todo OnNotification Clicked
                                }
                            }

                        }
                    };
                    context.registerReceiver(notificationClicked, intentFilter);
                    IntentFilter intentFilterDownload = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                    recieveDownloadComplete = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                            if (downloadReference == reference) {
                                DownloadManager.Query query = new DownloadManager.Query();
                                query.setFilterById(reference);
                                Cursor cursor = downloadManager.query(query);
                                cursor.moveToFirst();
                                int colmIndx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                                int status = cursor.getInt(colmIndx);
                                int fileNameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                                String savedFilePath = cursor.getString(fileNameIndex);
                                switch (status) {
                                    case DownloadManager.STATUS_SUCCESSFUL:
                                        Toast.makeText(context, "Download Successful", Toast.LENGTH_SHORT).show();
                                        break;
                                    case DownloadManager.STATUS_FAILED:
                                        Toast.makeText(context, "Download Unuccessful", Toast.LENGTH_SHORT).show();
                                        break;
                                    case DownloadManager.STATUS_PAUSED:
                                        Toast.makeText(context, "Download Paused", Toast.LENGTH_SHORT).show();
                                        break;
                                    case DownloadManager.STATUS_RUNNING:
                                        Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
                                        break;
                                    case DownloadManager.STATUS_PENDING:
                                        Toast.makeText(context, "Download Pending", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            }
                        }
                    };
                }
            });
        }


    }

    public class PaperViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPaperTitle, tvPaperCategory, tvPaperSize, tvDownload;
        ImageView ivIcon;
        RelativeLayout rl;

        public PaperViewHolder(View view) {
            super(view);
            tvPaperTitle = (TextView) view.findViewById(R.id.paper_title);
            tvPaperCategory = (TextView) view.findViewById(R.id.paper_category);
            tvPaperSize = (TextView) view.findViewById(R.id.paper_size);
            tvDownload = (TextView) view.findViewById(R.id.tvDownload);
            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
        }
    }
}
