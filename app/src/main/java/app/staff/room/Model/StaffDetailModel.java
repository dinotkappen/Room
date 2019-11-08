package app.staff.room.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class StaffDetailModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int staffId;

    @ColumnInfo(name = "staffName")
    private String staffName;

    @ColumnInfo(name = "staffDesignation")
    private String staffDesignation;

    @ColumnInfo(name = "count")
    private int count;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffDesignation() {
        return staffDesignation;
    }

    public void setStaffDesignation(String staffDesignation) {
        this.staffDesignation = staffDesignation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
