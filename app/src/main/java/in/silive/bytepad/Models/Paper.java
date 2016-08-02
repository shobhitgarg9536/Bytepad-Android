package in.silive.bytepad.Models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akriti on 2/8/16.
 */
public class Paper {

    private JSONObject subjectJSON;

    public Paper(JSONObject obj) {
        this.subjectJSON = obj;
    }

    public String getTitle() throws JSONException {
        return subjectJSON.getString(Bitmap.Config.SUBJECT_TITLE);
    }

    public String getType() throws JSONException {
        return subjectJSON.getString(Bitmap.Config.SUBJECT_EXAM_CATEGORY) + " "
                + subjectJSON.getString(Bitmap.Config.SUBJECT_PAPER_CATEGORY);
    }

    public String getUrl() throws JSONException {
        return subjectJSON.getString(Bitmap.Config.SUBJECT_URL);
    }

    public String getSize() throws JSONException {
        return subjectJSON.getString(Bitmap.Config.SUBJECT_SIZE);
    }
}
