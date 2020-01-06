package com.example.walkin;

/**
 * Adapted from Code provided for lab 5
 */


        import android.app.Activity;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.List;

public class Clinic_ServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public Clinic_ServiceList(Activity context, List<Service> services) {
        super(context, R.layout.activity_service_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_service_list, null, true);
        TextView textViewName = /*(TextView)*/ listViewItem.findViewById(R.id.textViewName);
        TextView textViewRole = /*(TextView)*/ listViewItem.findViewById(R.id.textViewRole);
        Service service = services.get(position);
        textViewName.setText(service.getServiceName());
        textViewRole.setText(String.valueOf(service.getServiceRole()));
        return listViewItem;
    }
}