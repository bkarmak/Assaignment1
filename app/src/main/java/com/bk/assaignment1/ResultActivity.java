package com.bk.assaignment1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class ResultActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    private IDatabaseManager iDatabaseManager;

    private String studentName;

    String grade = "";

    LinearLayout llMain;

    String studentId = "";

    String studentRank = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        llMain = ((LinearLayout) findViewById(R.id.llMain));

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            studentName= extras.getString("STUDENT_NAME");
            //Toast.makeText(this, "sssssssssss", Toast.LENGTH_SHORT).show();

        } else {
            studentName = "";
            Toast.makeText(this, "Choose a student first to see result", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("IS_FROM_RESULT", true);
            intent.putExtra("IS_FROM_ASSESMENT", false);
            startActivity(intent);
            finish();
        }

        iDatabaseManager = new DatabaseManagerImpl();
        iDatabaseManager.setupDatabase();

        SQLiteDatabase database = iDatabaseManager.getWritableDatabase();

            if (database != null) {



                String sqlQuery = "Select * From STUDENT";
                Cursor studentCursor = database.rawQuery(sqlQuery, null);

                if (studentCursor != null) {
                    int i;
                    int rowCount = studentCursor.getCount();
                    studentCursor.moveToFirst();

                    for (i = 0; i < rowCount; i++) {
                        if(studentName.equalsIgnoreCase(studentCursor.getString(studentCursor.getColumnIndex("NAME"))))
                        {
                            studentId = studentCursor.getString(studentCursor.getColumnIndex("_ID"));

                            studentRank = studentCursor.getString(studentCursor.getColumnIndex("CURRENT_RANK"));

                            if(studentRank.equalsIgnoreCase("White Belt")){
                                llMain.setBackgroundColor(Color.WHITE);

                            } else if(studentRank.equalsIgnoreCase("Yellow Belt Stripe 1")){
                                llMain.setBackgroundColor(Color.YELLOW);

                            } else if(studentRank.equalsIgnoreCase("Blue Belt Stripes 2")){
                                llMain.setBackgroundColor(Color.BLUE);

                            }
                            break;

                        }
                        studentCursor.moveToNext();
                    }
                    studentCursor.close();

                    // Set the ArrayAdapter as the ListView's adapter.

                }


                sqlQuery = "Select * From RESULT";
                Cursor resultCursor = database.rawQuery(sqlQuery, null);


                if (resultCursor != null) {
                    int i;
                    int rowCount = resultCursor.getCount();
                    resultCursor.moveToFirst();

                    boolean assessed = false;
                    for (i = 0; i < rowCount; i++) {
                        if(studentId.equalsIgnoreCase(resultCursor.getString(resultCursor.getColumnIndex("STUDENT_ID"))))
                        {
                            grade = resultCursor.getString(resultCursor.getColumnIndex("GRADE"));

                            assessed = true;

                            break;

                        }
                        resultCursor.moveToNext();
                    }
                    resultCursor.close();

                    if(assessed) {
                        ((TextView) findViewById(R.id.tvGrade)).setText("Student name : " + studentName + " \n Course : " + studentRank + " \n Result : " + grade);
                        ((LinearLayout) findViewById(R.id.llAssessmentForm)).setVisibility(View.VISIBLE);
                    } else {
                        ((TextView) findViewById(R.id.tvGrade)).setText("Student name : " + studentName + " \n Course : " + studentRank + " \n Result : The student has not been assesed yet");
                        ((LinearLayout) findViewById(R.id.llAssessmentForm)).setVisibility(View.INVISIBLE);
                    }


                } else{

                    ((TextView) findViewById(R.id.tvGrade)).setText("Student name : " + studentName + " \n Course : " + studentRank + " \n Result : The student has not been assesed yet");
                    ((LinearLayout) findViewById(R.id.llAssessmentForm)).setVisibility(View.INVISIBLE);
                }



                sqlQuery = "Select * From ASSESMENT";
                Cursor assessmentCursor = database.rawQuery(sqlQuery, null);

                if (assessmentCursor != null) {
                    int i;
                    int rowCount = assessmentCursor.getCount();
                    assessmentCursor.moveToFirst();

                    for (i = 0; i < rowCount; i++) {
                        if(studentId.equalsIgnoreCase(assessmentCursor.getString(assessmentCursor.getColumnIndex("STUDENT_ID"))))
                        {

                                    ((EditText) findViewById(R.id.etManner)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("MANNER")));
                                    ((EditText) findViewById(R.id.etStances)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("STANCES")));
                                    ((EditText) findViewById(R.id.etShortStance)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("SHORT_STANCES")));
                                    ((EditText) findViewById(R.id.etStrikes)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("STRIKES")));
                                    ((EditText) findViewById(R.id.etStrikeTriple)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("STRIKES_TRIPLE")));
                                    ((EditText) findViewById(R.id.boxing)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("BOXING_SKILL")));
                                    ((EditText) findViewById(R.id.etBlock)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("BLOCKS")));
                                    ((EditText) findViewById(R.id.etBlockDouble)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("BLOCKS_DOUBLE")));
                                    ((EditText) findViewById(R.id.etKicks)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("KICK")));
                                    ((EditText) findViewById(R.id.etFrontKick)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("FRONT_KICK")));
                                    ((EditText) findViewById(R.id.etSlideKick)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("SLIDE_KICK")));
                                    ((EditText) findViewById(R.id.etRoundhouse)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("ROUNDHOUSE_KICK")));
                                    ((EditText) findViewById(R.id.etBasicForm)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("BASIC_FORM")));
                                    ((EditText) findViewById(R.id.etTaegukByCount)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("TAEGUK_BY_COUNT")));
                                    ((EditText) findViewById(R.id.etTaugukByExercice)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("TAEGUK__FREE")));
                                    ((EditText) findViewById(R.id.etOneStep)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("ONE_STEP_SPARRING")));
                                    ((EditText) findViewById(R.id.etSparring)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("SPARRING")));
                                    ((EditText) findViewById(R.id.etYell)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("YELL")));

                                    ((EditText) findViewById(R.id.etFeedbackPositive)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("FEEDBACK_POSITIVE")));
                                    ((EditText) findViewById(R.id.etFeedbackNegetive)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("FEEDBACK_NEGETIVE")));

                                    ((EditText) findViewById(R.id.etRecommendedby)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("RECOMMENDED_BY")));
                                    ((EditText) findViewById(R.id.etRecommendedDate)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("RECOMMENDED_DATE")));
                                    ((EditText) findViewById(R.id.etGradingExaminer)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("GRADING_EXAMINER")));
                                    ((EditText) findViewById(R.id.etGradingDate)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("GRADING_DATE")));
                                    ((EditText) findViewById(R.id.etPercentage)).setText(assessmentCursor.getString(assessmentCursor.getColumnIndex("PERCENTAGE")));

                            break;

                        }
                        assessmentCursor.moveToNext();
                    }
                    assessmentCursor.close();

                    // Set the ArrayAdapter as the ListView's adapter.

                }
            }



    }


}
