package com.example.mohamed.moviesappllication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mohamed on 26/04/2016.
 */
public class ImageAdapter extends BaseAdapter {

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    private Context ImageContext;
    private String[]Paths;
    private String[]Titles;
    public ImageAdapter(Context Image_contex,String[] paths,String[] titles) {
        ImageContext = Image_contex;
        Paths=paths;
        Titles=titles;
    }

    @Override
    public int getCount() {
        if(Paths!=null)
            return Paths.length;

        return Titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    /*public int getCount() {
            return Posters.size();
        }
    */
  /*  public String getItem(int Image_position) {
        return Posters.get(Image_position);
    }
*/
    public long getItemId(int Image_position) {
        return Image_position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView Image_view;
//        view_holder holder = new view_holder();
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(ImageContext).inflate(R.layout.poster_title, null);
//            //ImageView Image_view=(ImageView)convertView.findViewById(R.id.imageView2);
//            holder.image_v = (ImageView) convertView.findViewById(R.id.imageView2);
//            convertView.setTag(holder);
//
//
//        } else {
//
//            holder = (view_holder) convertView.getTag();
//
//        }
//        String Path="http://image.tmdb.org/t/p/w185//";
////http://image.tmdb.org/t/p/w185//
//        //for loop
//
//        Path+=Paths[position];
//          Picasso.with(ImageContext).load(Path).into(holder.image_v);
//        //Picasso.with(ImageContext).load(getItem(position)).into(holder.image_v);
//        return convertView;
//
        String path="http://image.tmdb.org/t/p/w185//";
        view_holder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(ImageContext).inflate(R.layout.poster_title,null);
            vh = new view_holder();
            vh.image_v = (ImageView) convertView.findViewById(R.id.imageView2);
            vh.title = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(vh);
        }else{
            vh = (view_holder) convertView.getTag();
        }
        if(Paths!=null) {
            Picasso.with(ImageContext).load(path + Paths[position]).into(vh.image_v);
        }
        if(Titles!=null) {
            vh.title.setText(Titles[position]);
        }
        return convertView;
       /* if (convertView == null) {
            // if it's not recycled, initialize some attributes
            Image_view = new ImageView(ImageContext);



            Image_view.setLayoutParams(new GridView.LayoutParams(85, 85));
            Image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Image_view.setPadding(8, 8, 8, 8);
        } else {
            Image_view = (ImageView) convertView;
        }

        Image_view.setImageResource(Posters[position]);
        return Image_view;
    }*/
       /* ArrayList<String> Posters;
        {
            R.drawable.images, R.drawable.movies_1,
                    R.drawable.movies_2, R.drawable.movies_3



        } ;
*/
    }
}
