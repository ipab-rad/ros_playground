package com.rad.rosplayground.rosjava.topics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rad.rosplayground.R;

import org.ros.master.client.TopicType;

import java.util.ArrayList;

public class TopicTypeAdapter extends ArrayAdapter<TopicType> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public TopicTypeAdapter(Context context, int textViewResourceId, ArrayList<TopicType> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.topic_type_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.topictype_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TopicType item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(String.format("%s -- %s", item.getName(), item.getMessageType()));
        }

        return convertView;
    }
}
