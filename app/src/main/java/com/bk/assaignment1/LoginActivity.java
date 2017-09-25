package com.bk.assaignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText) findViewById(R.id.etName)).getText().toString().equalsIgnoreCase("teacher") &&
                        ((EditText) findViewById(R.id.etPassword)).getText().toString().equalsIgnoreCase("123456")){

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("STUDENT", false);
                    startActivity(i);
                    finish();

                } else if(((EditText) findViewById(R.id.etName)).getText().toString().equalsIgnoreCase("student") &&
                        ((EditText) findViewById(R.id.etPassword)).getText().toString().equalsIgnoreCase("123456")){

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("STUDENT", true);
                    startActivity(i);
                    finish();

                }
            }
        });
    }
}
