package com.example.android.waitlist.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.android.waitlist.data.WaitlistContract.WaitlistEntry

class WaitlistDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_WAITLIST_TABLE = """
CREATE TABLE ${WaitlistEntry.TABLE_NAME} (
${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
${WaitlistEntry.COLUMN_GUEST_NAME} TEXT NOT NULL,
${WaitlistEntry.COLUMN_PARTY_SIZE} INTEGER NOT NULL,
${WaitlistEntry.COLUMN_TIMESTAMP} TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
""";
        db.execSQL(SQL_CREATE_WAITLIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${WaitlistEntry.TABLE_NAME}")
        onCreate(db)
    }

    companion object {

        internal val DATABASE_NAME = "waitlist.db"
        internal val DATABASE_VERSION = 1
    }


    // TODO (6) Inside, create an String query called SQL_CREATE_WAITLIST_TABLE that will create the table

    // TODO (7) Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_WAITLIST_TABLE

    // TODO (8) Override the onUpgrade method

    // TODO (9) Inside, execute a drop table query, and then call onCreate to re-create it

}