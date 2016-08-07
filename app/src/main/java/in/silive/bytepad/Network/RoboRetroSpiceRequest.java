package in.silive.bytepad.Network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import in.silive.bytepad.Models.PaperModel;

/**
 * Created by akriti on 2/8/16.
 */
public class RoboRetroSpiceRequest extends RetrofitSpiceRequest<PaperModel.PapersList,BytePad> {



    public RoboRetroSpiceRequest() {
        super(PaperModel.PapersList.class, BytePad.class);

    }

    
    @Override
    public PaperModel.PapersList loadDataFromNetwork() throws Exception {

            return getService().papersList();
    }
}


