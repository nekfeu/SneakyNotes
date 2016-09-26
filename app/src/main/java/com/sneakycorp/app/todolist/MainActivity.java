package com.sneakycorp.app.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView listNote;
    ArrayList<Note> arrNotes;
    Gson gson;

    public void createNoteView() {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.putExtra("note", "");
        startActivity(intent);
    }

    public void loadAllNotes() {
        SharedPreferences pref = this.getSharedPreferences("MyNoteapp", MODE_PRIVATE);
        String jsonNotes = pref.getString("jsonNotes", "");

        gson = new Gson();
        Note[] notes = gson.fromJson(jsonNotes, Note[].class);
        arrNotes = new ArrayList<Note>();
        if (notes != null && notes.length > 0) {
            for (Note note : notes) {
                arrNotes.add(note);
            }
        }
        Collections.sort(arrNotes);
        Collections.reverse(arrNotes);

        NoteAdapter adapter = new NoteAdapter(MainActivity.this, arrNotes);
        listNote.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listNote = (ListView) findViewById(R.id.listView);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNoteView();
            }
        });
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                intent.putExtra("note", gson.toJson(arrNotes.get(position)));
                arrNotes.get(position).delete(getApplicationContext());
                startActivity(intent);
            }
        });
        loadAllNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
