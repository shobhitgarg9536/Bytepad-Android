package in.silive.bytepad.Services;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by akriti on 1/8/16.
 */
public class FollowerList extends ArrayList<FollowerList.Follower> {
    private static final long serialVersionUID = 8192333539004718470L;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Follower {

        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }
}
