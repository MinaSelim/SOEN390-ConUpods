package com.conupods.OutdoorMaps.View.SearchView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.conupods.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AbstractCampusLocationAdapter extends RecyclerView.Adapter<AbstractCampusLocationAdapter.MyViewHolder> implements Filterable {

    private List<AbstractCampusLocation> mCampusLocations;
    private List<AbstractCampusLocation> mFilteredCampusLocationsList;
    private Context mCurrentContext;
    private CampusLocationsAdapterListener mCampusLocationsAdapterListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mIdentifierText, mPhysicalParentNameText;

        public MyViewHolder(View view) {
            super(view);
            mIdentifierText = (TextView) view.findViewById(R.id.AbstractLocationName);
            mPhysicalParentNameText = (TextView) view.findViewById(R.id.BuildingName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mCampusLocationsAdapterListener.onContactSelected(mFilteredCampusLocationsList.get(getAdapterPosition()));
                }
            });

        }
    }



    public AbstractCampusLocationAdapter(Context context, List<AbstractCampusLocation> listOfCampusLocations, CampusLocationsAdapterListener campusLocationsAdapterListener){
        mCampusLocations = listOfCampusLocations;
        mCurrentContext = context;
        mFilteredCampusLocationsList =  listOfCampusLocations;
        mFilteredCampusLocationsList.clear();
        mCampusLocationsAdapterListener = campusLocationsAdapterListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AbstractCampusLocation campusLocation;

        if(mFilteredCampusLocationsList != null || !mFilteredCampusLocationsList.isEmpty()){
            campusLocation = mFilteredCampusLocationsList.get(position);
            holder.mIdentifierText.setText(campusLocation.getIdentifier());
            holder.mPhysicalParentNameText.setText(campusLocation.getPhysicalParent());
        }

    }

    @Override
    public int getItemCount() {
        return mCampusLocations.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredCampusLocationsList = new ArrayList<>();
                } else {
                    List<AbstractCampusLocation> constructedListOfResults = new ArrayList<>();
                    for (AbstractCampusLocation row : mCampusLocations) {

                        if (row.getIdentifier().toLowerCase().contains(charString.toLowerCase())) {
                            constructedListOfResults.add(row);
                        }
                    }

                    mFilteredCampusLocationsList = constructedListOfResults;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredCampusLocationsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredCampusLocationsList = (ArrayList<AbstractCampusLocation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
