package in.silive.bytepad.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import in.silive.bytepad.Models.PaperModel;
import in.silive.bytepad.R;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    public List<PaperModel> papersList;

    public PapersListAdapter(List<PaperModel> papersList) {
        this.papersList = papersList;
    }

    @Override
    public int getItemCount() {
        return papersList.size();
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_row_paper, parent, false);

        return new PaperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        PaperModel p = papersList.get(position);
        holder.paper_name.setText(p.getTitle());
        holder.paper_category.setText(p.ExamCategory);

    }

    public class PaperViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout paper_options;
        public TextView paper_name, paper_category;
        public Button paper_view, paper_download, paper_share;

        public PaperViewHolder(View view) {
            super(view);
            paper_options = (LinearLayout) view.findViewById(R.id.paper_options);
            paper_name = (TextView) view.findViewById(R.id.paper_name);
            paper_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paper_options.setVisibility(View.VISIBLE);
                }
            });
            paper_category = (TextView) view.findViewById(R.id.paper_category);
            paper_view = (Button) view.findViewById(R.id.paper_view);
            paper_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            paper_download = (Button) view.findViewById(R.id.paper_download);
            paper_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                PaperModel paper_for_download = papersList.get(getAdapterPosition());
                    String paper_url = paper_for_download.getURL();
                    try {
                        URL url = new URL(paper_url);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                       /* connection.setRequestMethod("GET");
                        connection.setDoOutput(true);*/
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        /*FileOutputStream file = new FileOutputStream();
                        int size_of_file = connection.getContentLength();
                        byte[] buffer = new byte[1024000];
                        int bufferLength = 0;
                        while((bufferLength = inputStream.read(buffer))>0 ){
                            file.write(buffer, 0, bufferLength);
                        }
                        file.close();*/

                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            paper_share = (Button) view.findViewById(R.id.paper_share);
            paper_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
