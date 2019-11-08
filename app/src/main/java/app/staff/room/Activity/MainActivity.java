package app.staff.room.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import java.util.ArrayList;
import java.util.List;

import app.staff.room.Adapter.StaffRecyclerviewadapter;
import app.staff.room.Model.StaffDetailModel;
import app.staff.room.R;
import app.staff.room.Room.StaffDatabaseClient;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStff;
    public static MainActivity mainActivity;
    private FloatingActionButton buttonAddTask;
    boolean doubleBackToExitPressedOnce = false;
    StaffRecyclerviewadapter adapterStaffRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();

        mainActivity=MainActivity.this;

        recyclerViewStff = findViewById(R.id.recyclerViewStff);
        recyclerViewStff.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showDetails();
    }

    private void showDetails() {
        class LoadData extends AsyncTask<Void, Void, List<StaffDetailModel>> {

            @Override
            protected List<StaffDetailModel> doInBackground(Void... voids) {
                List<StaffDetailModel> staffList = StaffDatabaseClient
                        .getInstance(getApplicationContext())
                        .getStaffDatabase()
                        .staffDao()
                        .getAll();
                return staffList;
            }

            @Override
            protected void onPostExecute(List<StaffDetailModel> staffDetailModel) {
                super.onPostExecute(staffDetailModel);
                if (staffDetailModel.size() > 0) {
                    adapterStaffRecyclerView = new StaffRecyclerviewadapter(MainActivity.this, staffDetailModel);
                    recyclerViewStff.setAdapter(adapterStaffRecyclerView);
                } else {
                    initialData();
                }

            }
        }

        LoadData loadData = new LoadData();
        loadData.execute();
    }

    public void initialData() {
        class SaveTask extends AsyncTask<Void, Void, List<StaffDetailModel>> {

            @Override
            protected List<StaffDetailModel> doInBackground(Void... voids) {

                //creating a task
                StaffDetailModel staffDetailModelObj = new StaffDetailModel();
                staffDetailModelObj.setStaffName(getString(R.string.RenjithPk));
                staffDetailModelObj.setStaffDesignation(getString(R.string.CEO));
                staffDetailModelObj.setCount(0);




                //adding to database
                StaffDatabaseClient.getInstance(getApplicationContext()).getStaffDatabase()
                        .staffDao()
                        .insert(staffDetailModelObj);

                List<StaffDetailModel> staffList = StaffDatabaseClient
                        .getInstance(getApplicationContext())
                        .getStaffDatabase()
                        .staffDao()
                        .getAll();
                return staffList;
            }

            protected void onPostExecute(List<StaffDetailModel> staffDetailModel) {
                super.onPostExecute(staffDetailModel);
                Log.v("count",""+staffDetailModel.size());
                adapterStaffRecyclerView = new StaffRecyclerviewadapter(MainActivity.this, staffDetailModel);
                recyclerViewStff.setAdapter(adapterStaffRecyclerView);
            }
        }

        SaveTask sveTask = new SaveTask();
        sveTask.execute();
    }

    public static void updateCount(final int count, final int staffID)
    {
        class UpdateCount extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                StaffDatabaseClient.getInstance(mainActivity).getStaffDatabase()
                        .staffDao()
                        .updateCount(count,staffID);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                Toast.makeText(mainActivity, mainActivity.getString(R.string.Dataupdatedsuccessfully), Toast.LENGTH_SHORT).show();

            }
        }

        UpdateCount ut = new UpdateCount();
        ut.execute();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.BackExit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
