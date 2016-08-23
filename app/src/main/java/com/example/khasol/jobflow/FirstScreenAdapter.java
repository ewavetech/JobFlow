package com.example.khasol.jobflow;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Khasol on 7/29/2016.
 */
public class FirstScreenAdapter extends RecyclerView.Adapter<FirstScreenAdapter.MyViewHolder> {

    private List<CustomeDataAdapter_Firstacreen> itemlist;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView first_text;
        LinearLayout background;

        public MyViewHolder(View view) {
            super(view);
            first_text = (TextView) view.findViewById(R.id.first_text);
            background = (LinearLayout) view.findViewById(R.id.layout_img);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i("click", "" + position);
        }
    }


    public FirstScreenAdapter(List<CustomeDataAdapter_Firstacreen> itemlist) {
        this.itemlist = itemlist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custome_firstscreen, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {

        CustomeDataAdapter_Firstacreen customeDataAdapter = itemlist.get(position);
        holder.first_text.setText(customeDataAdapter.getTitle());
        holder.background.setBackgroundResource(customeDataAdapter.getimageId());

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    @Override
    public long getItemId(int position) {
        Log.i("click", "" + position);
        return position;
    }
}
