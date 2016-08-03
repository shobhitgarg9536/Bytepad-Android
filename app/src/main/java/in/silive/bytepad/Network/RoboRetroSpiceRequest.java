package in.silive.bytepad.Network;

import in.silive.bytepad.Models.Paper;
import in.silive.bytepad.Models.PaperModel;
import roboguice.util.temp.Ln;

/**
 * Created by akriti on 2/8/16.
 */
public class RoboRetroSpiceRequest extends RetrofitSpiceRequest<PaperModel.PapersList,Bytepad> {
    private String data;


    public RoboRetroSpiceRequest(String data) {
        super(PaperModel.PapersList.class, Bytepad.class);
        this.data = data;
    }

    @Override
    public PaperModel.PaperList loadDataFromNetwork() {
        Ln.d("Call web service ");
        return getService().paperlist(data);
    }
}


