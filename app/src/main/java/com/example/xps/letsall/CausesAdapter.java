package com.example.xps.letsall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XPS on 29/11/2015.
 */
public class CausesAdapter extends BaseAdapter {
    private Context context;
    private List<CauseOLD> listCauseOLDs;

    public CausesAdapter(Context context, List<CauseOLD> listCauseOLDs) {
        this.context = context;
        this.listCauseOLDs = listCauseOLDs;
    }

    public int getCount() {
        return listCauseOLDs.size();
    }

    public Object getItem(int position) {
        return listCauseOLDs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        CauseOLD entry = listCauseOLDs.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cause_list_item, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(entry.getTitle());
        TextView op = (TextView) convertView.findViewById(R.id.op);
        op.setText(entry.getOP());

        return convertView;
    }
}