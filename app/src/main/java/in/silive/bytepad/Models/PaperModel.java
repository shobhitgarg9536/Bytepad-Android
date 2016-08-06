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

    public String getExamCategory() {
        return ExamCategory;
    }

    public void setExamCategory(String examCategory) {
        ExamCategory = examCategory;
    }

    public List<Object> getmChildObjectList() {
        return mChildObjectList;
    }

    public void setmChildObjectList(List<Object> mChildObjectList) {
        this.mChildObjectList = mChildObjectList;
    }

    public int getmParentNumber() {
        return mParentNumber;
    }

    public void setmParentNumber(int mParentNumber) {
        this.mParentNumber = mParentNumber;
    }

    public String getPaperCategory() {
        return PaperCategory;
    }

    public void setPaperCategory(String paperCategory) {
        PaperCategory = paperCategory;
    }

    public String getRelativeURL() {
        return RelativeURL;
    }

    public void setRelativeURL(String relativeURL) {
        RelativeURL = relativeURL;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

