package app.staff.room.Room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.staff.room.Model.StaffDetailModel;


@Database(entities = {StaffDetailModel.class}, version = 1)
public abstract class StaffDatabase extends RoomDatabase {
    public abstract StaffDao staffDao();
}