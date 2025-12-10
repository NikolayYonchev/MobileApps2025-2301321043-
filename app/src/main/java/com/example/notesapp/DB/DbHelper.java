package com.example.notesapp.DB;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NoteApp.bd";
    public static final String TABLE_NOTE = "t_note";

    public DbHelper(@Nullable Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NOTE +"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title text not null,"+
                "note text not null,"+
                "date text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_NOTE);
        onCreate(sqLiteDatabase);


    }
}
