package com.practise.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practise.myapplication.adapter.NotesAdapter;
import com.practise.myapplication.database.DatabaseHelper;
import com.practise.myapplication.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_add_note;
    RecyclerView notesRecyclerView;
    List<NoteModel> noteModelList;
    NotesAdapter adapter;
    EditText searchBox;

    @Override
    protected void onStart() {
        super.onStart();
        fetchDataFromDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_add_note = findViewById(R.id.fab_add_button);
        searchBox = findViewById(R.id.et_search);
        notesRecyclerView = findViewById(R.id.rv_notes);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteModelList = new ArrayList<>();
        adapter = new NotesAdapter(this, noteModelList);
        notesRecyclerView.setAdapter(adapter);

        fab_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchDataFromDb() {
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        SQLiteDatabase mDatabase = mDBHelper.getReadableDatabase();
        noteModelList = mDBHelper.fetchAllNotes(mDatabase);
        adapter.setLists(noteModelList);
        adapter.notifyDataSetChanged();


        searchBox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    noteModelList = mDBHelper.searchNotes(mDatabase, searchBox.getText().toString());
                    adapter.setLists(noteModelList);
                    adapter.notifyDataSetChanged();

                    hideKeyboard(MainActivity.this);

                    return true;
                }
                return false;
            }
        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}