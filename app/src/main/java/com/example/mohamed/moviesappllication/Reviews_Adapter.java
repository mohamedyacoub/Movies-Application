package com.example.mohamed.moviesappllication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by mohamed on 26/04/2016.
 */
public class Reviews_Adapter extends BaseAdapter {
    private Context context;
    private String[]reviews;

    public Reviews_Adapter(Context context,String[] reviews) {
        this.context = context;
        this.reviews=reviews;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public Object getItem(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view_Holder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.reviews_layout_,null);
            vh = new view_Holder();
            vh.txt_author = (TextView) convertView.findViewById(R.id.textView7);
            //vh.txt_content = (TextView) convertView.findViewById(R.id.textView8);
            convertView.setTag(vh);
        }else{
            vh = (view_Holder) convertView.getTag();
        }


        if(reviews!=null) {
            vh.txt_author.setText(reviews[position]);
        }
        return convertView;
    }
    class view_Holder {
        TextView txt_author;
    }
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        if(reviews!=null)
            return reviews.length;

        return reviews.length;
    }




}
