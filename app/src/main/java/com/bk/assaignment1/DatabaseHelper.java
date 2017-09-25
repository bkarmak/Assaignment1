package com.bk.assaignment1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Context context;

    public DatabaseHelper() {
        super(null, null, null, 1);
    }

    public DatabaseHelper(Context context, String databasePath, int databaseVersion) {
        super(context, databasePath, null, databaseVersion);
        this.context = context;
    }

    /**
     * Create database executing sql commands from file
     * @param sqlFile
     * @return boolean
     */
    public boolean createDatabase(String sqlFile) {
        try {
            // call to this.getWritableDatabase() will create a blank database file
            database = this.getWritableDatabase();

            InputStream sqlFileStream = this.context.getAssets().open(sqlFile);
            executeSqlStatements(sqlFileStream, database);

        } catch (IOException ioe) {
            if (BuildConfig.DEBUG) ioe.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Open Database in readable, writable mode
     * @param read
     * @throws SQLException
     */
    public void openDataBase(boolean isReadable) throws SQLException {
        //Open the database based on mode
        if (isReadable) {
            database = this.getReadableDatabase();
        } else {
            database = this.getWritableDatabase();
        }
    }

    /**
     * Execute sql statements
     * @param sqlFileStream
     * @return boolean
     */
    public boolean executeSqlStatements(InputStream sqlFileStream, SQLiteDatabase database) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(sqlFileStream));

        boolean statementStarted = false;
        StringBuffer stringStatement = new StringBuffer();

        try {
            if (database == null)
                database = this.getWritableDatabase();

            int i = 0;

            while (reader.ready()) {
                i++;
                String line = new String(reader.readLine());

                if (BuildConfig.DEBUG) Log.i(DatabaseManagerImpl.TAG, ">>>> Line No: " + i);

                if (line.startsWith("--"))
                    continue;// If line is SQL comment, ignore
                if (line.equals("")) {
                    continue;// If line is a blank line, ignore
                }

                // Start a SQL statement or add to an existing statement
                if (!statementStarted) {
                    statementStarted = true;
                    stringStatement = new StringBuffer();
                    stringStatement.append(" " + line);// append a space before the line

                } else {
                    stringStatement.append(" " + line);// append a space before the line
                }

                // End an sql statement
                if (line.contains(";")) {
                    statementStarted = false;
                    database.execSQL(stringStatement.toString());
                }
            }

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                sqlFileStream.close();
                database.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;

    }

    /**
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getDatabaseInstance() {

        return database;
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }


}
