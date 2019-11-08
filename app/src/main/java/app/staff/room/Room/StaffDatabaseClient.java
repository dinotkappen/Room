package app.staff.room.Room;

import android.content.Context;

import androidx.room.Room;

public class StaffDatabaseClient {
    private Context mContext;
    private static StaffDatabaseClient mInstance;

    //our app database object
    private StaffDatabase staffDatabase;

    private StaffDatabaseClient(Context mContext) {
        this.mContext = mContext;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        staffDatabase = Room.databaseBuilder(mContext, StaffDatabase.class, "StaffDetails").build();
    }

    public static synchronized StaffDatabaseClient getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new StaffDatabaseClient(mContext);
        }
        return mInstance;
    }

    public StaffDatabase getStaffDatabase() {
        return staffDatabase;
    }
}
