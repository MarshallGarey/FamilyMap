package com.example.mgarey2.familymap.ui_tools;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.mgarey2.familymap.R;

import java.util.ArrayList;

/**
 * Created by Marshall
 */
public class ExpandableListAdapater extends BaseExpandableListAdapter {

    private final String LOG_TAG = "ExpandableListAdapter";
    private ArrayList<String> groupItems, child;
    private ArrayList<Object> childItems;
    private Activity context;
    private LayoutInflater layoutInflater;

    public ExpandableListAdapater(Activity context, ArrayList<String> groupItems, ArrayList<Object> childItems,
                                  LayoutInflater layoutInflater) {
        this.context = context;
        this.childItems = childItems;
        this.groupItems = groupItems;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getGroupCount() {
        return groupItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }
        ((CheckedTextView) convertView).setText(groupItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        child = (ArrayList<String>) childItems.get(groupPosition);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expandable_list_child, null);
        }
        TextView view = (TextView) convertView.findViewById(R.id.groupTextView);
        view.setText(child.get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
}
