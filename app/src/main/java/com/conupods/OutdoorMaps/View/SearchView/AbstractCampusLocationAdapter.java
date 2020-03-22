package com.conupods.OutdoorMaps.View.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.conupods.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AbstractCampusLocationAdapter extends RecyclerView.Adapter<AbstractCampusLocationAdapter.MyViewHolder> {

    List<AbstractCampusLocation> mCampusLocations;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mIdentifierText, mPhysicalParentNameText;

        public MyViewHolder(View view) {
            super(view);
            mIdentifierText = (TextView) view.findViewById(R.id.AbstractLocationName);
            mPhysicalParentNameText = (TextView) view.findViewById(R.id.BuildingName);

        }
    }



    public AbstractCampusLocationAdapter(List<AbstractCampusLocation> listOfCampusLocations){
        mCampusLocations = listOfCampusLocations;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AbstractCampusLocation campusLocation = mCampusLocations.get(position);
        holder.mIdentifierText.setText(campusLocation.getIdentifier());
        holder.mPhysicalParentNameText.setText(campusLocation.getPhysicalParent());
    }

    @Override
    public int getItemCount() {
        return mCampusLocations.size();
    }
}
