package com.rad.rosplayground.rosjava.msg.transformers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rad.rosplayground.R;

import java.util.ArrayList;

public class ITransformMsgAdapter extends ArrayAdapter<ITransformMsg> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public ITransformMsgAdapter(Context context, int textViewResourceId, ArrayList<ITransformMsg> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.i_transform_msg_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.i_transform_msg_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ITransformMsg item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(item.getName());
        }

        return convertView;
    }
}
