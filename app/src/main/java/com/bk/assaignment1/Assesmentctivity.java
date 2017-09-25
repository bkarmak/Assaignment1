package com.bk.assaignment1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class Assesmentctivity extends AppCompatActivity {

    private IDatabaseManager iDatabaseManager;

    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            studentName= extras.getString("STUDENT_NAME");

        } else {
            studentName = "";
            Toast.makeText(this, "Choose a student first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("IS_FROM_ASSESMENT", true);
            startActivity(intent);
            finish();
        }

        iDatabaseManager = new DatabaseManagerImpl();
        iDatabaseManager.setupDatabase();

        findViewById(R.id.llScreenOne).setVisibility(View.VISIBLE);
        findViewById(R.id.llScreenTwo).setVisibility(View.GONE);
        findViewById(R.id.llScreenThree).setVisibility(View.GONE);

        findViewById(R.id.btnScreenOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewById(R.id.llScreenOne).setVisibility(View.VISIBLE);
                findViewById(R.id.llScreenTwo).setVisibility(View.GONE);
                findViewById(R.id.llScreenThree).setVisibility(View.GONE);

            }
        });

        findViewById(R.id.btnScreenTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.llScreenOne).setVisibility(View.GONE);
                findViewById(R.id.llScreenTwo).setVisibility(View.VISIBLE);
                findViewById(R.id.llScreenThree).setVisibility(View.GONE);

            }
        });

        findViewById(R.id.btnScreenThree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.llScreenOne).setVisibility(View.GONE);
                findViewById(R.id.llScreenTwo).setVisibility(View.GONE);
                findViewById(R.id.llScreenThree).setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               saveGradingInfo();
                Toast.makeText(Assesmentctivity.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }


    private void saveGradingInfo(){

//        if(((EditText) findViewById(R.id.et_name)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter applicant's name", Toast.LENGTH_SHORT).show();
//
//        }
//        else if(((EditText) findViewById(R.id.et_branch)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter club branch", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_rank)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter current rank", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_phone)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter home phone", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_location)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter grading location", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_gradingdate)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter date of grading", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_gender)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter gender", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_dob)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_age)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter age", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_weight)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter current weight", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_height)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter height", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_mobile)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter mobile", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_fee)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter grading fee", Toast.LENGTH_SHORT).show();
//        }
//        else if(((EditText) findViewById(R.id.et_startingtime)).getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(this, "Please enter staring time", Toast.LENGTH_SHORT).show();
//        }
//        else{
            Log.e("TAG" ,"Start");

            SQLiteDatabase database = iDatabaseManager.getWritableDatabase();


                if (database != null) {

                    String time= Long.toString(System.currentTimeMillis());

                    String studentId = "";

                    String sqlQuery = "Select * From STUDENT";
                    Cursor cursor = database.rawQuery(sqlQuery, null);

                    if (cursor != null) {
                        int i;
                        int rowCount = cursor.getCount();
                        cursor.moveToFirst();

                        for (i = 0; i < rowCount; i++) {

                            if(studentName.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("NAME")))) {
                                studentId = cursor.getString(cursor.getColumnIndex("_ID"));
                                break;
                            }
                            cursor.moveToNext();
                        }
                        cursor.close();
                    }

                    String status = "Pass";

                    if(((EditText) findViewById(R.id.etManner)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etStances)).getText().toString().equalsIgnoreCase("Fail")  ||
                            ((EditText) findViewById(R.id.etShortStance)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etStrikes)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etStrikeTriple)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.boxing)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etBlock)).getText().toString().equalsIgnoreCase("Fail")  ||
                            ((EditText) findViewById(R.id.etBlockDouble)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etKicks)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etFrontKick)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etSlideKick)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etRoundhouse)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etBasicForm)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etTaegukByCount)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etTaugukByExercice)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etOneStep)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etSparring)).getText().toString().equalsIgnoreCase("Fail") ||
                            ((EditText) findViewById(R.id.etYell)).getText().toString().equalsIgnoreCase("Fail") ||

                            ((EditText) findViewById(R.id.etManner)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etStances)).getText().toString().equalsIgnoreCase("1")  ||
                            ((EditText) findViewById(R.id.etShortStance)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etStrikes)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etStrikeTriple)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.boxing)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etBlock)).getText().toString().equalsIgnoreCase("1")  ||
                            ((EditText) findViewById(R.id.etBlockDouble)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etKicks)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etFrontKick)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etSlideKick)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etRoundhouse)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etBasicForm)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etTaegukByCount)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etTaugukByExercice)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etOneStep)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etSparring)).getText().toString().equalsIgnoreCase("1") ||
                            ((EditText) findViewById(R.id.etYell)).getText().toString().equalsIgnoreCase("1") ||


                            ((EditText) findViewById(R.id.etManner)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etStances)).getText().toString().equalsIgnoreCase("2")  ||
                            ((EditText) findViewById(R.id.etShortStance)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etStrikes)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etStrikeTriple)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.boxing)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etBlock)).getText().toString().equalsIgnoreCase("2")  ||
                            ((EditText) findViewById(R.id.etBlockDouble)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etKicks)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etFrontKick)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etSlideKick)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etRoundhouse)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etBasicForm)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etTaegukByCount)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etTaugukByExercice)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etOneStep)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etSparring)).getText().toString().equalsIgnoreCase("2") ||
                            ((EditText) findViewById(R.id.etYell)).getText().toString().equalsIgnoreCase("2")){

                        status = "Fail";

                    }


                        database.execSQL("INSERT INTO ASSESMENT VALUES ('" + time + "', " +
                                "'" + ((EditText) findViewById(R.id.etManner)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etStances)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etShortStance)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etStrikes)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etStrikeTriple)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.boxing)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etBlock)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etBlockDouble)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etKicks)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etFrontKick)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etSlideKick)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etRoundhouse)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etBasicForm)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etTaegukByCount)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etTaugukByExercice)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etOneStep)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etSparring)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etYell)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etFeedbackPositive)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etFeedbackNegetive)).getText().toString() + "', " +

                                "'" + ((EditText) findViewById(R.id.etRecommendedby)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etRecommendedDate)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etGradingExaminer)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etGradingDate)).getText().toString() + "', " +
                                "'" + ((EditText) findViewById(R.id.etPercentage)).getText().toString() + "', " +
                                "'" + studentId + "')");

                    database.execSQL("INSERT INTO RESULT VALUES ('" + time + "', " +

                            "'" + studentId + "', " +
                            "'" + status + "')");

                        // Set the ArrayAdapter as the ListView's adapter.

                        Toast.makeText(this, "Assesment information Successfully added to database", Toast.LENGTH_SHORT).show();
                    finish();


                } else {

                }
//            } catch (IllegalArgumentException ex) {
//                ex.printStackTrace();
//
//                //message for user
//            } finally {
//                //Always free resources
//                try {
//                    if (database != null) {
//                        iDatabaseManager.closeDatabase();
//
//                    } else {
//                        //message for user
//
//                    }
//                } catch (SQLiteAbortException ex) {
//                    ex.printStackTrace();
//                    //message for user
//
//                }
//            }
//        }
    }
}
