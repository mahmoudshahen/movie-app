package app.com.example.mahmoudshahen.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmoud shahen on 8/9/2016.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private List<dataItem> items = new ArrayList<dataItem>();
    private AppCompatActivity context;
    Intent intent;
    public myAdapter(List<dataItem> items, AppCompatActivity context)
    {
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

       //Toast.makeText(context,position,Toast.LENGTH_SHORT).show();
       // Log.v("xxxxxxxxxxxxxx",items.get(position).image);
        if(items.size()>0) {
            Picasso.with(context).load(items.get(position).image)
                    .error(R.drawable.notification_error)
                    .placeholder(R.drawable.loading)
                    .into(holder.img);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.mTwoPane &&(DetailsFragment.temperoryObject==null||
                            items.get(position).id != DetailsFragment.temperoryObject.id)) {
                        Bundle arguments = new Bundle();
                        arguments.putInt("pos", position);

                        MainActivity.fragment = new DetailsFragment();
                        MainActivity.fragment.setArguments(arguments);
                        context.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_fragment, MainActivity.fragment)
                                .commit();
                    } else if(!MainActivity.mTwoPane){

                        intent = new Intent(context, Details.class).putExtra(Intent.EXTRA_TEXT, String.valueOf(position));
                        context.startActivity(intent);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imagee);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}