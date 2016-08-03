package in.silive.bytepad.Network;

import in.silive.bytepad.Models.Paper;
import in.silive.bytepad.Models.PaperModel;
import roboguice.util.temp.Ln;

/**
 * Created by akriti on 2/8/16.
 */
public class RoboRetroSpiceRequest extends RetrofitSpiceRequest<PaperModel.List, Bytepad> {
    private String owner;


    public RoboRetroSpiceRequest(String owner) {
        super(PaperModel.List.class, Bytepad.class);
        this.owner = owner;
    }

    @Override
    public PaperModel.List loadDataFromNetwork() {
        Ln.d("Call web service ");
        return getService().contributors(owner);
    }
}


