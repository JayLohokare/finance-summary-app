package in.skylinelabs.barclaysmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jay Lohokare on 04-Mar-16.
 */
public class Card_Adapter extends ArrayAdapter<CardDetails> {

    AlertDialog alertDialog;

    public static String t;

    // declaring our ArrayList of items
    private List<CardDetails> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * final Cone
    * because it is the list of objects we want to display.
    */

    final Context context = this.getContext();

    public Card_Adapter(Context context, int textViewResourceId, List<CardDetails> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){


        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.card_list_element, parent, false);

        }
        final CardDetails i = objects.get(position);



        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView cardNo = (TextView) v.findViewById(R.id.editTextCard);

            cardNo.setText(i.getcard_number());

        }
       /* v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); */

        // the view must be returned to our activity
        return v;

    }

}


