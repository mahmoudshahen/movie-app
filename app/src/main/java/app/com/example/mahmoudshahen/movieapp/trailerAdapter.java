package app.com.example.mahmoudshahen.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmoud shahen on 8/13/2016.
 */
public class trailerAdapter extends ArrayAdapter<trailersData> {

    List<trailersData> list1 ;
    Context context;
    TextView txt;

    View itemLayoutView = null;

    public trailerAdapter(Context context, List<trailersData> objects) {
        super(context, -1, objects);
        this.list1= new ArrayList<trailersData>();
        this.list1 = objects;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            itemLayoutView = LayoutInflater.from(getContext()).inflate(R.layout.trailer, parent);

        txt = (TextView) itemLayoutView.findViewById(R.id.oneTrailer);
        txt.setText(list1.get(position).name);
        txt.setHeight(70);

        return itemLayoutView;
    }


}
