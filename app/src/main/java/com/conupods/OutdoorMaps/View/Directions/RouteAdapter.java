package com.conupods.OutdoorMaps.View.Directions;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.conupods.R;
import com.google.maps.model.DirectionsStep;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

    private List<DirectionsStep> mAllSteps;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mRouteStep;

        public MyViewHolder(View view) {
            super(view);
            mRouteStep = (TextView) view.findViewById(R.id.RouteStep);
        }
    }

    public RouteAdapter(List<DirectionsStep> routeSteps) {
        this.mAllSteps = routeSteps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DirectionsStep step = mAllSteps.get(position);
        holder.mRouteStep.setText(Html.fromHtml(step.htmlInstructions));
    }

    @Override
    public int getItemCount() {
        return mAllSteps.size();
    }
}
