package com.conupods.OutdoorMaps.View.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.conupods.OutdoorMaps.Models.Building.AbstractCampusLocation;
import com.conupods.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AbstractCampusLocationAdapter extends RecyclerView.Adapter<AbstractCampusLocationAdapter.MyViewHolder> implements Filterable {

    private List<AbstractCampusLocation> mCampusLocations;
    private List<AbstractCampusLocation> mFilteredCampusLocationsList;
    private CampusLocationsAdapterListener mCampusLocationsAdapterListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mIdentifierText;
        public TextView mPhysicalParentNameText;

        public MyViewHolder(View view) {
            super(view);
            mIdentifierText = (TextView) view.findViewById(R.id.AbstractLocationName);
            mPhysicalParentNameText = (TextView) view.findViewById(R.id.BuildingName);

            view.setOnClickListener(view1 -> mCampusLocationsAdapterListener.onCampusLocationSelected(mFilteredCampusLocationsList.get(getAdapterPosition())));

        }
    }


    public AbstractCampusLocationAdapter(List<AbstractCampusLocation> listOfCampusLocations, CampusLocationsAdapterListener campusLocationsAdapterListener) {
        mCampusLocationsAdapterListener = campusLocationsAdapterListener;
        mCampusLocations = listOfCampusLocations;
        mFilteredCampusLocationsList = listOfCampusLocations;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AbstractCampusLocation campusLocation = mFilteredCampusLocationsList.get(position);
        if (campusLocation.getmLongIdentifier() != null) {
            holder.mIdentifierText.setText(campusLocation.getIdentifier() + "  " + campusLocation.getmLongIdentifier());
        } else {
            holder.mIdentifierText.setText(campusLocation.getIdentifier());
        }
        holder.mPhysicalParentNameText.setText(campusLocation.getConcreteParent());

    }


    @Override
    public int getItemCount() {
        return mFilteredCampusLocationsList.size();
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
                        if (row.getmLongIdentifier() != null) {
                            if (row.getIdentifier().toLowerCase().contains(charString.toLowerCase()) || row.getmLongIdentifier().toLowerCase().contains(charString.toLowerCase())) {
                                constructedListOfResults.add(row);
                            }
                        } else {
                            if (row.getIdentifier().toLowerCase().contains(charString.toLowerCase())) {
                                constructedListOfResults.add(row);

                            }
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
