package com.example.khasol.jobflow;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Khasol on 7/29/2016.
 */
public class SavedJob_Adapter extends RecyclerView.Adapter<SavedJob_Adapter.MyViewHolder> {

    private List<CustomeDataAdapter> itemlist;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, job_type,stockholm, days;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.job_title);
            job_type = (TextView) view.findViewById(R.id.job_type);
            days = (TextView) view.findViewById(R.id.days);
            stockholm = (TextView) view.findViewById(R.id.location);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i("click",""+position);
        }
    }


    public SavedJob_Adapter(List<CustomeDataAdapter> itemlist){
        this.itemlist = itemlist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customize_savejobs, parent, false);

        return new MyViewHolder(itemView);
    }
    public void onBindViewHolder(MyViewHolder holder,int position){

        CustomeDataAdapter customeDataAdapter = itemlist.get(position);
        holder.title.setText(customeDataAdapter.getTitle());
        holder.job_type.setText(customeDataAdapter.getType());
        holder.stockholm.setText(customeDataAdapter.getLocation());
        holder.days.setText(customeDataAdapter.getDays());
    }
    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    @Override
    public long getItemId(int position) {
        Log.i("click",""+position);
        return position;
    }
}
