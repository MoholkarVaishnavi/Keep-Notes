package com.practise.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practise.myapplication.database.DatabaseHelper;

public class AddNoteActivity extends AppCompatActivity {

    EditText noteTitle,noteDetails;
    Button addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    private void addNewNote() {
        if (TextUtils.isEmpty(noteTitle.getText()))
        {
            Toast.makeText(this, "Please enter note title!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(noteDetails.getText()))
        {
            Toast.makeText(this, "Please enter note details!", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        SQLiteDatabase mDatabase = mDBHelper.getWritableDatabase();
        mDBHelper.addNote(mDatabase, noteTitle.getText().toString(), noteDetails.getText().toString());
        finish();

    }

    private void initViews() {
        noteTitle = findViewById(R.id.et_note_title);
        noteDetails = findViewById(R.id.et_note_details);
        addNoteButton = findViewById(R.id.bt_add_note);
    }
}