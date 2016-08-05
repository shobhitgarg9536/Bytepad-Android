package in.silive.bytepad.Models;

import com.raizlabs.android.dbflow.annotation.Column;

import java.util.List;

/**
 * Created by akriti on 5/8/16.
 */
public class PaperDatabaseModel {
    @Column
    public String Title;
    @Column
    public String ExamCategory;
    @Column
    public String PaperCategory;
    @Column
    public String Size;
    @Column
    public String URL;
    @Column
    public String RelativeURL;
    @Column
    public boolean downloaded;
    @Column
    public String dwnldPath;
    public int mParentNumber;
    public List<Object> mChildObjectList;
}
