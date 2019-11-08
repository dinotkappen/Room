package app.staff.room.Activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import app.staff.room.R
import kotlinx.android.synthetic.main.activity_add_data.*
import app.staff.room.Room.StaffDatabaseClient
import app.staff.room.Model.StaffDetailModel
import android.widget.Toast


class AddDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_data)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtDesignation = findViewById<EditText>(R.id.edtDesignation)

        btnSave.setOnClickListener {
            addData();

        }

    }

    fun addData()
    {
        val staffName: String = edtName.text.toString()
        val staffDesignation: String = edtDesignation.text.toString()
        if (staffName.isEmpty()) {
            edtName.error = getString(R.string.nameValidation)
            edtName.requestFocus()
            return
        }
        if (staffDesignation.isEmpty()) {
            edtDesignation.error = getString(R.string.designationValidation)
            edtDesignation.requestFocus()
            return
        }


        class insertDataAsyncTask() : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? {

                val insertData = StaffDetailModel()
                insertData.setStaffName(staffName)
                insertData.setStaffDesignation(staffDesignation)
                insertData.setCount(0)

                //adding to database
                StaffDatabaseClient.getInstance(applicationContext).getStaffDatabase()
                        .staffDao()
                        .insert(insertData)
                return null

            }

            override fun onPreExecute() {
                super.onPreExecute()

            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)

                Toast.makeText(applicationContext, getString(R.string.Data_successfully_added), Toast.LENGTH_LONG).show()
                val intent = Intent(this@AddDataActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

        val obj = insertDataAsyncTask()
        obj.execute()
    }



}
