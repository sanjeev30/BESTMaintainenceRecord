package com.bestbuses.bestmaintenancerecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpListAdapter";

    private Context ctx;
    private List<String> headingItems;
    private HashMap<String,List<RecordItems>> childItems;

    public ExpListAdapter(Context ctx, List<String> headingItems, HashMap<String,List<RecordItems>> childItems) {
        this.ctx = ctx;
        this.headingItems = headingItems;
        this.childItems = childItems;
    }

    @Override
    public int getGroupCount() {
        return headingItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childItems.get(headingItems.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headingItems.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childItems.get(headingItems.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) this.getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.header_expandable_listview, null);
        }
        TextView heading = view.findViewById(R.id.expandable_heading);
        heading.setText(title);
        Log.d(TAG, "getGroupView: Heading : "+title);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        RecordItems child = (RecordItems) this.getChild(i,i1);
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.child_expandable_listview,null);
        }
        TextView dateFitted = view.findViewById(R.id.tv_dateFitted);
        TextView dateRemoved = view.findViewById(R.id.tv_dateRemoved);
        TextView kilometers = view.findViewById(R.id.tv_kms);

        dateFitted.setText(child.getDateFitted());
        Log.d(TAG, "getChildView: dateFitted: "+child.getDateFitted()+"\ndateRemoved: "+child.getDateRemoved()+"\n Kilometers : "+child.getKilometers());
        dateRemoved.setText(child.getDateRemoved());
        kilometers.setText(child.getKilometers());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
