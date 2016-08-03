package in.silive.bytepad.Network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by akriti on 2/8/16.
 */
public class RoboRetrofitService extends RetrofitGsonSpiceService {
    private final static String BASE_URL = "https://silive.in/bytepad";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(BytePad.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}

