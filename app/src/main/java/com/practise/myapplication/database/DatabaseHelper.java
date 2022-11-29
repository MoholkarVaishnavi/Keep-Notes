package com.practise.myapplication.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.practise.myapplication.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "keep_notes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TAG = "DatabaseHelper";

    public static final String CREATE_TABLE = "CREATE TABLE " + KeepNotesModel.TABLE_NAME + " ( " +
            KeepNotesModel.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            KeepNotesModel.COL_NAME + " TEXT NOT NULL ," +
            KeepNotesModel.COL_DETAILS + " TEXT NOT NULL " + ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO::Upgrade database
    }


    public void addNote(SQLiteDatabase db, String name, String details) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(KeepNotesModel.COL_NAME, name);
            values.put(KeepNotesModel.COL_DETAILS, details);
            db.insert(KeepNotesModel.TABLE_NAME, null, values);
        }
    }

    public List<NoteModel> fetchAllNotes(SQLiteDatabase db){
        String sqlQuery = "Select * from " + KeepNotesModel.TABLE_NAME;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery(sqlQuery, null);
        List<NoteModel> resultantList = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt( cursor.getColumnIndex( KeepNotesModel.COL_ID ) );
            @SuppressLint("Range") String name = cursor.getString( cursor.getColumnIndex( KeepNotesModel.COL_NAME ) );
            @SuppressLint("Range") String details = cursor.getString( cursor.getColumnIndex( KeepNotesModel.COL_DETAILS ) );
            NoteModel model = new NoteModel(id, name, details);
            resultantList.add(model);
        }
        return resultantList;
    }

    public List<NoteModel> searchNotes(SQLiteDatabase db, String query){
        String sqlQuery = "SELECT * FROM " + KeepNotesModel.TABLE_NAME +" WHERE " +
                KeepNotesModel.COL_NAME + " LIKE '%" + query + "%' OR " +
                KeepNotesModel.COL_DETAILS + " LIKE '%" + query + "%'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sqlQuery, null);
        List<NoteModel> resultantList = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt( cursor.getColumnIndex( KeepNotesModel.COL_ID ) );
            @SuppressLint("Range") String name = cursor.getString( cursor.getColumnIndex( KeepNotesModel.COL_NAME ) );
            @SuppressLint("Range") String details = cursor.getString( cursor.getColumnIndex( KeepNotesModel.COL_DETAILS ) );
            NoteModel model = new NoteModel(id, name, details);
            resultantList.add(model);
        }
        return resultantList;
    }


    public void deleteNoteById(SQLiteDatabase db, long id) {
        String sqlQuery = "DELETE from " + KeepNotesModel.TABLE_NAME + " WHERE " + KeepNotesModel.COL_ID + " = " + id;
        db.execSQL(sqlQuery);
    }
}
