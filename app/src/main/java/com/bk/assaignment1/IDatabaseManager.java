package com.bk.assaignment1;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public interface IDatabaseManager {
    SQLiteDatabase getWritableDatabase();   // Open database in write mode
    SQLiteDatabase getReadableDatabase();   // Open database in read mode
    boolean setupDatabase();    // Prepare databse for entire app
    void closeDatabase();   // Close database connection
}