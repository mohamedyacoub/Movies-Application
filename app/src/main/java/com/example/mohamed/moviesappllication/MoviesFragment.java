package com.example.mohamed.moviesappllication;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    String ID;
    String title;
    String poster;
    String over_view;
    Double avg;
    String release_date;


    ImageAdapter imageAdapter;
    Context contex;
    GridView Movies_Grid;
    MovieDataModel[] model;
    String[] Poster_paths;
    MoviesDB Favourite_movies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //showing options menu
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        contex = getActivity();
        Favourite_movies = new MoviesDB(contex);


        view_holder vh;

        ArrayAdapter<String> MoviesAdapter = new ArrayAdapter<String>(contex, android.R.layout.simple_list_item_1);
        Movies_Grid = (GridView) rootView.findViewById(R.id.gridView);

        Movies_Grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(contex, "ok ,the details is :  ", Toast.LENGTH_LONG).show();
                Intent details = new Intent(contex, DetailsActivity.class);
                // details.putExtra("title",)

                //  vh=(view_holder)view(id);



      /*        details.putExtra("model_id", model[position].id);
                details.putExtra("model_overview", model[position].overview);
                details.putExtra("model_poster", model[position].poster);
                details.putExtra("model_title", model[position].title);
                details.putExtra("model_vote", model[position].Vote_Avg);
                details.putExtra("model_date", model[position].release_date);

                startActivity(details);*/

               ID= model[position].id+"";
                title=model[position].title;
                poster=model[position].poster;
                over_view=model[position].overview;
                avg=model[position].Vote_Avg;
                release_date=model[position].release_date;
                ((MoviesActivity)getActivity()).data_to_send(ID, poster, over_view, avg, title, release_date);


            }
        });

        try {
            URL API_url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=29ada0c7f50ff3de567515c6198a1748");


            Down_Async_Movies DownLoaded_Movies = new Down_Async_Movies(imageAdapter, Movies_Grid, contex);
            DownLoaded_Movies.execute(API_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        URL API_url;
        //noinspection SimplifiableIfStatement
        if (id == R.id.Most_Popular) {

            try {
                API_url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=29ada0c7f50ff3de567515c6198a1748");


                Down_Async_Movies DownLoaded_Movies = new Down_Async_Movies(imageAdapter, Movies_Grid, contex);
                DownLoaded_Movies.execute(API_url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

           /* TitleAsync mytitleAsync=new TitleAsync(imageAdapter,Movies_Grid,contex);
            mytitleAsync.execute();*/
            return true;
        } else if (id == R.id.Highest_Rate) {
            try {


                API_url = new URL("http://api.themoviedb.org/3/movie/top_rated?api_key=29ada0c7f50ff3de567515c6198a1748");

                Down_Async_Movies DownLoaded_Movies = new Down_Async_Movies(imageAdapter, Movies_Grid, contex);
                DownLoaded_Movies.execute(API_url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            /*Title_Top_Async mytitleAsync=new Title_Top_Async(imageAdapter,Movies_Grid,contex);
            mytitleAsync.execute();*/
            return true;
        } else if (id == R.id.Favorite) {

            String[] titles_str_arr;
            Cursor cursor_favourites = Favourite_movies.fetchMovies();
            int increment = 0;
            model= new MovieDataModel[cursor_favourites.getCount()];
            Poster_paths = new String[cursor_favourites.getCount()];
            cursor_favourites.moveToFirst();
            while (!cursor_favourites.isAfterLast()) {

                model[increment] = new MovieDataModel();

                model[increment].poster = cursor_favourites.getString(cursor_favourites.getColumnIndex("Path"));
                model[increment].title = cursor_favourites.getString(cursor_favourites.getColumnIndex("Title"));
                model[increment].id = cursor_favourites.getInt(cursor_favourites.getColumnIndex("Movie_Id"));
                model[increment].overview = cursor_favourites.getString(cursor_favourites.getColumnIndex("overview"));
                model[increment].release_date = cursor_favourites.getString(cursor_favourites.getColumnIndex("date"));
                model[increment].Vote_Avg = cursor_favourites.getDouble(cursor_favourites.getColumnIndex("rate"));
                Poster_paths[increment] = model[increment].poster;
                cursor_favourites.moveToNext();
//                for(int i=0;i<jsonArrayLength;i++)
//                {
//                    model[i]=new MovieDataModel();
//
//                }0.
                increment++;
            }
//            titles_str_arr = new String[model_favourite.length];
//            for (int increm = 0; increm < model_favourite.length - 1; increm++) {
//                titles_str_arr[increm] = model_favourite[increm].title;
//            }

            ImageAdapter adapter = new ImageAdapter(contex, Poster_paths,null);
            GridView Movies_grid = (GridView) rootView.findViewById(R.id.gridView);
            Movies_grid.setAdapter(adapter);

            ID= model[0].id+"";
            title=model[0].title;
            poster=model[0].poster;
            over_view=model[0].overview;
            avg=model[0].Vote_Avg;
            release_date=model[0].release_date;
            ((MoviesActivity)getActivity()).data_to_send(ID, poster, over_view, avg, title, release_date);
        }


        return super.onOptionsItemSelected(item);
    }

    public class Down_Async_Movies extends AsyncTask<URL, Integer, MovieDataModel[]> {
        ImageAdapter Image_Custom;
        GridView Movies_grid;
        Context main_Context;
        int id;
        double Vote_Avg;
        String release_date;
        String overview = "";
        String[] Poster_paths;


        public Down_Async_Movies(ImageAdapter imageAdapter, GridView Movies_Grid, Context MianContext) {
            Image_Custom = imageAdapter;
            Movies_grid = Movies_Grid;
            main_Context = MianContext;
        }


        @Override
        protected void onPostExecute(MovieDataModel[] movieDataModels) {
            super.onPostExecute(movieDataModels);
            Poster_paths = new String[movieDataModels.length];
            for (int i = 0; i < movieDataModels.length; i++)
                Poster_paths[i] = movieDataModels[i].poster;

            Image_Custom = new ImageAdapter(main_Context, Poster_paths, null);
            Movies_grid.setAdapter(Image_Custom);

            ID= model[0].id+"";
            title=model[0].title;
            poster=model[0].poster;
            over_view=model[0].overview;
            avg=model[0].Vote_Avg;
            release_date=model[0].release_date;
            ((MoviesActivity)getActivity()).data_to_send(ID, poster, over_view, avg, title, release_date);
        }

        @Override
        protected MovieDataModel[] doInBackground(URL... params) {
            HttpURLConnection Url_Connection = null;
            BufferedReader Reader = null;
            URL API_url;
            String MovieJsonStr = null;

            try {

                API_url = params[0];
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
                    String[] poster_pathes = new String[MoviesJsonArray.length()];
                    String[] posterTitles = new String[MoviesJsonArray.length()];
                    String path;
                    String title;
                    JSONObject Movie_obj_i;
                    int jsonArrayLength = MoviesJsonArray.length();
                    model = new MovieDataModel[jsonArrayLength];
                    for (int i = 0; i < jsonArrayLength; i++) {
                        model[i] = new MovieDataModel();
                    }
                    for (int i = 0; i < jsonArrayLength; i++) {
                        Movie_obj_i = MoviesJsonArray.getJSONObject(i);
                        path = Movie_obj_i.getString("poster_path");
                        title = Movie_obj_i.getString("title");
                        id = Movie_obj_i.getInt("id");


                        Vote_Avg = Movie_obj_i.getDouble("vote_average");
                        release_date = Movie_obj_i.getString("release_date");
                        overview = Movie_obj_i.getString("overview");

                        model[i].id = id;
                        model[i].overview = overview;
                        model[i].release_date = release_date;
                        model[i].poster = path;
                        model[i].title = title;
                        model[i].Vote_Avg = Vote_Avg;

                        posterTitles[i] = title;
                        poster_pathes[i] = path;

                    }
                    return model;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
