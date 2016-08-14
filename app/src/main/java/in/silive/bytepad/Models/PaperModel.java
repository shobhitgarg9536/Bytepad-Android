package in.silive.bytepad.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by akriti on 2/8/16.
 */
public class PaperModel implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        @Override
        public PaperModel createFromParcel(Parcel parcel) {
            return new PaperModel(parcel);
        }

        @Override
        public PaperModel[] newArray(int i) {
            return new PaperModel[i];
        }
    };
    public String Title;
    public String ExamCategory;
    public String PaperCategory;
    public String Size;
    public String URL;
    public String RelativeURL;

    private PaperModel(Parcel in) {
        Title = in.readString();
        ExamCategory = in.readString();
        PaperCategory = in.readString();
        Size = in.readString();
        URL = in.readString();
        RelativeURL = in.readString();
    }

    public String getExamCategory() {
        return ExamCategory;
    }

    public void setExamCategory(String examCategory) {
        ExamCategory = examCategory;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(ExamCategory);
        parcel.writeString(PaperCategory);
        parcel.writeString(Size);
        parcel.writeString(URL);
        parcel.writeString(RelativeURL);
    }

    @SuppressWarnings("serial")
    public static class PapersList extends ArrayList<PaperModel> {
    }
}

