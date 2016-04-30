package com.example.mohamed.moviesappllication;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    DetailDataModel[] model;
    DetailDataModel[] model_reviews;
    int id;
    TextView titletxt;
    View rootView;
    Context contex;
    String overView;
    String poster;
    String title;
    double vote;
    String date;
    TextView overtxt;
    TextView datetxt;
    TextView votetxt;
    ImageView image;
    ListView trailers_list;
    ListView reviews_list;
    MoviesDB New_favourite;
    Button btn_favourite;



    public DetailsFragment() {
        // Required empty public constructor
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        String titlet2=d.getString("title2");
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        contex = getActivity();

        if(!isTablet(getActivity())){
            Bundle detail_bundle=getActivity().getIntent().getExtras();
            id =Integer.parseInt(detail_bundle.getString("ID"));
            overView = detail_bundle.getString("over_view");
            poster = detail_bundle.getString("poster");
            title = detail_bundle.getString("title");
            vote = detail_bundle.getDouble("vote");
            date = detail_bundle.getString("date");
        }

        else
        {
            Bundle detail_bundle=getArguments();
            id =Integer.parseInt(detail_bundle.getString("ID"));
            overView = detail_bundle.getString("over_view");
            poster = detail_bundle.getString("poster");
            title = detail_bundle.getString("title");
            vote = detail_bundle.getDouble("vote");
            date = detail_bundle.getString("date");
        }

        titletxt = (TextView) rootView.findViewById(R.id.yaqub);
        overtxt = (TextView) rootView.findViewById(R.id.textView4);
        datetxt = (TextView) rootView.findViewById(R.id.textView5);
        votetxt = (TextView) rootView.findViewById(R.id.textView6);
        image = (ImageView) rootView.findViewById(R.id.imageView3);
        trailers_list = (ListView) rootView.findViewById(R.id.listViewtrailers);

        title=getArguments().getString("title");

    /*    titletxt.setText(title);
        overtxt.setText(overView);
        datetxt.setText(date);
        votetxt.setText(Double.toString(vote));
        Picasso.with(contex).load("http://image.tmdb.org/t/p/w185" + poster).into(image);
*/
        trailers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + model[position].key));
                startActivity(i);
            }
        });

//        reviews_list.setOnTouchListener(new ListView.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Disallow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        // Allow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//
//                // Handle ListView touch events.
//                v.onTouchEvent(event);
//                return true;
//
//            }
//        });
        trailers_list.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;

        }
        });


        New_favourite = new MoviesDB(contex);
        btn_favourite = (Button) rootView.findViewById(R.id.btn_favourite);


        if (New_favourite.checkMovie(title)) {
            btn_favourite.setText("marked as favourite");
            // change button color
            btn_favourite.setBackgroundColor(5);
        }

        Trailers_Async DownLoaded_trailers = new Trailers_Async(contex);
        DownLoaded_trailers.execute();

        Reviews_Async DownLoaded_reviews = new Reviews_Async(contex);
        DownLoaded_reviews.execute();


        btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!New_favourite.checkMovie(title)) {

                    New_favourite.addNewMovie(id, poster, title, overView, date, vote);
                    btn_favourite.setText("marked as favourite");
                }
            }
        });

        titletxt.setText(getArguments().getString("title"));
        overtxt.setText(getArguments().getString("over_view"));
        datetxt.setText(getArguments().getString("date"));
        votetxt.setText(getArguments().getString("vote"));
        Picasso.with(contex).load("http://image.tmdb.org/t/p/w185" + getArguments().getString("poster")).into(image);
        return rootView;


//       View fragment = inflater.inflate(R.layout.fragment_details, container, false);
//      TextView  tvName = (TextView) fragment.findViewById(R.id.yaqub);
        //Get the selected "name" from the received "extras" bundle
     //  String name = getArguments().getString("ID");
//        tvName.setText("");


     //   tvName.setText("Welcome " + name);
  //      return fragment;


    }
    public class Trailers_Async extends AsyncTask<URL, Integer, String[]> {

        Context main_Context;
        int id_;

        String[] trailors;
        String key;

        Trailers_Async(Context context) {
            main_Context = context;
        }


        @Override
        protected void onPostExecute(String[] strings) {

            String[] trailers = strings;

            ListView trailers_list = (ListView) rootView.findViewById(R.id.listViewtrailers);
            if (trailers != null) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(main_Context, android.R.layout.simple_list_item_1, trailers);
                trailers_list.setAdapter(adapter);

            }

            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(URL... params) {
            HttpURLConnection Url_Connection = null;
            BufferedReader Reader = null;
            URL API_url;
            String MovieJsonStr = null;

            try {
//Toast.makeText(getApplicationContext(),id+"",Toast.LENGTH_LONG).show();
                Log.i("idddddd", id + "");
                API_url = new URL("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=29ada0c7f50ff3de567515c6198a1748");

                //API_url=new URL("https://api.themoviedb.org/3/movie/popular?api_key=29ada0c7f50ff3de567515c6198a1748");

                Url_Connection = (HttpURLConnection) API_url.openConnection();
                Url_Connection.setRequestMethod("GET");
                Url_Connection.connect();

                InputStream Input_Stream = Url_Connection.getInputStream();
                StringBuffer Buffer = new StringBuffer();
                if (Input_Stream == null) {
                    return null;
                }
                Reader = new BufferedReader(new InputStreamReader(Input_Stream));
                String Line;
                while ((Line = Reader.readLine()) != null) {

                    Buffer.append(Line + "\n");
                }


                if (Buffer.length() == 0) {
                    return null;
                }


                MovieJsonStr = Buffer.toString();
                try {


                    JSONObject MoviesJson = new JSONObject(MovieJsonStr);
                    JSONArray MoviesJsonArray = MoviesJson.getJSONArray("results");
                    trailors = new String[MoviesJsonArray.length()];
                    String name;
                    String my_trailer;
                    JSONObject Movie_obj_i;
                    int jsonArrayLength = MoviesJsonArray.length();
                    model = new DetailDataModel[jsonArrayLength];
                   /* for()
                    {
                        model[i]=new MovieDataModel();
                    }*/

                    for (int i = 0; i < jsonArrayLength; i++) {
                        model[i] = new DetailDataModel();
                        Movie_obj_i = MoviesJsonArray.getJSONObject(i);
                        key = Movie_obj_i.getString("key");
                        name = Movie_obj_i.getString("name");
                        // id_ = Movie_obj_i.getInt("id");


                        //model[i].id=id;
                        model[i].key = key;
                        model[i].name = name;
                        trailors[i] = model[i].name;


                    }

                    return trailors;
                    //Vector<ContentValues> CV_Vector=new Vector<ContentValues>(MoviesJsonArray.length());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONArray jsonArray = new JSONArray();





            /*


             */

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (Url_Connection != null) {
                    Url_Connection.disconnect();
                }
                if (Reader != null) {
                    try {

                        //return get
                        Reader.close();

                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }

    public class Reviews_Async extends AsyncTask<URL, Integer, String[]> {

        Context main_Context;
        int id_;

        String[] reviews;
        String key;

        Reviews_Async(Context context) {
            main_Context = context;
        }


        @Override
        protected void onPostExecute(String[] strings) {

            String[] reviews = strings;

            ListView mylist = (ListView) rootView.findViewById(R.id.listViewreviews);
            if (reviews != null) {

                Reviews_Adapter adapter = new Reviews_Adapter(main_Context, reviews);
                mylist.setAdapter(adapter);

            }

            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(URL... params) {
            HttpURLConnection Url_Connection = null;
            BufferedReader Reader = null;
            URL API_url;
            String MovieJsonStr = null;

            try {
//Toast.makeText(getApplicationContext(),id+"",Toast.LENGTH_LONG).show();
                Log.i("idddddd", id + "");
                API_url = new URL("https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=29ada0c7f50ff3de567515c6198a1748");

                //API_url=new URL("https://api.themoviedb.org/3/movie/popular?api_key=29ada0c7f50ff3de567515c6198a1748");

                Url_Connection = (HttpURLConnection) API_url.openConnection();
                Url_Connection.setRequestMethod("GET");
                Url_Connection.connect();

                InputStream Input_Stream = Url_Connection.getInputStream();
                StringBuffer Buffer = new StringBuffer();
                if (Input_Stream == null) {
                    return null;
                }
                Reader = new BufferedReader(new InputStreamReader(Input_Stream));
                String Line;
                while ((Line = Reader.readLine()) != null) {

                    Buffer.append(Line + "\n");
                }


                if (Buffer.length() == 0) {
                    return null;
                }


                MovieJsonStr = Buffer.toString();
                try {


                    JSONObject MoviesJson = new JSONObject(MovieJsonStr);
                    JSONArray MoviesJsonArray = MoviesJson.getJSONArray("results");
                    reviews = new String[MoviesJsonArray.length()];
                    String author;
                    String content;
                    JSONObject Movie_obj_i;
                    int jsonArrayLength = MoviesJsonArray.length();
                    model_reviews = new DetailDataModel[jsonArrayLength];
                   /* for()
                    {
                        model[i]=new MovieDataModel();
                    }*/

                    for (int i = 0; i < jsonArrayLength; i++) {
                        model_reviews[i] = new DetailDataModel();
                        Movie_obj_i = MoviesJsonArray.getJSONObject(i);
                        String id_ = Movie_obj_i.getString("id");
                        model_reviews[i].author = Movie_obj_i.getString("author");
                        model_reviews[i].content = Movie_obj_i.getString("content");

                        reviews[i] = "  author   :  " + model_reviews[i].author + "\n  content  :  " + model_reviews[i].content;


                        //model[i].id=id;


                    }

                    return reviews;
                    //Vector<ContentValues> CV_Vector=new Vector<ContentValues>(MoviesJsonArray.length());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //JSONArray jsonArray = new JSONArray();





            /*


             */

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (Url_Connection != null) {
                    Url_Connection.disconnect();
                }
                if (Reader != null) {
                    try {

                        //return get
                        Reader.close();

                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }

}
