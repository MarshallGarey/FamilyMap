package com.example.mgarey2.familymap.map;

import com.example.mgarey2.familymap.event.Event;
import com.example.mgarey2.familymap.person.Person;

/**
 * Created by Marshall on 12/7/2016.
 * Options for the family map.
 */
public class FamilyMapOptions {
    protected static float lifeStoryLinesHue;
    protected static float familyTreeLinesHue;
    protected static float spouseLinesHue;
    protected enum MAP_TYPE_E {
        regular, satellite, hybrid, terrain
    }
    protected static MAP_TYPE_E mapType = MAP_TYPE_E.regular;
    protected static boolean reSync = false;
}
