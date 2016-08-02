package in.silive.bytepad.Network;

import org.simpleframework.xml.Path;

import in.silive.bytepad.Models.Paper;
import in.silive.bytepad.Models.PaperModel;

/**
 * Created by akriti on 2/8/16.
 */
public interface Bytepad {
    @GET("/repos/{owner}")
    PaperModel.List contributors(@Path("owner") String owner);
}
