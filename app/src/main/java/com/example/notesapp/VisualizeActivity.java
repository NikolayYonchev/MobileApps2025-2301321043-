package com.example.notesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.DB.DbNote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VisualizeActivity extends AppCompatActivity {


    EditText txtTitle, txtNote, txtDate;
    Button btnSave;
    FloatingActionButton fabEdit, fabDelete;


    Note note;
    int id= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualize);

        txtTitle = findViewById(R.id.txtTitle);
        txtNote = findViewById(R.id.txtNote);
        txtDate = findViewById(R.id.txtDate);
        btnSave = findViewById(R.id.btnSave);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);
        Button btnGenerateContactQR = findViewById(R.id.btnGenerateNoteQR);
        ImageView imageViewContactQR = findViewById(R.id.imageViewNoteQR);

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

        DbNote dbNote = new DbNote(VisualizeActivity.this);
        note = dbNote.seeNote(id);

        if (note !=null){
            txtTitle.setText(note.getTitle());
            txtNote.setText(note.getNote());
            txtDate.setText(note.getDate());

            btnSave.setVisibility(View.INVISIBLE);
            txtTitle.setInputType(InputType.TYPE_NULL);
            txtNote.setInputType(InputType.TYPE_NULL);
            txtDate.setInputType(InputType.TYPE_NULL);
        }

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisualizeActivity.this, EditActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizeActivity.this);
                builder.setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbNote.deleteNote(id)){
                                    list();
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        btnGenerateContactQR.setOnClickListener(view -> {
            String titleQR = txtTitle.getText().toString();
            String noteQR = txtNote.getText().toString();
            String dateQR = txtDate.getText().toString();

            if (!titleQR.isEmpty() && !noteQR.isEmpty() && !dateQR.isEmpty()) {
                // Create vCard string
                String noteData =
                        "Title:" + titleQR + "\n" +
                        "Note:" + noteQR + "\n" +
                        "Date:" + dateQR + "\n";

                try {
                    // Generate QR Code
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(noteData, BarcodeFormat.QR_CODE, 400, 400);
                    imageViewContactQR.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error generating QR code", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
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
    private void list(){
        Intent intent = new Intent(this, MainActivity.class);
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