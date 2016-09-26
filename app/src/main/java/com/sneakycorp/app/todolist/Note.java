package com.sneakycorp.app.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kevin on 22/09/2016.
 */
public class Note {
    private String text;
    private Date time;

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public Date getTime() {
        return this.time;
    }

    public String getStringTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.time);
    }

    public ArrayList<Note> getAllNotes(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyNoteapp", context.MODE_PRIVATE);
        String jsonNotes = pref.getString("jsonNotes", "");
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        Note[] notes = gson.fromJson(jsonNotes, Note[].class);

        // Log
        System.out.println(notes);

        ArrayList<Note> notesArray = new ArrayList<>();
        if (notes != null && notes.length > 0) {
            for (Note note : notes) {
                notesArray.add(note);
            }
        }
        return notesArray;
    }

    public void saveAllNotes(Context context, ArrayList<Note> notesArray) {
        SharedPreferences pref = context.getSharedPreferences("MyNoteapp", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        String jsonFinal = gson.toJson(notesArray);
        editor.putString("jsonNotes", jsonFinal);
        editor.apply();

        // Log
        System.out.println(jsonFinal);
    }

    public void save(Context context) {
        ArrayList<Note> notesArray = getAllNotes(context);
        notesArray.add(this);
        saveAllNotes(context, notesArray);
    }

    public void delete(Context context) {
        ArrayList<Note> notesArray = getAllNotes(context);

        for (int i = 0 ; i < notesArray.size() ; i++) {
            if (notesArray.get(i).getTime().equals(this.getTime())) {
                notesArray.remove(i);
                break;
            }
        }
        saveAllNotes(context, notesArray);
    }
}
