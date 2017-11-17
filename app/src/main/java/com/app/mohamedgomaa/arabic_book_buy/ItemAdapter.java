package com.app.mohamedgomaa.arabic_book_buy;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>{
    private ArrayList<item> myList;
    private Context myCon;

    public ItemAdapter(ArrayList<item> myList, Context myCon) {
        this.myList = myList;
        this.myCon = myCon;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    static int count = 0;

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Picasso.with(myCon).load(myList.get(position).pth_photo).placeholder(R.drawable.file_wait).error(R.drawable.file_wait).into(holder.img);
        holder.txtPrice.setText(holder.txtPrice.getText()+ String.valueOf(myList.get(position).price));
        if(Locale.getDefault().getLanguage().equals("ar")) {
            holder.txtDetails.setText(myList.get(position).details_ar);
            holder.txtTitle.setText(myList.get(position).title_ar);
            holder.txtAuthor.setText(myList.get(position).author_ar);
        }else {
            holder.txtDetails.setText(myList.get(position).details_en);
            holder.txtTitle.setText(myList.get(position).title_en);
            holder.txtAuthor.setText(myList.get(position).author_en);
        }

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitle;
        com.borjabravo.readmoretextview.ReadMoreTextView txtDetails;
        TextView txtPrice;
        TextView txtAuthor;
        Button dwn;
        Button rvw;
        CardView cardView;

        public Holder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imgbook);
            txtTitle = (TextView) view.findViewById(R.id.titleName);
            txtDetails = (ReadMoreTextView) view.findViewById(R.id.details);
            txtPrice = (TextView) view.findViewById(R.id.price);
            txtAuthor=(TextView)view.findViewById(R.id.author);
            dwn=(Button)view.findViewById(R.id.download);
            rvw=(Button)view.findViewById(R.id.review);
            cardView=(CardView)view.findViewById(R.id.card_view);
        }

    }
}