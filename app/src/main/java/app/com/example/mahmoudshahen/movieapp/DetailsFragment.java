package app.com.example.mahmoudshahen.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    static int pos = 0;
    ImageView pic_of_movie;
    ImageView background;
    TextView originalTitle;
    TextView rating;
    TextView releaseDate;
    TextView overView;
    TextView runTime;
    public Button imdb;

    List<String> reviews;

    RecyclerView review;
    trailerAdapter TA;
    ListView trailer;
    List<trailersData> trailers;
    public static boolean fav;
    public static boolean refreshFragment = false;

    public static FloatingActionButton fab;
    public static dataItem temperoryObject = new dataItem();

    //public static CollapsingToolbarLayout collapsingToolbarLayout;
    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("sss","deleted");
        if(MainActivity.mTwoPane && getArguments().containsKey("pos"))
        {
            pos = getArguments().getInt("pos");
            Log.v("poss1", String.valueOf(pos));
        }
        else
        {
            Intent intent = getActivity().getIntent();
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT))
            {
                pos = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));
                Log.v("poss", String.valueOf(pos));
            }
        }

        if(savedInstanceState!=null)
        {
            fav = savedInstanceState.getBoolean("star");
        }
        Log.v("size", String.valueOf(MainActivityFragment.items.size()));
        Log.v("pos", String.valueOf(pos));
        if(MainActivityFragment.items.size()> pos)
            temperoryObject = MainActivityFragment.items.get(pos);
        else
        temperoryObject = null;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("star", fav);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(trailer == null)
            Log.v("xxx", "resume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_details, container, false);

        pic_of_movie = (ImageView)rootView.findViewById(R.id.pic_of_movie);
        originalTitle = (TextView)rootView.findViewById(R.id.name_of_movie);
        //collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing);
        //collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        rating = (TextView)rootView.findViewById(R.id.rate);
        releaseDate = (TextView)rootView.findViewById(R.id.release_date);
        overView = (TextView)rootView.findViewById(R.id.over_view);
        background = (ImageView)rootView.findViewById(R.id.backGround);
        runTime = (TextView)rootView.findViewById(R.id.runTime);
        imdb = (Button)rootView.findViewById(R.id.imdb);

        reviews = new ArrayList<String>();
        review = (RecyclerView) rootView.findViewById(R.id.reviews);

        review.setLayoutManager(new LinearLayoutManager(getActivity()));

        review.setItemAnimator(new DefaultItemAnimator());
        trailers = new ArrayList<trailersData>();
        trailer = (ListView)rootView.findViewById(R.id.trailerList);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        if(temperoryObject!=null &&getActivity().getContentResolver().query(
                FavouriteContract.favouriteTable.CONTENT_URI_ID, null, temperoryObject.id, null, null) != null)
            fav = true;
        else
            fav = false;
        assert fab != null;
        if(fav)
        {
            fab.setImageResource((R.drawable.fullstarr));
        }
        else {
            fab.setImageResource((R.drawable.emptystarr));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fav) {
                    fab.setImageResource((R.drawable.fullstarr));
                    Toast.makeText(getActivity(), "added to favourite", Toast.LENGTH_SHORT).show();
                    if (MainActivity.mTwoPane && MainActivity.sort.equals("favourite"))
                        MainActivityFragment.items.add(temperoryObject);
                    if (temperoryObject != null && getContext().getContentResolver().query(
                            FavouriteContract.favouriteTable.CONTENT_URI_ID, null, temperoryObject.id, null, null) == null) {
                        ContentValues values = new ContentValues();
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_ID, temperoryObject.id);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_ORIGINALTITLE, temperoryObject.originalTitle);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_OVERVIEW, temperoryObject.details);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RELEASEDATE, temperoryObject.releaseDate);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RUNTIME, temperoryObject.runtime);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_RATING, temperoryObject.rating);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_IMAGE, temperoryObject.image);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_BACKGROUND, temperoryObject.backGround);
                        values.put(FavouriteContract.favouriteTable.COLUMN_NAME_IMDB, temperoryObject.imdb);
                        Uri uri = getContext().getContentResolver().insert(FavouriteContract.favouriteTable.CONTENT_URI, values);
                    }
                    fav = true;
                } else {
                    fab.setImageResource((R.drawable.emptystarr));
                    Toast.makeText(getActivity(), "removed from favourite", Toast.LENGTH_SHORT).show();
                    if (MainActivity.mTwoPane && MainActivity.sort.equals("favourite"))
                        MainActivityFragment.items.remove(temperoryObject);
                    if (temperoryObject != null && getContext().getContentResolver().query(
                            FavouriteContract.favouriteTable.CONTENT_URI_ID, null, temperoryObject.id, null, null) != null)
                        getContext().getContentResolver().delete(FavouriteContract.favouriteTable.CONTENT_URI, temperoryObject.id, null);
                    fav = false;
                }
                if (MainActivity.sort.equals("favourite") && MainActivityFragment.items.size() == 0)
                    MainActivityFragment.nofav.setText("you have no favourite");
                else
                    MainActivityFragment.nofav.setText("");
                MainActivityFragment.adapter.notifyDataSetChanged();
            }

        });
        if(temperoryObject!=null)
            set();

            imdb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(temperoryObject.imdb));
                        refreshFragment = false;
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        Log.v("ssss", "onCreate");

        trailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailers.get(position).link));
                startActivity(browserIntent);
            }
        });

        Log.v("datailfragment", "Detail");

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("fra", String.valueOf(refreshFragment));

        if(refreshFragment)
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        refreshFragment = true;
        MainActivityFragment.getData = false;
    }
    public void set(){

        try{
            Ion.with(getActivity())
                    .load("http://api.themoviedb.org/3/movie/" + temperoryObject.id + "?api_key=c1d0d4700ff7be419e1741c8d5894f04")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (result != null && result.has("overview")) {
                                temperoryObject.setIMDb(result.get("imdb_id").toString());
                                temperoryObject.setRunTime(result.get("runtime").toString());
                                runTime.setText(temperoryObject.runtime);
                            }
                        }

                    });
        }

        catch (Exception e)
        {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }



     try {
         Picasso.with(getActivity()).load(temperoryObject.image)
                 .error(R.drawable.notification_error)
                 .placeholder(R.drawable.loading)
                 .into(pic_of_movie);
         Picasso.with(getActivity()).load(temperoryObject.backGround)
                 .error(R.drawable.notification_error)
                 .placeholder(R.drawable.loading)
                 .into(background);
         originalTitle.setText(temperoryObject.originalTitle);
         //collapsingToolbarLayout.setTitle(temperoryObject.originalTitle);
         rating.setText(temperoryObject.rating);
         releaseDate.setText(temperoryObject.releaseDate);
         overView.setText(temperoryObject.details);
         runTime.setText(temperoryObject.runtime);
     }
     catch (Exception e)
     {
         Toast.makeText(getActivity(), "error start activity", Toast.LENGTH_SHORT).show();
     }
        try {
            Ion.with(this)
                    .load("http://api.themoviedb.org/3/movie/" + DetailsFragment.temperoryObject.id + "/videos?api_key=c1d0d4700ff7be419e1741c8d5894f04")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (result != null && result.has("results")) {
                                JsonArray ja = result.get("results").getAsJsonArray();
                                for (int i = 0; i < ja.size(); i++) {
                                    trailers.add(new trailersData(ja.get(i).getAsJsonObject().get("name").toString(),
                                            ja.get(i).getAsJsonObject().get("key").toString()));
                                }
                            }
                            TA = new trailerAdapter(getContext(), trailers);
                            trailer.setAdapter(TA);
                            setListViewHeightBasedOnChildren(trailer);
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
        }
        try {
            Ion.with(this)
                    .load("http://api.themoviedb.org/3/movie/" + DetailsFragment.temperoryObject.id + "/reviews?api_key=c1d0d4700ff7be419e1741c8d5894f04")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (result != null && result.has("results")) {
                                JsonArray ja = result.get("results").getAsJsonArray();
                                for (int i = 0; i < ja.size(); i++) {
                                    reviews.add(ja.get(i).getAsJsonObject().get("content").toString());
                                }
                            }
                            ReviewAdapter reviewAdapter = new ReviewAdapter(reviews,  getContext());
                            review.setAdapter(reviewAdapter);
                        }
                    });
                    }

        catch (Exception e)
        {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}