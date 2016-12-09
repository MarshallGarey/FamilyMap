package com.example.mgarey2.familymap.map;

import com.amazon.geo.mapsv2.AmazonMap;
import com.example.mgarey2.familymap.event.Event;

import java.util.TreeSet;

/**
 * Created by Marshall on 12/7/2016.
 * Options for the family map.
 */
public class FamilyMapOptions {
    protected static float lifeStoryLinesHue;
    protected static float familyTreeLinesHue;
    protected static float spouseLinesHue;
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
