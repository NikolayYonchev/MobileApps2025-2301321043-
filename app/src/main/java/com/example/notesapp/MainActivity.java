package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesapp.R;
import com.example.notesapp.DB.DbNote;
import com.example.notesapp.adapters.ListNoteAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listNote;
    ArrayList<Note> listArrayNote;

    private void loadFragment(Fragment fragment, int containerId){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listNote = findViewById(R.id.listNote);

        listNote.setLayoutManager(new LinearLayoutManager(this));

        DbNote dbNote = new DbNote(MainActivity.this);

        listArrayNote = new ArrayList<Note>();

        ListNoteAdapter adapter = new ListNoteAdapter(dbNote.showNote());

        listNote.setAdapter(adapter);


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.newMenu) {
            newRegister();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newRegister(){
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

}