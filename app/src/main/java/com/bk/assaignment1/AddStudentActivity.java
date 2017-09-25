package com.bk.assaignment1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class AddStudentActivity extends AppCompatActivity {

    private Button btnSubmit;
    private IDatabaseManager iDatabaseManager;

    private String studentName, studentId , clubBranch, currentRank, phone,gradingLocation, dateOfGrading, gender, dob, age, weight, height, mobile, fee, startingTime;

    String[] rankList = { "White Belt", "Yellow Belt Stripe 1", "Blue Belt Stripes 2" };

    boolean isUpdateInfo;

    private Spinner spnRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            studentName= extras.getString("STUDENT_NAME");
            isUpdateInfo = true;
        } else {
            studentName = "";
            isUpdateInfo = false;
        }

        iDatabaseManager = new DatabaseManagerImpl();
        iDatabaseManager.setupDatabase();

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStudentInfo();
            }
        });

        spnRank = (Spinner) findViewById(R.id.sp_rank);
        spnRank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), rankList[i] ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, rankList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnRank.setAdapter(aa);

        if(!studentName.equalsIgnoreCase("")){
            SQLiteDatabase database = iDatabaseManager.getWritableDatabase();

                if (database != null) {


                    String sqlQuery = "Select * From STUDENT";
                    Cursor cursor = database.rawQuery(sqlQuery, null);

                    if (cursor != null) {
                        int i;
                        int rowCount = cursor.getCount();
                        cursor.moveToFirst();

                        for (i = 0; i < rowCount; i++) {
                            if(studentName.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("NAME")))){

                                studentId = cursor.getString(cursor.getColumnIndex("_ID"));
                                clubBranch = cursor.getString(cursor.getColumnIndex("CLUB_BRANCH"));
                                currentRank = cursor.getString(cursor.getColumnIndex("CURRENT_RANK"));
                                phone = cursor.getString(cursor.getColumnIndex("HOME_PHONE"));
                                gradingLocation = cursor.getString(cursor.getColumnIndex("GRADING_LOCATION"));
                                dateOfGrading = cursor.getString(cursor.getColumnIndex("DATE_OF_GRADING"));
                                gender = cursor.getString(cursor.getColumnIndex("GENDER"));
                                dob = cursor.getString(cursor.getColumnIndex("DATE_OF_BIRTH"));
                                age = cursor.getString(cursor.getColumnIndex("AGE"));
                                weight = cursor.getString(cursor.getColumnIndex("CURRENT_WEIGHT"));
                                height = cursor.getString(cursor.getColumnIndex("HEIGHT"));
                                mobile = cursor.getString(cursor.getColumnIndex("MOBILE"));
                                fee = cursor.getString(cursor.getColumnIndex("GRADING_FEE"));
                                startingTime = cursor.getString(cursor.getColumnIndex("STARTING_TIME"));


                                break;

                            }

                            cursor.moveToNext();
                        }
                        cursor.close();
                    } else {
                        //message for user
                    }
                }
        }

        if(!studentName.equalsIgnoreCase("")){

            for(int i=0; i<3; i++){
                if(currentRank.equalsIgnoreCase(spnRank.getItemAtPosition(i).toString())){
                    spnRank.setSelection(i);
                    break;
                }
            }

            ((EditText) findViewById(R.id.et_name)).setText(studentName);
            ((EditText) findViewById(R.id.et_branch)).setText(clubBranch);
            //((EditText) findViewById(R.id.et_rank)).setText(currentRank);
            ((EditText) findViewById(R.id.et_phone)).setText(phone);
            ((EditText) findViewById(R.id.et_location)).setText(gradingLocation);
            ((EditText) findViewById(R.id.et_gradingdate)).setText(dateOfGrading);
            ((EditText) findViewById(R.id.et_gender)).setText(gender);
            ((EditText) findViewById(R.id.et_dob)).setText(dob);
            ((EditText) findViewById(R.id.et_age)).setText(age);
            ((EditText) findViewById(R.id.et_weight)).setText(weight);
            ((EditText) findViewById(R.id.et_height)).setText(height);
            ((EditText) findViewById(R.id.et_mobile)).setText(mobile);
            ((EditText) findViewById(R.id.et_fee)).setText(fee);
            ((EditText) findViewById(R.id.et_startingtime)).setText(startingTime);
        }


    }

    private void saveStudentInfo(){

        if(((EditText) findViewById(R.id.et_name)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter applicant's name", Toast.LENGTH_SHORT).show();

        }
        else if(((EditText) findViewById(R.id.et_branch)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter club branch", Toast.LENGTH_SHORT).show();
        }
//        else if(((EditText) findViewById(R.id.et_rank)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter current rank", Toast.LENGTH_SHORT).show();
//        }
        else if(((EditText) findViewById(R.id.et_phone)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter home phone", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_location)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter grading location", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_gradingdate)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter date of grading", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_gender)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter gender", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_dob)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_age)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter age", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_weight)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter current weight", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_height)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter height", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_mobile)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter mobile", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_fee)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter grading fee", Toast.LENGTH_SHORT).show();
        }
        else if(((EditText) findViewById(R.id.et_startingtime)).getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter staring time", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.e("TAG" ,"Start");

            SQLiteDatabase database = iDatabaseManager.getWritableDatabase();

            try {
            if (database != null) {

                String time= Long.toString(System.currentTimeMillis());

                String str = "test";

                if(isUpdateInfo) {

                    Log.e("TAG", studentId);

                    ContentValues cv = new ContentValues();
                    cv.put("NAME", ((EditText) findViewById(R.id.et_name)).getText().toString());
                    cv.put("CLUB_BRANCH", ((EditText) findViewById(R.id.et_branch)).getText().toString());
                    cv.put("CURRENT_RANK", spnRank.getSelectedItem().toString());
                    cv.put("HOME_PHONE", ((EditText) findViewById(R.id.et_phone)).getText().toString() );
                    cv.put("GRADING_LOCATION", ((EditText) findViewById(R.id.et_location)).getText().toString() );
                    cv.put("DATE_OF_GRADING", ((EditText) findViewById(R.id.et_gradingdate)).getText().toString() );
                    cv.put("GENDER", ((EditText) findViewById(R.id.et_gender)).getText().toString() );
                    cv.put("DATE_OF_BIRTH", ((EditText) findViewById(R.id.et_dob)).getText().toString() );
                    cv.put("AGE", ((EditText) findViewById(R.id.et_age)).getText().toString() );
                    cv.put("CURRENT_WEIGHT", ((EditText) findViewById(R.id.et_weight)).getText().toString() );
                    cv.put("HEIGHT", ((EditText) findViewById(R.id.et_height)).getText().toString() );
                    cv.put("MOBILE", ((EditText) findViewById(R.id.et_mobile)).getText().toString() );
                    cv.put("GRADING_FEE", ((EditText) findViewById(R.id.et_fee)).getText().toString() );
                    cv.put("STARTING_TIME", ((EditText) findViewById(R.id.et_startingtime)).getText().toString() );

                    database.update("STUDENT", cv, "_ID=" + studentId, null);



                }

                else {
                    database.execSQL("INSERT INTO STUDENT VALUES ('" + time + "', " +
                            "'" + ((EditText) findViewById(R.id.et_name)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_branch)).getText().toString() + "', " +
                            "'" + spnRank.getSelectedItem().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_phone)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_location)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_gradingdate)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_gender)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_dob)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_age)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_weight)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_height)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_mobile)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_fee)).getText().toString() + "', " +
                            "'" + ((EditText) findViewById(R.id.et_startingtime)).getText().toString() + "')");
                }


                Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
                finish();


            } else {

            }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();

                //message for user
            } finally {
                //Always free resources
                try {
                    if (database != null) {
                        iDatabaseManager.closeDatabase();

                    } else {
                        //message for user

                    }
                } catch (SQLiteAbortException ex) {
                    ex.printStackTrace();
                    //message for user

                }
            }
        }
    }
}
