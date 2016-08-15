package in.silive.bytepad;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by akriti on 5/8/16.
 */
@Table(database = PaperDatabase.class)
public class PaperDatabaseModel extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;
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
}
