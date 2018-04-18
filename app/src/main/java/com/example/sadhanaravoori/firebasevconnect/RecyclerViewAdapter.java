package com.example.sadhanaravoori.firebasevconnect;

/**
 * Created by Sadhana Ravoori on 14-04-18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>{

    Context context;
    List<EventDetails> list;

    public OnItemClickedListener mListener;

    public interface OnItemClickedListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener){
        mListener=listener;

    }

    public RecyclerViewAdapter(Context context, List<EventDetails> TempList) {

        this.list = TempList;

        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_items, parent, false);

        MyHolder viewHolder = new MyHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        EventDetails eventDetails = list.get(position);
        holder.nameE.setText(eventDetails.getNameOfEvent());
        holder.nameO.setText(eventDetails.getNameOfOrganization());
        holder.dateE.setText(eventDetails.getDate());
        holder.descE.setText(eventDetails.getDescriptionOfEvent());
        holder.timeE.setText(eventDetails.getTime());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        public TextView nameO,nameE, descE, dateE, timeE;

        public MyHolder(View itemView, final OnItemClickedListener listener) {

            super(itemView);

            nameO = (TextView) itemView.findViewById(R.id.nameOfOrganization);
            nameE = (TextView) itemView.findViewById(R.id.nameOfEvent);
            descE = (TextView) itemView.findViewById(R.id.descriptionOfEvent);
            dateE = (TextView) itemView.findViewById(R.id.date);
            timeE = (TextView) itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
