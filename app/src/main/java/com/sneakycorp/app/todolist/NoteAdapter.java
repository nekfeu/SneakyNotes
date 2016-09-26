package com.sneakycorp.app.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kevin on 23/09/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_note,parent, false);
        }

        NoteViewHolder viewHolder = (NoteViewHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new NoteViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Note tweet = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(tweet.getText());
        viewHolder.date.setText(tweet.getStringTime());

        return convertView;
    }

    private class NoteViewHolder {
        public TextView title;
        public TextView date;
    }
}
