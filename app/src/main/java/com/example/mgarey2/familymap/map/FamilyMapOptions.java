package com.example.mgarey2.familymap.map;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.Marker;
import com.example.mgarey2.familymap.event.Event;

import java.util.TreeSet;

/**
 * Created by Marshall on 12/7/2016.
 * Options for the family map.
 */
public class FamilyMapOptions {

    protected static final int RED_INDEX = 0;
    protected static final int GREEN_INDEX = 1;
    protected static final int BLUE_INDEX = 2;

    protected static int lifeStoryLinesHueIndex = RED_INDEX;
    protected static boolean lifeStoryLinesActive = false;
    protected static int familyTreeLinesHueIndex = GREEN_INDEX;
    protected static boolean familyTreeLinesActive = false;
    protected static int spouseLinesHueIndex = BLUE_INDEX;
    protected static boolean spouseLinesActive = false;
    protected static float[] lineColors = {
            BitmapDescriptorFactory.HUE_RED, // Red
            BitmapDescriptorFactory.HUE_GREEN, // Green
            BitmapDescriptorFactory.HUE_BLUE // Blue
    };

    protected static int mapType = AmazonMap.MAP_TYPE_NORMAL;
    protected static boolean reSync = false;
    protected static TreeSet<String> activeEventFilters;

    public static void initFilters() {
        activeEventFilters = new TreeSet<>();
        for (String str : Event.eventTypes) {
            activeEventFilters.add(str);
        }
    }

    public static void addFilter(String filter) {
        activeEventFilters.add(filter);
    }

    public static void removeFilter(String filter) {
        activeEventFilters.remove(filter);
    }

    public static String activeFiltersToString() {
        StringBuilder sb = new StringBuilder();
        for (String str : activeEventFilters) {
            sb.append(str);
            sb.append(", ");
        }
        return sb.toString();
    }
}
