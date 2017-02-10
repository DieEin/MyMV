package com.elsyscoursework.mymv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tomi on 10.2.2017 г..
 */

public class VehicleMaintenanceArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public VehicleMaintenanceArrayAdapter(Context context, String[] values) {
        super(context, R.layout.maintenance_list, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.maintenance_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        String valueName = values[position].split(":")[0];

        if (valueName.equals("Gas")) {
            imageView.setImageResource(R.drawable.gas_icon);
        } else if (valueName.equals("Other")) {
            imageView.setImageResource(R.drawable.other_icon);
        } else if (valueName.equals("Fluids")) {
            imageView.setImageResource(R.drawable.fluids_icon);
        } else if (valueName.equals("Tyres")) {
            imageView.setImageResource(R.drawable.tyres_icon);
        }

        return rowView;
    }
}
