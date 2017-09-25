package com.bk.assaignment1;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class DatabaseManagerImpl implements IDatabaseManager {

    public static final String TAG = "DATABASE";

    private static final String DATABASE_NAME = "cwt.db";
    private static final String SQL_FILE_NAME = "grading_assesment.sql";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper databaseHelper;
    private Context context;

    private boolean dbDownloadStatus;

    private static DatabaseManagerImpl mInstance;

    private String databaseDirectory = Environment.getExternalStorageDirectory() + File.separator + "Assaigmnent1";

    static {
        if (mInstance == null) {
            mInstance = new DatabaseManagerImpl();
        }
    }

    /**
     * Open database in writable mode
     *
     * @return SQLiteDatabase
     */
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return mInstance.openWritableDatabase();
    }

    /**
     * Open database in readable mode
     *
     * @return SQLiteDatabase
     */
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return mInstance.openReadableDatabase();
    }

    /**
     * Create or prepare database for entire application
     *
     * @return boolean
     */
    @Override
    public boolean setupDatabase() {
        return mInstance.prepareDatabse();
    }

    /**
     * Close databse connection
     */
    @Override
    public void closeDatabase() {
        mInstance.closeDatabaseConnection();
    }

    /**
     * Opens the database in read only mode
     *
     * @return database
     */
    private SQLiteDatabase openReadableDatabase() {
        try {
            // Pass method parameter value true if open databse is read only mode
            databaseHelper.openDataBase(true);

        } catch (SQLException e) {
            // Exception throws because database is not prepared. Setup it again to be operational.
            setupDatabase();

            // Pass method parameter value true if open databse is read only mode
            databaseHelper.openDataBase(true);

        } catch (NullPointerException e) {
            // Exception throws because database is not prepared. Setup it again to be operational.
            setupDatabase();

            // Pass method parameter value false if open databse is write only mode
            databaseHelper.openDataBase(true);
        }

        return databaseHelper.getDatabaseInstance();

    }

    /**
     * Opens the database in write mode
     *
     * @return SQLiteDatabase
     */
    private SQLiteDatabase openWritableDatabase() {
        try {
            // Pass method parameter value false if open databse is write only mode
            databaseHelper.openDataBase(false);

        } catch (SQLException e) {
            // Exception throws because database is not prepared. Setup it again to be operational.
            setupDatabase();

            // Pass method parameter value false if open databse is write only mode
            databaseHelper.openDataBase(false);

        } catch (NullPointerException e) {
            // Exception throws because database is not prepared. Setup it again to be operational.
            setupDatabase();

            // Pass method parameter value false if open databse is write only mode
            databaseHelper.openDataBase(false);
        }

        return databaseHelper.getDatabaseInstance();
    }


    private void closeDatabaseConnection() {
        // Check if database connection is live
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    private boolean prepareDatabse() {
        // Get only application context to avoid leackage
        context = MainApplication.getInstance();

        File file = new File(databaseDirectory);

        // Create database directory if not exist
        if (!file.exists()) {
            file.mkdirs();
        }

        // Setup database connection based on application mode
        setupDatabaseConnection();

        // Create database
        databaseHelper.createDatabase(SQL_FILE_NAME);

        return true;
    }

    /**
     * This method will be used later to initialize helper on activity resume
     * because android re-initializes static singletones
     */
    public void setupDatabaseConnection() {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);

    }

}