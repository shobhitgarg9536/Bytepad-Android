package in.silive.bo.Network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import in.silive.bo.Config;

/**
 * Created by akriti on 2/8/16.
 */
public class RoboRetrofitService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        addRetrofitInterface(BytePad.class);
        super.onCreate();
    }

    @Override
    protected String getServerUrl() {
        return Config.BASE_URL;
    }

}

