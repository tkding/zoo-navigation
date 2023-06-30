package com.example.sdzooseeker_team_64;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZooGraph implements Serializable {
    public static class Exhibit implements Serializable {
        public static enum Kind {
            // The SerializedName annotation tells GSON how to convert
            // from the strings in our JSON to this Enum.
            @SerializedName("gate") GATE,
            @SerializedName("exhibit") EXHIBIT,
            @SerializedName("intersection") INTERSECTION
        }

        public String id;
        @SerializedName("group_id")
        public String groupId;  // is null if lat and lng exist
        public String kind;
        public String name;
        public List<String> tags;
        public double lat;  // is null if groupId exists
        public double lng;  // is null if groupId exists

        public Exhibit(String id, String groupId, String kind, String name, List<String> tags, double lat, double lng) {
            this.id = id;
            this.groupId = groupId;
            this.kind = kind;
            this.name = name;
            this.tags = tags;
            this.lat = lat;
            this.lng = lng;
        }

        public String toString() {
            return name;
        }

    }

    public static class Trail implements Serializable {
        public String id;
        public String street;

        public Trail(String id, String street) {
            this.id = id;
            this.street = street;
        }
    }

    public List<Exhibit> exhibits;
    public List<Trail> trails;
    public Graph<String, IdentifiedWeightedEdge> graph;

    public ZooGraph(Context context) {
        this.exhibits = loadExhibits(context, "sample_vertex_info.json");
        this.trails = loadTrails(context, "sample_edge_info.json");
        this.graph = loadZooGraphJSON(context, "sample_zoo_graph.json");
    }

    public List<Exhibit> getAllStrictExhibits() {
        List<Exhibit> allStrictExhibits = new ArrayList<>();
        for (Exhibit exhibit : exhibits) {
            if(exhibit.kind.equals("exhibit")) {
                allStrictExhibits.add(exhibit);
            }
        }
        return allStrictExhibits;
    }

    public Exhibit getExhibitWithId(String id) {
        for (Exhibit exhibit: exhibits) {
            if(exhibit.id.equals(id)) {
                return exhibit;
            }
        }
        return null;
    }

    public Exhibit getExhibitWithName(String name) {
        for (Exhibit exhibit: exhibits) {
            if(exhibit.name.equals(name)) {
                return exhibit;
            }
        }
        return null;
    }

    public Trail getTrailWithId(String id) {
        for (Trail trail: trails) {
            if(trail.id.equals(id)) {
                return trail;
            }
        }
        return null;
    }


    private static List<ZooGraph.Exhibit> loadExhibits(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooGraph.Exhibit>>(){}.getType();
            List<ZooGraph.Exhibit> exhibits = gson.fromJson(reader, type);

            return exhibits;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<ZooGraph.Trail> loadTrails(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooGraph.Trail>>(){}.getType();
            List<ZooGraph.Trail> trails = gson.fromJson(reader, type);

            return trails;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static Graph<String, IdentifiedWeightedEdge> loadZooGraphJSON(Context context, String path) {
        // Create an empty graph to populate.
        Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);

        // Create an importer that can be used to populate our empty graph.
        JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();

        // We don't need to convert the vertices in the graph, so we return them as is.
        importer.setVertexFactory(v -> v);

        // We need to make sure we set the IDs on our edges from the 'id' attribute.
        // While this is automatic for vertices, it isn't for edges. We keep the
        // definition of this in the IdentifiedWeightedEdge class for convenience.
        importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);

            // And now we just import it!
            importer.importGraph(g, reader);

            return g;
        } catch (IOException e) {
            e.printStackTrace();
            return g;
        }
    }

}
