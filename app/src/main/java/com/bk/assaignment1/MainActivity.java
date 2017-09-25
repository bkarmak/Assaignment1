package com.bk.assaignment1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity{

    private Button btnSubmit;
    private IDatabaseManager iDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if(extras.getBoolean("STUDENT")){
                findViewById(R.id.btn_add_student).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_studentlist).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_assesment).setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_result).setVisibility(View.VISIBLE);
            }
            else{
                findViewById(R.id.btn_add_student).setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_studentlist).setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_assesment).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_result).setVisibility(View.VISIBLE);

            }
        }

        iDatabaseManager = new DatabaseManagerImpl();
        iDatabaseManager.setupDatabase();

        findViewById(R.id.btn_add_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddStudentActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_studentlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StudentListActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_assesment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Assesmentctivity.class);
                startActivity(i);
            }
        });


    }


}
