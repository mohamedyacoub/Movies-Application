package com.example.mohamed.moviesappllication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {




    String overView;
    String poster;
    String title;
    double vote;
    String date;
    DetailDataModel[] model;
    DetailDataModel[] model_reviews;
    int id;
    TextView titletxt;
Intent i=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
/*
        id = getIntent().getExtras().getInt("model_id");
        overView = getIntent().getExtras().getString("model_overview");
        poster = getIntent().getExtras().getString("model_poster");
        title = getIntent().getExtras().getString("model_title");
        vote = getIntent().getExtras().getDouble("model_vote");
        date = getIntent().getExtras().getString("model_date");


        getFragmentManager().beginTransaction().add(R.id.fragment_details_container,new DetailsFragment()).commit();*/



        Bundle details_bundle = getIntent().getExtras();
        if (null == savedInstanceState) {
            DetailsFragment new_details_fragment = new DetailsFragment();
            //Pass the "extras" Bundle that contains the selected "name" to the fragment
            new_details_fragment.setArguments(details_bundle);
            getFragmentManager().beginTransaction().add(R.id.fragment_details_container, new_details_fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
