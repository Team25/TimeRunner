package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.entities.Employee;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dingo on 4.4.2017.
 */

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {
        super(context, 0, employeeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee employee = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_employee, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.employee_name);
        TextView id = (TextView) convertView.findViewById(R.id.employee_id);
        // Populate the data into the template view using the data object
        name.setText(employee.getFullName());
        id.setText(employee.getId()+"");
        // Return the completed view to render on screen
        return convertView;
    }

}
