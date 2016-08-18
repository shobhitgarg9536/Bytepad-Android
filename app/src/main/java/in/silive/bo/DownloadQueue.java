package in.silive.bo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AKG002 on 08-08-2016.
 */
@Table(database = PaperDatabase.class)
public class DownloadQueue extends BaseModel {
    @Column
    @PrimaryKey
    public long reference;
    @Column
    public int paperId;
    @Column
    public String dwnldPath;
}
