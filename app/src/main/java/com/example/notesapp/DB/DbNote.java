package com.example.notesapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.notesapp.Note;

import java.util.ArrayList;

public class DbNote extends DbHelper {

    Context context;


    public DbNote(@Nullable Context context) {
        super(context);
        this.context = context;

    }

    public long insertNote(String title, String note, String date){
        long id =0;
        try {


            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("note", note);
            values.put("date", date);

            id= db.insert(TABLE_NOTE,null,values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;

    }


    public boolean editNote(int id, String title, String note, String date){
        boolean correct =false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
                db.execSQL("UPDATE " + TABLE_NOTE + " SET title = '" + title + "', note = '" + note + "' WHERE id='" + id + "' ");
                correct = true;
        }catch (Exception ex){
            ex.toString();
        }finally {
            db.close();
        }

        return correct;

    }

    public boolean deleteNote(int id){
        boolean correct =false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_NOTE + " WHERE id='" + id + "' ");
            correct = true;
        }catch (Exception ex){
            ex.toString();
        }finally {
            db.close();
        }

        return correct;

    }

    public ArrayList<Note> showNote(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Note> listNote = new ArrayList<>();
        Note note = null;
        Cursor cursorNote = null;

        cursorNote = db.rawQuery("SELECT * FROM "+ TABLE_NOTE,null);

        if (cursorNote.moveToFirst()){

            do {
                note = new Note();
                note.setId(cursorNote.getInt(0));
                note.setTitle(cursorNote.getString(1));
                note.setNote(cursorNote.getString(2));
                note.setDate(cursorNote.getString(3));
                listNote.add(note);
            }while(cursorNote.moveToNext());
        }

        cursorNote.close();

        return listNote;
    }

    public Note seeNote(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Note note = null;
        Cursor cursorNote = null;

        cursorNote = db.rawQuery("SELECT * FROM "+ TABLE_NOTE +" WHERE id = "+ id+" LIMIT 1",null);

        if (cursorNote.moveToFirst()){

                note = new Note();
                note.setId(cursorNote.getInt(0));
                note.setTitle(cursorNote.getString(1));
                note.setNote(cursorNote.getString(2));
                note.setDate(cursorNote.getString(3));
        }

        cursorNote.close();

        return note;


    }

}
