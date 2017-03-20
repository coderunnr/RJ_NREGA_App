package com.android.rajasthanhackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.rajasthanhackathon.DistrictActivity;
import com.android.rajasthanhackathon.DistrictMainActivity;
import com.android.rajasthanhackathon.R;
import com.android.rajasthanhackathon.models.Employee;

import java.util.List;

/**
 * Created by root on 13/7/16.
 */
public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> {


        List<Employee> employees;
    DistrictMainActivity districtMainActivity;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        View v;
        TextView aadhar,description,grampanchayat;

        public ViewHolder(View v, DistrictAdapter myAdapter) {
            super(v);
            this.v = v;
            aadhar=(TextView)v.findViewById(R.id.district_aadhar);
            description=(TextView)v.findViewById(R.id.district_description);
            grampanchayat=(TextView)v.findViewById(R.id.district_grampanchayat);

;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DistrictAdapter(DistrictMainActivity districtMainActivity, List<Employee> employees) {

        this.districtMainActivity=districtMainActivity;
        this.employees=employees;



    }

    // Create new views (invoked by the layout manager)
    @Override
    public DistrictAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v;

            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleitem_district, parent, false);

        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.aadhar.setText("Aadhar No: "+employees.get(position).getAadharno());
        holder.description.setText("Description: "+employees.get(position).getDescription());
        holder.grampanchayat.setText("Uploaded by: "+employees.get(position).getUploaded_by());



        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent((Context) districtMainActivity, DistrictActivity.class);
                intent.putExtra("item",employees.get(position));
                districtMainActivity.startActivity(intent);

            }
        });




    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return employees.size();

    }
}


