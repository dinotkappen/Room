package app.staff.room.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import app.staff.room.Model.StaffDetailModel;
import app.staff.room.R;
import app.staff.room.Room.StaffDatabaseClient;

public class UpdateActivity extends AppCompatActivity {

    int staffID;
    private EditText edtName, edtDesignation, edtCount;
    StaffDetailModel staffDetailObj=new StaffDetailModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        edtName = findViewById(R.id.edtName);
        edtDesignation = findViewById(R.id.edtDesignation);
        edtCount = findViewById(R.id.edtCount);

        staffDetailObj= Hawk.get("StaffDetailModel",staffDetailObj);
        staffID=Hawk.get("staffID",staffID);
        Log.v("staffID",""+staffID);

        loadSingleItem();

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(staffDetailObj);
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteData(staffDetailObj);
            }
        });

    }


    private void updateData(final StaffDetailModel staffDetailModel) {
        final String staffName = edtName.getText().toString().trim();
        final String staffDesignation = edtDesignation.getText().toString().trim();
        final String staffCount = edtCount.getText().toString().trim();

        if (staffName.isEmpty()) {
            edtName.setError(getString(R.string.nameValidation));
            edtName.requestFocus();
            return;
        }

        if (staffDesignation.isEmpty()) {
            edtDesignation.setError(getString(R.string.designationValidation));
            edtDesignation.requestFocus();
            return;
        }

        if (staffCount.isEmpty()) {
            edtCount.setError(getString(R.string.countValidation));
            edtCount.requestFocus();
            return;
        }

        class UpdateValues extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                staffDetailModel.setStaffName(staffName);
                staffDetailModel.setStaffDesignation(staffDesignation);
                staffDetailModel.setCount(Integer.parseInt(staffCount));

                StaffDatabaseClient.getInstance(getApplicationContext()).getStaffDatabase()
                        .staffDao()
                        .update(staffDetailModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), getString(R.string.Dataupdatedsuccessfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                finish();
            }
        }

        UpdateValues updateValues = new UpdateValues();
        updateValues.execute();
    }

    private void deleteData(final StaffDetailModel StaffDetailModel) {
        class DeleteValue extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                StaffDatabaseClient.getInstance(getApplicationContext()).getStaffDatabase()
                        .staffDao()
                        .delete(StaffDetailModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), getString(R.string.Deleted), Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                finish();
            }
        }

        DeleteValue deleteValue = new DeleteValue();
        deleteValue.execute();

    }

    private void loadSingleItem()
    {
        class loadSingle extends AsyncTask<Void, Void, StaffDetailModel> {

            @Override
            protected StaffDetailModel doInBackground(Void... voids) {


                StaffDetailModel staffDetailModel=new StaffDetailModel();
                 staffDetailModel =StaffDatabaseClient.getInstance(getApplicationContext()).getStaffDatabase()
                        .staffDao()
                        .getLiveOrderById(staffID);
                return staffDetailModel;
            }

            @Override
            protected void onPostExecute(StaffDetailModel staffDetailModel) {
                super.onPostExecute(staffDetailModel);
                edtName.setText(staffDetailModel.getStaffName());
                edtDesignation.setText(staffDetailModel.getStaffDesignation());
                edtCount.setText(""+staffDetailModel.getCount());
            }
        }

        loadSingle ut = new loadSingle();
        ut.execute();
    }



}
