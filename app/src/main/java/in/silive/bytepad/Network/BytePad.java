package in.silive.bytepad.Network;

import in.silive.bytepad.Models.PaperModel;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by akriti on 2/8/16.
 */
public interface BytePad {
    @GET("/paper/getallpapers")
    PaperModel.PapersList papersList(
            @Query("query") String data);
}