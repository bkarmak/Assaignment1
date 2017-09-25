package com.bk.assaignment1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
public class StudentListActivity extends Activity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    private IDatabaseManager iDatabaseManager;

    boolean isFromAssesment = false;
    boolean isFromResult = false;

    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            isFromAssesment= extras.getBoolean("IS_FROM_ASSESMENT");
            isFromResult= extras.getBoolean("IS_FROM_RESULT");

        }

        iDatabaseManager = new DatabaseManagerImpl();
        iDatabaseManager.setupDatabase();

        // Find the ListView resource.
        mainListView = findViewById( R.id.mainListView );

        // Create and populate a List of planet names.
        String[] planets = new String[] { ""};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        SQLiteDatabase database = iDatabaseManager.getWritableDatabase();

        try {
            if (database != null) {


                String sqlQuery = "Select * From STUDENT";
                Cursor cursor = database.rawQuery(sqlQuery, null);

                if (cursor != null) {
                    int i;
                    int rowCount = cursor.getCount();
                    cursor.moveToFirst();

                    for (i = 0; i < rowCount; i++) {
                        listAdapter.add(cursor.getString(cursor.getColumnIndex("NAME")));

                        studentId = cursor.getString(cursor.getColumnIndex("_ID"));

                        Log.e("TAG" , cursor.getString(cursor.getColumnIndex("_ID")));
                        cursor.moveToNext();
                    }
                    cursor.close();

                    // Set the ArrayAdapter as the ListView's adapter.



                } else {
                    //message for user
                }
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

        mainListView.setAdapter( listAdapter );

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView = (TextView) view.findViewById(R.id.rowTextView);

                if(isFromAssesment) {

                    Intent intent = new Intent(StudentListActivity.this, Assesmentctivity.class);
                    intent.putExtra("STUDENT_NAME", textView.getText().toString());
                    startActivity(intent);
                    finish();

                } else if(isFromResult){

                    Intent intent = new Intent(StudentListActivity.this, ResultActivity.class);
                    intent.putExtra("STUDENT_NAME", textView.getText().toString());
                    startActivity(intent);
                    finish();

                }  else {
                    Intent intent = new Intent(StudentListActivity.this, AddStudentActivity.class);
                    intent.putExtra("STUDENT_NAME", textView.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
