package in.silive.bo.Network;

import in.silive.bo.Models.PaperModel;
import retrofit.http.GET;

/**
 * Created by akriti on 2/8/16.
 */
public interface BytePad {
    @GET("/getallpapers")
    PaperModel.PapersList papersList();
}