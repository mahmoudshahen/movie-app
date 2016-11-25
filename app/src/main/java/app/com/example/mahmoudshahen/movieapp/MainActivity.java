package app.com.example.mahmoudshahen.movieapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static boolean mTwoPane;
    public static String sort = "popular";
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static DetailsFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTwoPane = false;

        SharedPreferences shared = getSharedPreferences("data", MODE_PRIVATE);
        MainActivity.sort = shared.getString("store", "popular");

        if (findViewById(R.id.detail_fragment) != null){
            Log.v("tablet landscape", "true");
            //Toast.makeText(this, "TWO PANE", Toast.LENGTH_SHORT).show();
            mTwoPane = true;
            /*Bundle arguments = new Bundle();
            arguments.putInt("pos", 0);
            fragment = new DetailsFragment();
            MainActivity.fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, MainActivity.fragment)
                    .commit();*/
        }
        else
        {
            //Toast.makeText(this, "ONE PANE", Toast.LENGTH_SHORT).show();

            mTwoPane = false;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        pref = getSharedPreferences("data", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("store", MainActivity.sort);
        editor.commit();
    }
}