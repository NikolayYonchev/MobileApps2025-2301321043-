package com.example.notesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Note;
import com.example.notesapp.R;
import com.example.notesapp.VisualizeActivity;

import java.util.ArrayList;

public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.NoteViewHolder> {

    ArrayList<Note> listNote;

    public ListNoteAdapter(ArrayList<Note> listNote){

        this.listNote = listNote;

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note,null,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        holder.viewTitle.setText(listNote.get(position).getTitle());
        holder.viewNote.setText(listNote.get(position).getNote());
        holder.viewDate.setText(listNote.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView viewTitle, viewNote, viewDate;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTitle = itemView.findViewById(R.id.viewTitle);
            viewNote = itemView.findViewById(R.id.viewNote);
            viewDate = itemView.findViewById(R.id.viewDate);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VisualizeActivity.class);
                    intent.putExtra("ID", listNote.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });
        }
    }
}
