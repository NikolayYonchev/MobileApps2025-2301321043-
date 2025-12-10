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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesapp.DB.DbNote;

public class NewActivity extends AppCompatActivity {

    EditText txtTitle, txtNote, txtDate;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new);

        txtTitle = findViewById(R.id.txtTitle);
        txtNote = findViewById(R.id.txtNote);
        txtDate = findViewById(R.id.txtDate);
        btSave = findViewById(R.id.btnSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbNote dbNote = new DbNote(NewActivity.this);

                long id = dbNote.insertNote(txtTitle.getText().toString(), txtNote.getText().toString(), txtDate.getText().toString());

                if (id>0){
                    Toast.makeText(NewActivity.this, "SAVED NOTE", Toast.LENGTH_LONG).show();
                    Clean();
                    back();
                }
                else{
                    Toast.makeText(NewActivity.this, "ERROR SAVING THE NOTE", Toast.LENGTH_LONG).show();
                }

            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Clean(){
        txtTitle.setText("");
        txtNote.setText("");
        txtDate.setText("");

    }

    private void back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}