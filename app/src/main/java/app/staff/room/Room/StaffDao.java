package app.staff.room.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.staff.room.Model.StaffDetailModel;

@Dao
public interface  StaffDao {

    @Query("SELECT * FROM StaffDetailModel")
    List<StaffDetailModel> getAll();

    @Insert
    void insert(StaffDetailModel staffDetailModel);
    @Delete
    void delete(StaffDetailModel staffDetailModel);

    @Update
    void update(StaffDetailModel staffDetailModel);

    @Query("UPDATE StaffDetailModel SET count=:count WHERE staffId = :staffId")
    void updateCount(int count, int staffId);

    @Query("SELECT * FROM StaffDetailModel WHERE staffId =:staffId")
    StaffDetailModel getLiveOrderById(int staffId);
}
