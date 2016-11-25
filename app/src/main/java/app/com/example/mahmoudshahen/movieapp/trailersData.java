package app.com.example.mahmoudshahen.movieapp;

import android.util.Log;

/**
 * Created by mahmoud shahen on 8/13/2016.
 */
public class trailersData {
    public String name;
    public String link;

    public trailersData(String name, String link)
    {
        this.name = name;
        this.link = link;
        clean();
    }
    private void clean()
    {
        link = link.substring(1, link.length()-1);
        Log.v("ooo", link);
        link = "https://www.youtube.com/watch?v=" + link;
        name = name.substring(1, name.length()-1);
    }
}
