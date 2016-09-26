package com.sneakycorp.app.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {

    EditText textNote;
    Note note;

    public void deleteNote() {
        if (note != null) {
            note.delete(getApplicationContext());
        }
        finish();
    }

    public void createNote() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Note newNote = new Note();

        newNote.setText(textNote.getText().toString());
        newNote.setTime(date);
        newNote.save(getApplicationContext());

        System.out.println("Nouvelle note du " + dateFormat.format(date) + " - Content : " + textNote.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Intent intent = getIntent();
        Gson gson = new Gson();

        textNote = (EditText) findViewById(R.id.editText);
        if (intent.getStringExtra("note").length() > 0) {
            note = gson.fromJson(intent.getStringExtra("note"), Note.class);
            textNote.setText(note.getText());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_note) {
            // save and back
            createNote();
            finish();
            return true;
        } else if (id == R.id.join_data) {
            return true;
        } else if (id == R.id.delete_note) {
            deleteNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
