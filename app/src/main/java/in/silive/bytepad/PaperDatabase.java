package in.silive.bytepad;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by akriti on 5/8/16.
 */
@Database(name = Config.DB_NAME, version =Config.DB_VERSION)
public class PaperDatabase  {


        public static final String NAME = Config.DB_NAME;

        public static final int VERSION =Config.DB_VERSION;

}
