package com.example.mohamed.moviesappllication;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MoviesActivity extends AppCompatActivity {
    boolean check_tablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        FrameLayout tablet_framelayout = (FrameLayout) findViewById(R.id.fragment2_container);
        if (null == tablet_framelayout) {
            check_tablet = false;
        } else {
            check_tablet = true;
        }
        if (check_tablet) {
            getFragmentManager().beginTransaction().replace(R.id.fragment2_container,new MoviesFragment()).commit();
        }
        else
        {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,new MoviesFragment()).commit();
        }

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public void data_to_send(String ID,String poster,String over_view,Double vote,String title,String date ) {
        //Case Two Pane UI
        if (check_tablet) {
            DetailsFragment detailsFragment= new DetailsFragment();
            Bundle details_bundle= new Bundle();
            details_bundle.putString("ID", ID);
            details_bundle.putString("poster", poster);
            details_bundle.putString("over_view", over_view);
            details_bundle.putString("vote", vote+"");
            details_bundle.putString("title", title);
            details_bundle.putString("date", date);
            detailsFragment.setArguments(details_bundle);
            getFragmentManager().beginTransaction().replace(R.id.fragment_details_container, detailsFragment).commit();

        } else {
            //Case Single Pane UI
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra("ID", ID);
            i.putExtra("poster", poster);
            i.putExtra("over_view", over_view);
            i.putExtra("vote", vote + "");
            i.putExtra("title", title);
            i.putExtra("date", date);
            startActivity(i);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
