package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.DB.DbNote;

public class EditActivity extends AppCompatActivity{

    EditText txtTitle, txtNote, txtDate;
    Button btnSave;
    boolean correct = false;
    Note note;
    int id= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);

        txtTitle = findViewById(R.id.txtTitle);
        txtNote = findViewById(R.id.txtNote);
        txtDate = findViewById(R.id.txtDate);
        btnSave = findViewById(R.id.btnSave);

        if(savedInstanceState == null){

            Bundle extra = getIntent().getExtras();
            if (extra == null){

                id=Integer.parseInt(null);
            }
            else{
                id = extra.getInt("ID");
            }

        } else{

            id = (int)savedInstanceState.getSerializable("ID");
        }

        DbNote dbNote = new DbNote(EditActivity.this);
        note = dbNote.seeNote(id);

        if (note !=null){
            txtTitle.setText(note.getTitle());
            txtNote.setText(note.getNote());
            txtDate.setText(note.getDate());

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtTitle.getText().toString().equals("") && !txtNote.getText().toString().equals("")){
                    correct = dbNote.editNote(id, txtTitle.getText().toString(), txtNote.getText().toString(), txtDate.getText().toString());

                    if (correct){
                        Toast.makeText(EditActivity.this, "SUCCESSFULLY EDITED THE NOTE", Toast.LENGTH_LONG).show();
                        seeRegister();
                    }else{
                        Toast.makeText(EditActivity.this, "ERROR TRYING TO EDIT THE NOTE", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditActivity.this, "FILL IN THE REQUIRED FIELDS", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void seeRegister(){
        Intent intent = new Intent(this, VisualizeActivity.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
