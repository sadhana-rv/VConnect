package com.example.sadhanaravoori.firebasevconnect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sadhana Ravoori on 02-05-18.
 */

public class RecyclerViewAdapterOrganization extends RecyclerView.Adapter<RecyclerViewAdapterOrganization.MyHolder>{

    Context context;
    List<OrgDetailsToDisplay> list;

    //just to push
    public RecyclerViewAdapterOrganization.OnItemClickedListener mListener;

    public interface OnItemClickedListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RecyclerViewAdapterOrganization.OnItemClickedListener listener){
        mListener=listener;

    }

    public RecyclerViewAdapterOrganization(Context context, List<OrgDetailsToDisplay> TempList) {

        this.list = TempList;

        this.context = context;
    }

    @Override
    public RecyclerViewAdapterOrganization.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_items_organization, parent, false);

        RecyclerViewAdapterOrganization.MyHolder viewHolder = new RecyclerViewAdapterOrganization.MyHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterOrganization.MyHolder holder, int position) {

        OrgDetailsToDisplay orgDetailsToDisplay = list.get(position);
        holder.nameO.setText(orgDetailsToDisplay.getNameOfOrganization());
        holder.descE.setText(orgDetailsToDisplay.getDescription());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        public TextView nameO,descE;

        public MyHolder(View itemView, final RecyclerViewAdapterOrganization.OnItemClickedListener listener) {

            super(itemView);

            nameO = (TextView) itemView.findViewById(R.id.nameOfOrganization);

            descE = (TextView) itemView.findViewById(R.id.description);


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
