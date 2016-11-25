package app.com.example.mahmoudshahen.movieapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    RecyclerView recyclerView;
    public static List<dataItem> items;
    public static myAdapter adapter;
    public dataItem temp;
    public static boolean getData = true;
    public static TextView nofav;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        getData=true;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nofav = (TextView)rootView.findViewById(R.id.nofav);

            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        items = new ArrayList<dataItem>();
        Log.v("mainfragment", "main");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getData  ||(MainActivity.sort.equals("favourite")&&!MainActivity.mTwoPane))
            get(getActivity());
        getData = true;

        if(MainActivity.sort.equals("favourite") && items.size()==0)
            nofav.setText("you have no favourite");
        else
        nofav.setText("");


        Log.v("mainfragment", String.valueOf(DetailsFragment.pos)+","+items.size());

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            DetailsFragment.refreshFragment = false;
            startActivity(new Intent(getActivity(), Setting.class));
            return true;
        }
        if(id == R.id.refresh)
        {
            get(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void get(final Context context) {

        if(!MainActivity.sort.equals("favourite")) {
            try {

                Ion.with(context)
                        .load("http://api.themoviedb.org/3/movie/" + MainActivity.sort + "?api_key=c1d0d4700ff7be419e1741c8d5894f04")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                if (result != null && result.has("results")) {
                                    JsonArray ja = result.get("results").getAsJsonArray();
                                    items.clear();
                                    for (int i = 0; i < ja.size(); i++) {

                                        temp = new dataItem(ja.get(i).getAsJsonObject().get("id").toString(),
                                                ja.get(i).getAsJsonObject().get("poster_path").toString(),
                                                ja.get(i).getAsJsonObject().get("backdrop_path").toString(),
                                                ja.get(i).getAsJsonObject().get("overview").toString(),
                                                ja.get(i).getAsJsonObject().get("original_title").toString(),
                                                ja.get(i).getAsJsonObject().get("vote_average").toString(),
                                                ja.get(i).getAsJsonObject().get("release_date").toString()
                                        );
                                        items.add(temp);
                                    }
                                    adapter = new myAdapter(items, (AppCompatActivity) getContext());
                                    recyclerView.setAdapter(adapter);
                                    if(MainActivity.mTwoPane){
                                    Bundle arguments = new Bundle();
                                    arguments.putInt("pos", DetailsFragment.pos);
                                    MainActivity.fragment = new DetailsFragment();
                                    MainActivity.fragment.setArguments(arguments);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.detail_fragment, MainActivity.fragment)
                                            .commit();}
                                }
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
        try
            {
               //cursor;
                Cursor cursor = context.getContentResolver().query(FavouriteContract.favouriteTable.CONTENT_URI, null, null, null, null);
                items.clear();
                if (cursor != null) {
                    cursor.moveToFirst();
                    do {
                        dataItem tem = new dataItem();
                        tem.setFromDataBase(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8));
                        items.add(tem);
                    }
                    while (cursor.moveToNext());
                }
                adapter = new myAdapter(items, (AppCompatActivity) getActivity());
                recyclerView.setAdapter(adapter);
                if(MainActivity.mTwoPane && items.size()>0){
                    Bundle arguments = new Bundle();
                    if(DetailsFragment.pos == items.size())
                        arguments.putInt("pos", 0);
                    else
                    arguments.putInt("pos", DetailsFragment.pos);
                    MainActivity.fragment = new DetailsFragment();
                    MainActivity.fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_fragment, MainActivity.fragment)
                            .commit();}
            }catch (Exception e){
                Toast.makeText(context, "Database error", Toast.LENGTH_SHORT).show();
            }

        }


    }
}