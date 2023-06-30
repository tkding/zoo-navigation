package com.example.sdzooseeker_team_64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class PlanExhibitListAdapter extends RecyclerView.Adapter<PlanExhibitListAdapter.ViewHolder> {
    private List<ZooGraph.Exhibit> exhibitList = Collections.emptyList();
    private LinkedHashMap<ZooGraph.Exhibit, Double> distanceMap;

    public void setExhibitList(List<ZooGraph.Exhibit> newExhibitList, LinkedHashMap<ZooGraph.Exhibit, Double> distanceMap) {
        this.exhibitList.clear();
        this.exhibitList = newExhibitList;
        this.distanceMap = distanceMap;

        // Don't show entry/exit gate
        this.exhibitList.remove(0);
        this.exhibitList.remove(this.exhibitList.size()-1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.plan_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZooGraph.Exhibit exhibit = exhibitList.get(position);
        holder.setExhibit(exhibit);
        holder.setExhibitDistance(distanceMap.get(exhibit));
    }

    @Override
    public int getItemCount() {
        return exhibitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView exhibitNameTextView;
        private final TextView exhibitDistanceTextView;
        private ZooGraph.Exhibit exhibit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibitNameTextView = itemView.findViewById(R.id.plan_item_text);
            this.exhibitDistanceTextView = itemView.findViewById(R.id.exhibit_distance_textview);
        }

        public ZooGraph.Exhibit getExhibit() { return exhibit; }

        public void setExhibit(ZooGraph.Exhibit exhibit) {
            this.exhibit = exhibit;
            this.exhibitNameTextView.setText(exhibit.name);
        }

        public void setExhibitDistance(Double distance) {
            this.exhibitDistanceTextView.setText(Double.toString(distance) + " ft");
        }
    }

}
