package com.heineck.mydrawerlayoutsample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vheineck on 11/09/15.
 */
public class MenuArrayAdapter extends ArrayAdapter {

    private Context context;
    private List list;
    private int selectedPosition = 0;

    public MenuArrayAdapter(Context context, List items) {
        super(context, R.layout.drawer_list_item, items);
        this.context = context;
        this.list = items;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        View verticalLine;
        ImageView imgViewIcon;
        TextView txtTitle;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View viewToUse = null;

        try {
            CategoryItem category = (CategoryItem)this.list.get(position);

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                viewToUse = mInflater.inflate(R.layout.drawer_list_item, null);
                holder = new ViewHolder();
                holder.verticalLine = viewToUse.findViewById(R.id.viewVerticalLine);
                holder.imgViewIcon = (ImageView) viewToUse.findViewById(R.id.imgViewIcon);
                holder.txtTitle = (TextView) viewToUse.findViewById(R.id.txtTitle);

                viewToUse.setTag(holder);

            } else {
                viewToUse = convertView;
                holder = (ViewHolder) viewToUse.getTag();
            }

            if (this.selectedPosition == position) {
                holder.verticalLine.setVisibility(View.VISIBLE);
            } else {
                holder.verticalLine.setVisibility(View.INVISIBLE);
            }

            holder.txtTitle.setText(category.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewToUse;
    }

    public void setSelectedPosition(int position) {

        this.selectedPosition = position;
        notifyDataSetChanged();

    }

}
