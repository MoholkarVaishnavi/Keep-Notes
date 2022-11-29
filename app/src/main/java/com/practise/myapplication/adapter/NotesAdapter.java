package com.practise.myapplication.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practise.myapplication.R;
import com.practise.myapplication.database.DatabaseHelper;
import com.practise.myapplication.models.NoteModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    Context context;
    List<NoteModel> noteModel;
    public static final String TAG = "NotesAdapter";

    public NotesAdapter(Context context, List<NoteModel> noteModel) {
        this.context = context;
        this.noteModel = noteModel;
    }

    public void setLists(List<NoteModel> noteModel) {
        this.noteModel = noteModel;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.noteTitle.setText(noteModel.get(position).getName());
        holder.noteDetails.setText(noteModel.get(position).getDetails());
        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote(position);
            }
        });
    }

    private void deleteNote(int position) {
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        SQLiteDatabase mDatabase = mDBHelper.getWritableDatabase();
        mDBHelper.deleteNoteById(mDatabase, noteModel.get(position).getId());
        noteModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteModel.size());
    }

    @Override
    public int getItemCount() {
        return noteModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteDetails;
        ImageButton deleteNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.tv_note_title);
            noteDetails = itemView.findViewById(R.id.tv_note_details);
            deleteNote = itemView.findViewById(R.id.ibt_delete_note);
        }
    }
}
