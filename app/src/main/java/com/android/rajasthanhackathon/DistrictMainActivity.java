package com.android.rajasthanhackathon;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.rajasthanhackathon.adapter.DistrictAdapter;
import com.android.rajasthanhackathon.connectionutils.Connection;
import com.android.rajasthanhackathon.connectionutils.Utility;
import com.android.rajasthanhackathon.models.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistrictMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Employee> employees;
    int district_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_main);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycle_gramlist);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(DistrictMainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);




        new GetDistrictId(Utility.URL1+"district/?officer="+Utility.getDistrictId(DistrictMainActivity.this),new JSONObject()).execute();

    }



    public class GetDistrictDetails extends AsyncTask<Void,Void,String>
    {
        String url;
        JSONObject jsonObject;

        public GetDistrictDetails(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.v(getApplication().getClass().getSimpleName(),s);
            try {
                employees=new ArrayList<>();

                JSONArray topArray=new JSONArray(s);
                for(int i=0;i<topArray.length();i++)
                {
                    Employee employee=new Employee();
                    JSONObject single_element=topArray.getJSONObject(i);
                    employee.setAadharno(single_element.getString("emp_aadhar"));
                    employee.setDescription(single_element.getString("description"));
                    employee.setPhoto(single_element.getString("photo"));
                    employee.setUploaded_by(single_element.getInt("uploaded_by"));
                    employee.setTimestamp(single_element.getString("timestamp"));
                    employee.setId(single_element.getInt("id"));
                    employees.add(employee);


                }
                callAdapter();




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(Void... voids) {
            Connection connection=new Connection(url,jsonObject,DistrictMainActivity.this);
            return connection.connectiontask();
        }

    }



    public class GetDistrictId extends AsyncTask<Void,Void,String>
    {
        String url;
        JSONObject jsonObject;

        public GetDistrictId(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {



            Log.v(getApplication().getClass().getSimpleName(),s);
            try {
                employees=new ArrayList<>();

                JSONArray topArray=new JSONArray(s);
                JSONObject jsonobject=topArray.getJSONObject(0);
                Utility.setDistrictMainId(jsonobject.getInt("id"),DistrictMainActivity.this);

                callGramList();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            Connection connection=new Connection(url,jsonObject,DistrictMainActivity.this);
            return connection.connectiontask();
        }

    }

    private void callGramList() {
        new GetDistrictDetails(Utility.URL1+"work/?district="+Utility.getDistrictMainId(DistrictMainActivity.this),new JSONObject()).execute();
    }


    private void callAdapter() {

        mAdapter=new  DistrictAdapter(this,employees);
        mRecyclerView.setAdapter(mAdapter);
    }


}
