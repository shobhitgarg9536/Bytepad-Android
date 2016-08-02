package in.silive.bytepad.Network;

import org.simpleframework.xml.Path;

/**
 * Created by akriti on 2/8/16.
 */
public interface Bytepad {
    @GET("/repos/{owner}/{repo}/contributors")
    Contributor.List contributors(@Path("owner") String owner);
}
