package app.staff.room.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.hawk.Hawk;

import java.util.List;

import app.staff.room.Activity.AddDataActivity;
import app.staff.room.Activity.UpdateActivity;
import app.staff.room.Model.StaffDetailModel;
import app.staff.room.R;

import static app.staff.room.Activity.MainActivity.updateCount;

public class StaffRecyclerviewadapter extends RecyclerView.Adapter<StaffRecyclerviewadapter.TasksViewHolder> {

    private Context mContext;
    private List<StaffDetailModel> staffList;

    public StaffRecyclerviewadapter(Context mContext, List<StaffDetailModel> staffList) {
        this.mContext = mContext;
        this.staffList = staffList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_staff, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TasksViewHolder holder, final int position) {
        StaffDetailModel staffDetailModel = staffList.get(position);
        holder.staffID = staffDetailModel.getStaffId();
        holder.txtCount.setText("" + staffDetailModel.getCount());
        holder.txtStaffName.setText(staffDetailModel.getStaffName());
        holder.txtStaffDesignation.setText(staffDetailModel.getStaffDesignation());

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(holder.txtCount.getText().toString());
                count = count + 1;
                holder.txtCount.setText("" + count);
                updateCount(count,holder.staffID);
            }
        });

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int staffID;
        Button btnPlus;
        TextView txtCount, txtStaffName, txtStaffDesignation;

        public TasksViewHolder(View itemView) {
            super(itemView);

            btnPlus = itemView.findViewById(R.id.btnPlus);
            txtCount = itemView.findViewById(R.id.txtCount);
            txtStaffName = itemView.findViewById(R.id.txtStaffName);
            txtStaffDesignation = itemView.findViewById(R.id.txtStaffDesignation);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            StaffDetailModel StaffDetailModel = staffList.get(getAdapterPosition());
            Hawk.put("StaffDetailModel",StaffDetailModel);
            Hawk.put("staffID",StaffDetailModel.getStaffId());
            Intent intent = new Intent(mContext, UpdateActivity.class);
            mContext.startActivity(intent);
        }
    }
}