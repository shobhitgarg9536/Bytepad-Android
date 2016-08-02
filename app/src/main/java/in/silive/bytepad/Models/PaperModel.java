package in.silive.bytepad.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akriti on 2/8/16.
 */
public class PaperModel implements ParentObject {
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

    public void setParentNumber(int parentNumber) {
        mParentNumber = parentNumber;
    }

    /**
     * Getter method for the list of children associated with this parent object
     *
     * @return list of all children associated with this specific parent object
     */
    @Override
    public List<Object> getChildObjectList() {
        return mChildObjectList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildObjectList = list;

    }

    @SuppressWarnings("serial")
    public static class PapersList extends ArrayList<PaperModel> {
    }
}

