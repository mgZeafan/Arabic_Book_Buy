package com.app.mohamedgomaa.arabic_book_buy;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mohamed on 11/15/2017.
 */

public class ItemAdapter extends  RecyclerView.Adapter<ItemAdapter.Holder> implements View.OnLongClickListener{ {

    public static class Holder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView txtname;
        TextView txtemail;
        CheckBox box;
        CardView cardView;
        MainActivity mainActivity;

        public Holder(View view, final MainActivity mainActivity) {
            super(view);
            img=(ImageView)view.findViewById(R.id.imageView123);
            txtemail=(TextView)view.findViewById(R.id.email);
            txtname=(TextView)view.findViewById(R.id.name);
            box=(CheckBox)view.findViewById(R.id.checkbox);
            cardView=(CardView)view.findViewById(R.id.card_view);
            this.mainActivity=mainActivity;
            cardView.setOnLongClickListener(mainActivity);
        }
    }

}
