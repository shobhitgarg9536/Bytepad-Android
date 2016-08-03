package in.silive.bytepad.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akriti on 2/8/16.
 */
public class PaperModel {
    public String Title;
    public String ExamCategory;
    public String PaperCategory;
    public String Size;
    public String URL;
    public String RelativeURL;
    public int mParentNumber;
    public List<Object> mChildObjectList;

    public PaperModel(String title, String size) {
        this.Title = title;
        this.Size = size;
    }

    public PaperModel() {
    }

    @SuppressWarnings("serial")
    public static class PapersList extends ArrayList<PaperModel> {
    }
}

