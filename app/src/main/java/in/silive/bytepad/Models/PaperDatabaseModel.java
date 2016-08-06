package in.silive.bytepad.Models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import in.silive.bytepad.PaperDatabase;

/**
 * Created by akriti on 5/8/16.
 */
@Table(database = PaperDatabase.class)
@Parcel(analyze={PaperDatabaseModel.class})
public class PaperDatabaseModel extends BaseModel {
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
