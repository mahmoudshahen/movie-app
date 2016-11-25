package app.com.example.mahmoudshahen.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Details extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.name_of_movie);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // DetailsFragment.temperoryObject = MainActivityFragment.items.get(DetailsFragment.pos);


    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v("fra", "2");
    }
}