package com.example.sdzooseeker_team_64;

import android.util.Pair;
import android.widget.Toast;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ZooPlan implements Serializable {
    public List<ZooGraph.Exhibit> exhibits;
    private LinkedHashMap<ZooGraph.Exhibit, Double> exhibitDistanceMap; // Don't use it other than in plan activity
    public ZooGraph zooGraph;
    private int currentStartExhibitIndex;
    private int currentEndExhibitIndex;
    double Degree_latitude_in_ft = 363843.57;
    double Degree_longitude_in_ft = 307515.50;
    double Base = 100;

    private ZooGraph.Exhibit getEntranceExitGate() { return zooGraph.getExhibitWithId("entrance_exit_gate"); }

    @Override
    public String toString() {
        return "ZooPlan{" +
                "exhibits=" + exhibits +
                '}';
    }

    public ZooPlan(ZooGraph zooGraph, List<ZooGraph.Exhibit> exhibits) {
        this.zooGraph = zooGraph;
        this.exhibits = exhibits;

        sortAllExhibits();

        addGateToStartAndBack();

        // Initialize instance variables
        currentStartExhibitIndex = 0;
        currentEndExhibitIndex = 1;
    }

    public ZooPlan(ZooGraph zooGraph, ZooGraph.Exhibit[] exhibits) {
        List<ZooGraph.Exhibit> exhibitsAsList = Arrays.asList(exhibits);
        new ZooPlan(zooGraph, exhibitsAsList);
    }

    public ZooGraph.Exhibit getCurrentStartExhibit() {
        return exhibits.get(currentStartExhibitIndex);
    }

    public ZooGraph.Exhibit getCurrentEndExhibit() {
        return exhibits.get(currentEndExhibitIndex);
    }

    public int getCurrentStartIndex() {
        return currentStartExhibitIndex;
    }

    public int getCurrentEndIndex() {
        return currentEndExhibitIndex;
    }

    public void setStartIndex(int index) { this.currentStartExhibitIndex = index; }

    public void setEndIndex(int index) { this.currentEndExhibitIndex = index; }

    public void addGateToStartAndBack() {
        // add entrance_exit_gate to start and back
        exhibits.add(0, getEntranceExitGate());
        exhibits.add(exhibits.size(), getEntranceExitGate());
    }

    public boolean addExhibit(ZooGraph.Exhibit exhibit) {
        boolean isSuccessful = exhibits.add(exhibit);
        // sort all exhibits
        sortAllExhibits();
        return isSuccessful;
    }

    public void skipThisExhibit(double userLat, double userLng) {
        // remove the currentEndExhibit and re-plan the ones after it
        deleteListData(exhibits.size() - 2);
        exhibits.remove(currentEndExhibitIndex);
        saveList(exhibits);
        // If going forward on plan, don't change the star/end index because exhibits shift forward after removal
        // If going backward, decrement both start/end index so that start exhibit is the same, end exhibit goes backward by one
        if(!goingForward()) {
            currentStartExhibitIndex--;
            currentEndExhibitIndex--;
            saveIndex(currentStartExhibitIndex, currentEndExhibitIndex);
        }

        // Replan the following exhibits according to user location
        // Don't sort first and last one as they are the entry/exit gate
        // check which way to replan
        if(goingForward()) {
            // avoid last gate
            replanExhibitsWithUserLocation(userLat, userLng, currentEndExhibitIndex, exhibits.size()-2);
        } else {
            // avoid first gate
            replanExhibitsWithUserLocation(userLat, userLng, 1, currentEndExhibitIndex);
        }

    }

    private GraphPath<String, IdentifiedWeightedEdge> findPathBetween(ZooGraph.Exhibit start, ZooGraph.Exhibit end) {
        // check which id to use for both exhibits
        boolean useGroupIdForStart = (start.groupId != null);
        boolean useGroupIdForEnd = (end.groupId != null);

        GraphPath<String, IdentifiedWeightedEdge> path;

        path = DijkstraShortestPath.
                findPathBetween(zooGraph.graph,
                        useGroupIdForStart ? start.groupId : start.id,
                        useGroupIdForEnd ? end.groupId : end.id);
        return path;
    }

    // Both fromIndex and toIndex are inclusive!
    public void replanExhibitsWithUserLocation(double userLat, double userLng, int fromIndex, int toIndex) {
        if(fromIndex == toIndex || fromIndex == 0) { return; }

        // get all location for all exhibits within range
        // Notice exhibits with group_id don't have lat/lng
        Map<ZooGraph.Exhibit, Double> exhibitsToSort = new HashMap<>(); // key value is lat/lng diff
        for(int i = fromIndex; i <= toIndex; i++) {
            // Get direct distance in lat/lng
            ZooGraph.Exhibit exhibit = exhibits.get(i);
            // get lat/lng of exhibit
            boolean useGroupLocation = (exhibit.groupId != null);
            double exhibitLat = useGroupLocation ?
                    zooGraph.getExhibitWithId(exhibit.groupId).lat : exhibit.lat;
            double exhibitLng = useGroupLocation ?
                    zooGraph.getExhibitWithId(exhibit.groupId).lng : exhibit.lng;
            // calculate location diff
            double d_lat = Math.abs(userLat - exhibitLat);
            double d_lng = Math.abs(userLng - exhibitLng);
            double d_ft_v = d_lat * Degree_latitude_in_ft;
            double d_ft_h = d_lng * Degree_longitude_in_ft;
            double d_ft = Math.sqrt(Math.pow(d_ft_h, 2) + Math.pow(d_ft_v, 2));
            double locationDiff = Base * Math.ceil(d_ft / Base);
            exhibitsToSort.put(exhibit, locationDiff);
        }

        // sort exhibits
        List<ZooGraph.Exhibit> sortedExhibits = new ArrayList<>();
        exhibitsToSort.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedExhibits.add(x.getKey()));

        // apply exhibits order
        // remove unsorted exhibits
        int numOfExhibitsToRemove = toIndex - fromIndex + 1;
        for(int i = 0; i < numOfExhibitsToRemove; i++) {
            exhibits.remove(fromIndex);
        }
        // add sorted exhibits back
        if(goingForward()) {
            for(int i = fromIndex; i <= toIndex; i++) {
                // add sequentially, before last gate
                exhibits.add(exhibits.size() - 1, sortedExhibits.get(i - fromIndex));
            }
        } else {
            Collections.reverse(sortedExhibits);
            for(int i = fromIndex; i <= toIndex; i++) {
                exhibits.add(i, sortedExhibits.get(i - fromIndex));
            }
        }

    }

    private void sortAllExhibits() {
        Map<ZooGraph.Exhibit, Double> unsorted = new HashMap<>();
        ZooGraph.Exhibit gate = zooGraph.getExhibitWithId("entrance_exit_gate");
        for(ZooGraph.Exhibit exhibit : exhibits) {
            // find complete shortest path from gate to exhibit
            System.out.println(exhibit.id);
            // check if exhibit belongs to a group
            GraphPath<String, IdentifiedWeightedEdge> path;
            path = findPathBetween(gate, exhibit);

            // calculate total distance from gate to exhibit
            double distance = 0;
            for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                distance += zooGraph.graph.getEdgeWeight(e);
            }
            unsorted.put(exhibit, distance);
        }

        // update exhibits to sorted order
        ArrayList<ZooGraph.Exhibit> sortedExhibits = new ArrayList<>();
        LinkedHashMap<ZooGraph.Exhibit, Double> sortedExhibitsInLinkedHashMap = new LinkedHashMap<>();
        unsorted.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered( x -> {
                    sortedExhibits.add(x.getKey());
                    sortedExhibitsInLinkedHashMap.put(x.getKey(), x.getValue());
                });
        exhibits = sortedExhibits;
        exhibitDistanceMap = sortedExhibitsInLinkedHashMap;
    }

    public boolean goToNextExhibit() {
        // Check if it is possible to go to next exhibit
        if(canGoNext()) {
            // if user is going forward in plan, increment start/end exhibit indices,
            // otherwise, reverse start/end exhibit indices
            if(goingForward()) {
                currentStartExhibitIndex++;
                currentEndExhibitIndex++;
                saveIndex(currentStartExhibitIndex, currentEndExhibitIndex);
            } else {
                int tmp = currentStartExhibitIndex;
                currentStartExhibitIndex = currentEndExhibitIndex;
                currentEndExhibitIndex = tmp;
                saveIndex(currentStartExhibitIndex, currentEndExhibitIndex);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean goToPrevExhibit() {
        // Check if it is possible to go to previous exhibit
        if(canGoPrev()) {
            // if user is going forward in plan, reverse start/end exhibit indices,
            // otherwise, decrement start/end exhibit indices
            if(goingForward()) {
                int tmp = currentStartExhibitIndex;
                currentStartExhibitIndex = currentEndExhibitIndex;
                currentEndExhibitIndex = tmp;
                saveIndex(currentStartExhibitIndex, currentEndExhibitIndex);
            } else {
                currentStartExhibitIndex--;
                currentEndExhibitIndex--;
                saveIndex(currentStartExhibitIndex, currentEndExhibitIndex);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean goingForward() {
        return (currentEndExhibitIndex > currentStartExhibitIndex);
    }

    public boolean canGoNext() {
        // For it to be possible to go next, the user either is going backward on the plan,
        // or going forward and the current destination is not the last one.
        return (!goingForward()) | (currentEndExhibitIndex != exhibits.size()-1);
    }

    public boolean canGoPrev() {
        // For it to be possible to go prev, the user either is going forward on the plan,
        // or going backward and the current destination is not the first exhibit.
        return goingForward() | (currentEndExhibitIndex != 0);
    }

    public boolean canSkip() {
        return exhibits.size() > 3 &&
                !exhibits.get(currentEndExhibitIndex).equals(getEntranceExitGate());
    }

    public ArrayList<String> getCurrentDetailedPath() {
        ArrayList<String> path = new ArrayList<>();

        GraphPath<String, IdentifiedWeightedEdge> graphPath =
                findPathBetween(exhibits.get(currentStartExhibitIndex),
                        exhibits.get(currentEndExhibitIndex));

        Graph<String, IdentifiedWeightedEdge> g = zooGraph.graph;

        String pastStreet = "";
        String startVertex = zooGraph.getExhibitWithId(graphPath.getStartVertex()).name;

        for (IdentifiedWeightedEdge e: graphPath.getEdgeList()){
            String k2, p2;
            k2 = zooGraph.getExhibitWithId(g.getEdgeTarget(e)).kind;
            p2 = zooGraph.getExhibitWithId(g.getEdgeTarget(e)).name;

            if(startVertex.compareTo(p2) == 0){
                k2 = zooGraph.getExhibitWithId(g.getEdgeSource(e)).kind;
                p2 = zooGraph.getExhibitWithId(g.getEdgeSource(e)).name;
            }

            startVertex = p2;

            String currStreet = zooGraph.getTrailWithId(e.getId()).street;
            double eWeight = g.getEdgeWeight(e);
            String detailedDirection = "";

            if (pastStreet.compareTo(currStreet) == 0){
                detailedDirection = "Continue on " + currStreet + " "+
                        eWeight + " ft towards ";
            }
            else{
                pastStreet = currStreet;
                detailedDirection = "Proceed on " + currStreet + " "+
                        eWeight + " ft towards ";
            }

            // target location
            if(k2.compareTo("exhibit") == 0){
                detailedDirection +=
                        p2 + " exhibit";
            }

            else if(k2.compareTo("intersection") == 0){
                if (p2.contains(" / ")) {
                    String[] tokens = p2.split(" / ");
                    detailedDirection +=
                            " corner of " + tokens[0] + " and " + tokens[1];
                }
                else {
                    detailedDirection += p2;
                }
            }

            else if(k2.compareTo("gate") == 0){
                detailedDirection += p2;
            }

            path.add(detailedDirection);
        }

        return path;
    }

    public ArrayList<String> getCurrentBriefPath() {
        ArrayList<String> path = new ArrayList<>();

        GraphPath<String, IdentifiedWeightedEdge> graphPath =
                findPathBetween(exhibits.get(currentStartExhibitIndex),
                        exhibits.get(currentEndExhibitIndex));

        Graph<String, IdentifiedWeightedEdge> g = zooGraph.graph;

        double totalWeight = 0;
        String briefDirection = "";
        String pastStreet = "";
        String endLocation = exhibits.get(currentEndExhibitIndex).name;
        String startVertex = zooGraph.getExhibitWithId(graphPath.getStartVertex()).name;

        for (IdentifiedWeightedEdge e: graphPath.getEdgeList()) {
            String k1, k2, p1, p2;
            k1 = zooGraph.getExhibitWithId(g.getEdgeSource(e)).kind;
            k2 = zooGraph.getExhibitWithId(g.getEdgeTarget(e)).kind;
            p1 = zooGraph.getExhibitWithId(g.getEdgeSource(e)).name;
            p2 = zooGraph.getExhibitWithId(g.getEdgeTarget(e)).name;
            if(startVertex.compareTo(p1) != 0){
                String temp;
                temp = p1;
                p1 = p2;
                p2 = temp;
                temp = k1;
                k1 = k2;
                k2 = temp;
            }
            startVertex = p2;

            double eWeight = g.getEdgeWeight(e);

            totalWeight += eWeight;
            String currStreet = zooGraph.getTrailWithId(e.getId()).street;

            // Starting location
            if (pastStreet.compareTo("") == 0) {
                pastStreet = currStreet;
                briefDirection = "Proceed on " + currStreet + " ";
            }

            // look ahead
            // return end path if curr path != prev path
            // start new path
            if (pastStreet.compareTo(currStreet) != 0) {
                totalWeight -= eWeight;
                briefDirection += totalWeight + " ft towards ";

                if (k1.compareTo("exhibit") == 0) {
                    briefDirection += p1 + " exhibit";
                }

                else if (k1.compareTo("intersection") == 0) {
                    if (p1.contains(" / ")) {
                        String[] tokens = p1.split(" / ");
                        briefDirection +=
                                " corner of " + tokens[0] + " and " + tokens[1];
                    } else {
                        briefDirection += p1;
                    }
                }
                else if (k1.compareTo("gate") == 0) {
                    briefDirection += p1;
                }

                path.add(briefDirection);

                // start new Path
                totalWeight = eWeight;
                briefDirection = "Proceed on " + currStreet + " ";
                pastStreet = currStreet;
            }
            // last location
            if (endLocation.compareTo(p2) == 0) {
                briefDirection += totalWeight + " ft towards ";
                if (k2.compareTo("exhibit") == 0) {
                    briefDirection += p2 + " exhibit";
                    totalWeight = 0;
                }
                else if (k2.compareTo("intersection") == 0) {
                    if (p2.contains(" / ")) {
                        String[] tokens = p2.split(" / ");
                        briefDirection +=
                                " corner of " + tokens[0] + " and " + tokens[1];
                    }
                    else {
                        briefDirection += p2;
                    }
                }
                else if (k2.compareTo("gate") == 0) {
                    briefDirection += p2;
                }

                path.add(briefDirection);
            }
        }

        return path;
    }

    public LinkedHashMap<ZooGraph.Exhibit, Double> getDistanceMapForPlanSummary() {
        exhibitDistanceMap.remove(getEntranceExitGate());
        return exhibitDistanceMap;
    }
    public List<ZooGraph.Exhibit> getUnvisitedExhibits() {
        List<ZooGraph.Exhibit> list = new ArrayList<>();
        if(goingForward()) {
            for(int i = currentEndExhibitIndex; i < exhibits.size()-1; i++) {
                list.add(exhibits.get(i));
            }
        }
        else {
            for(int i = currentEndExhibitIndex; i > 0; i--) {
                list.add(exhibits.get(i));
            }
        }
        return list;
    }
    public boolean checkOffTrack(double lat, double lng) {
        double Base = 100;
        double firstWeight = 0;
        boolean flag = false;
        List<ZooGraph.Exhibit> list = getUnvisitedExhibits();
        Map<ZooGraph.Exhibit, Double> map = new HashMap<>();
        for (ZooGraph.Exhibit ex : list) {
            if(ex.kind == "gate") {
                continue;
            }
            double d_lat = Math.abs(lat - ex.lat);
            double d_lng = Math.abs(lng - ex.lng);
            double d_ft_v = d_lat * Degree_latitude_in_ft;
            double d_ft_h = d_lng * Degree_longitude_in_ft;
            double d_ft = Math.sqrt(Math.pow(d_ft_h, 2) + Math.pow(d_ft_v, 2));
            double weight = Base * Math.ceil(d_ft / Base);
            if (firstWeight == 0) {
                firstWeight = weight;
            } else {
                if (weight < firstWeight) {
                    flag = true;
                    return flag;
                }
            }
            map.put(ex, weight);
        }
        return flag;
    }

    public void saveIndex(int s, int e) {
        MyPrefs.saveLength(App.getContext(), "startIndex", s);
        MyPrefs.saveLength(App.getContext(), "endIndex", e);
    }

    public void saveList(List<ZooGraph.Exhibit> exhibits) {
        for(int i = 0; i < exhibits.size()-2; i++) {
            MyPrefs.saveString(App.getContext(), "exhibitList", exhibits.get(i+1).id, i);
        }
        MyPrefs.saveLength(App.getContext(), "exhibitListSize",exhibits.size()-2);
    }

    public void deleteListData(int length) {
        for(int i = 0; i < length; i++) {
            MyPrefs.delete(App.getContext(), "exhibitList"+i);
        }
        MyPrefs.saveLength(App.getContext(), "exhibitListSize", 0);
    }
}
