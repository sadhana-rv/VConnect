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

public class RegVolunteerViewed extends RecyclerView.Adapter<RegVolunteerViewed.MyHolder>{

    Context context;
    List<StoredVolunteerInfo> list;

    //just to push
    public OnItemClickedListener mListener;

    public interface OnItemClickedListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener){
        mListener=listener;

    }

    public RegVolunteerViewed(Context context, List<StoredVolunteerInfo> TempList) {

        Log.e("regVol","enters");

        this.list = TempList;

        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.reg_volunteer_viewed_items, parent, false);

        MyHolder viewHolder = new MyHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        StoredVolunteerInfo obj = list.get(position);
        Log.e("FromReg",obj.getName()+" "+obj.getEmail());
        holder.name.setText(obj.getName());
        holder.email.setText(obj.getEmail());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        public TextView name,email;

        public MyHolder(View itemView, final OnItemClickedListener listener) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);

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
